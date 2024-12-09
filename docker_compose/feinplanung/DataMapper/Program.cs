using System.Globalization;
using Microsoft.Data.SqlClient;
using Microsoft.Extensions.Configuration;
using MySqlConnector;

namespace DataMapper
{
  public class Program
  {
    private static IConfiguration _iconfiguration;
    private static string _hicumesConnectionString;
    private static string _raicoConnectionString;


    public static void Main(string[] args)
    {
      var builder = new ConfigurationBuilder()
        .SetBasePath(AppContext.BaseDirectory)
        .AddJsonFile("appsettings.json", optional: false, reloadOnChange: true);
      _iconfiguration = builder.Build();

      try
      {
        _hicumesConnectionString = _iconfiguration.GetConnectionString("HicumesDB") ?? string.Empty;
        _raicoConnectionString = _iconfiguration.GetConnectionString("RaicoDB") ?? string.Empty;
        if (string.IsNullOrEmpty(_raicoConnectionString) || string.IsNullOrEmpty(_hicumesConnectionString))
        {
          throw new Exception($"One or many connectionStrings are missing: HicumesDB:{_hicumesConnectionString}, RaicoDB:{_raicoConnectionString}");
        }

        var raicoConnection = new SqlConnection(_raicoConnectionString);
        var hicumesConnection = new MySqlConnection(_hicumesConnectionString);
        raicoConnection.Open();
        hicumesConnection.Open();

        //MachineType
         using var getMachineTypes = new SqlCommand("SELECT ko.[id], ko.[Kost] " +
                                                    "FROM [Raico].[dbo].[KOST] ko ",
           raicoConnection);
         using (var reader = getMachineTypes.ExecuteReader())
         {
           while (reader.Read())
           {
             Console.WriteLine("{0}  {1}" , reader.GetInt32(0).ToString(), reader.GetString(1));
             var insertCommand = new MySqlCommand($"insert into CD_MachineType (externalId, name) values ('{reader.GetInt32(0).ToString()}', '{reader.GetString(1)}')", hicumesConnection).ExecuteNonQuery();
           }
         }

         // //Machine
         using var getMachines = new SqlCommand("select kost.[id], magr.[Kost], magr.[MAGR], magr.[id] " +
                                                "from [Raico].[dbo].[MAGR] magr " +
                                                "join [Raico].[dbo].[KOST] kost on kost.KOST = magr.[KOST]",
           raicoConnection);
         using (var reader = getMachines.ExecuteReader())
         {
           while (reader.Read())
           {
             using var machineTypes = new MySqlCommand($"select id from CD_MachineType where name = '{reader.GetString(1)}'", hicumesConnection);
             int machineTypeId = 0;
             using (var machineTypeReader = machineTypes.ExecuteReader())
             {
               while (machineTypeReader.Read())
               {
                 machineTypeId = machineTypeReader.GetInt32(0);
                 Console.WriteLine("MT Id  {0}" , machineTypeReader.GetInt32(0).ToString());
               }
             }
             if(machineTypeId == 0) continue;
             Console.WriteLine("{0}  {1} {2}  {3}" , reader.GetInt32(0).ToString(), reader.GetString(1), reader.GetString(2), reader.GetInt32(3).ToString());
             var insertCommand = new MySqlCommand($"insert into CD_Machine (externalId, name, machineType_id) values ('{reader.GetInt32(3).ToString()}', '{reader.GetString(1)} {reader.GetString(2)}', '{machineTypeId}')", hicumesConnection).ExecuteNonQuery();
           }
         }

         //Product
         using var getProducts = new SqlCommand("Select [Artikel], [VKME], [id] FROM [Raico].[dbo].[ARTIKEL]",
           raicoConnection);
         using (var reader = getProducts.ExecuteReader())
         {
           while (reader.Read())
           {
             Console.WriteLine("{0} {1} {2}" , reader.GetString(0), reader.GetString(1), reader.GetInt32(2).ToString());
             var insertCommand = new MySqlCommand($"insert into CD_Product (externalId, name, unitType) values ('{reader.GetInt32(2).ToString()}', '{reader.GetString(0)}', '{reader.GetString(1)}')", hicumesConnection).ExecuteNonQuery();
           }
        }

        //ProductionStep
        using var getProductsForProductionSteps = new SqlCommand("SELECT [BEMERKUNG], [POSITION], [APLAN], [KOST], [TR], [TE], [id] FROM [Raico].[dbo].[APLANPOS]",
          raicoConnection);

        using (var reader = getProductsForProductionSteps.ExecuteReader())
        {
          while (reader.Read())
          {
            var machineTypeName = reader.GetString(3);
            using var machineTypes = new MySqlCommand($"select id from CD_MachineType where name = '{machineTypeName}'", hicumesConnection);
            long machineTypeId = 0;
            using (var machineTypeReader = machineTypes.ExecuteReader())
            {
              var hasValues = false;
              while (machineTypeReader.Read())
              {
                hasValues = true;
                machineTypeId = machineTypeReader.GetInt64(0);
                Console.WriteLine("MT Id  {0}" , machineTypeReader.GetInt64(0).ToString());
              }

              if (!hasValues)
              {
                Console.Write($"MT - No value for machineTypeName {machineTypeName}");
                continue;
              }
            }
            using var products = new MySqlCommand($"select id from CD_Product where name = '{reader.GetString(2)}'", hicumesConnection);
            long productId = 0;
            using (var productReader = products.ExecuteReader())
            {
              while (productReader.Read())
              {
                productId = productReader.GetInt64(0);
                Console.WriteLine("Product Id  {0}" , productReader.GetInt64(0).ToString());
              }
            }
            // productionDuration ist in Raico-DB pro 1000 Teile in Sekunden
            // Ausrechnen pro Teil in Sekunden
            var productionDuration = reader.GetInt32(5) / 1000;
            if (productionDuration < 1)
            {
              productionDuration = 1;
            }
            // if(machineTypeId == 0 || ) continue;
            Console.WriteLine("bemerkung {0} ag {1} aplan {2} kost {3} tr {4} te {5}" , reader.GetString(0), reader.GetInt16(1).ToString(), reader.GetString(2),reader.GetString(3), reader.GetInt32(4).ToString(), reader.GetInt32(5).ToString());
            var insertCommand = new MySqlCommand($"insert into CD_ProductionStep (externalId, name, productionDuration, sequence, setupTime, product_id, machineType_id) " +
                                                 $"values ('{reader.GetInt32(6)}', '{reader.GetString(0)}', '{productionDuration}', '{reader.GetInt16(1)}', '{reader.GetInt32(4)}', '{productId}', '{machineTypeId}')", hicumesConnection)
              .ExecuteNonQuery();
          }
        }

        //ProductionOrder
        using var getProductionOrder = new SqlCommand("SELECT [BAUFTRAG],[VBME],[MENGE],[STATUS],[ENDE],[APLAN] " +
                                                      "FROM [Raico].[dbo].[WAUFTRAG] " +
                                                      //"where [ENDE] > GETDATE()",
                                                       "where [STATUS] != 6",
          raicoConnection);
        using (var reader = getProductionOrder.ExecuteReader())
        {
          while (reader.Read())
          {
            using var products = new MySqlCommand($"select id from CD_Product where name = '{reader.GetString(5)}'",
              hicumesConnection);
            long productId = 0;
            using (var productReader = products.ExecuteReader())
            {
              while (productReader.Read())
              {
                productId = productReader.GetInt64(0);
                Console.WriteLine("Product Id  {0}", productReader.GetInt64(0).ToString());
              }
            }

            var status = reader.GetInt16(3);

            Console.WriteLine("BAUFTRAG {0} VBME {1} MENGE {2} STATUS {3}  ENDE {4:s}", reader.GetString(0),
              reader.GetString(1), reader.GetDecimal(2).ToString(CultureInfo.CurrentCulture),
              //reader.GetInt16(3).ToString(),
              status switch
              {
                1 => "angelegt",
                2 => "geplant",
                3 => "in Arbeit",
                4 => "beendet",
                5 => "teilweise eingelagert",
                6 => "komplett eingelagert",
                _ => "kein valider Status"
              }, reader.GetDateTime(4).ToString());

            var insertCommand = new MySqlCommand(
              $"insert into ProductionOrder (externalId, name, unitString, amount, status, deadline, product_id) " +
              $"values ('{reader.GetString(0)}','{reader.GetString(0)}', '{reader.GetString(1)}', '{reader.GetSqlDecimal(2).ToString()}', '{status switch
              {
                1 => "angelegt",
                2 => "geplant",
                3 => "in Arbeit",
                4 => "beendet",
                5 => "teilweise eingelagert",
                6 => "komplett eingelagert",
                _ => "kein valider Status"
              }}', " +
              $"'{reader.GetDateTime(4):s}'" +
              $", '{productId}')", hicumesConnection).ExecuteNonQuery();
          }
        }

        //SubProductionOrder

          using var addedProductionOrders = new MySqlCommand($"select po.name, po.unitString, po.amount, po.status, po.deadline, prod.id, po.id, prod.name from ProductionOrder po join CD_Product prod on prod.id = po.product_id", hicumesConnection);
          var list = new List<ProductionOrderData>();
          int containersAmount = 0;
          using (var addedProductionOrderReader = addedProductionOrders.ExecuteReader())
          {
            while (addedProductionOrderReader.Read())
            {
              var productionOrderName = addedProductionOrderReader.GetString(0);
              var orderUnitString = addedProductionOrderReader.GetString(1);
              var orderAmount = addedProductionOrderReader.GetDouble(2);
              var orderStatus = addedProductionOrderReader.GetString(3);
              var orderDeadline = addedProductionOrderReader.GetDateTime(4);
              var orderId = addedProductionOrderReader.GetInt64(6);
              var productId = addedProductionOrderReader.GetInt64(5);
              var productName = addedProductionOrderReader.GetString(7);

              list.Add(new ProductionOrderData() {productionOrderName = productionOrderName, orderUnitString = orderUnitString, orderAmount = orderAmount, orderStatus = orderStatus, orderDeadline = orderDeadline, orderId = orderId, productId = productId, productName = productName});
            }
          }

          foreach (var order in list)
          {
              using var containers = new SqlCommand($"select a.ANP_PAZMNG, a.MANDANT, a.ARTIKEL from ARTIKEL a " +
                                                    $"join WAUFTRAG wa on wa.APLAN = a.artikel " +
                                                    $"join APLAN ap on ap.MANDANT = a.MANDANT " +
                                                    $"where a.ARTIKEL = '{order.productName}' " +
                                                    $"group by a.ANP_PAZMNG, a.MANDANT, ARTIKEL;", raicoConnection);
              var piecesProContainer = 0;
              using (var containersReader = containers.ExecuteReader())
              {
                while (containersReader.Read())
                {
                  piecesProContainer = containersReader.GetInt32(0);
                }
              }

              if (piecesProContainer <= 0) continue;

              containersAmount = (int)Math.Ceiling(order.orderAmount / piecesProContainer);
              var remainingAmount = (int)order.orderAmount % piecesProContainer;

              if (containersAmount <= 1 ) continue;
              for (var i = 0; i < containersAmount; i++)
              {
                var insertSubOrdersCommand = new MySqlCommand($"insert into ProductionOrder (externalId, name, unitString, amount, status, deadline, product_id, parentProductionOrder_id) " +
                                                              $"values ('{order.productionOrderName}.{i+1}','{order.productionOrderName}.{i+1}', '{order.orderUnitString}', '{(i != containersAmount - 1 ? piecesProContainer : remainingAmount)}', '{order.orderStatus}', " +
                                                              $"'{order.orderDeadline:s}'" +
                                                              $", '{order.productId}', '{order.orderId}')", hicumesConnection).ExecuteNonQuery();
              }
          }
      }
      catch (Exception e)
      {
        Console.WriteLine(e);
        throw new Exception(e.Message);
      }
    }
  }

  internal record ProductionOrderData
  {
    public string productionOrderName;
    public string orderUnitString;
    public double orderAmount;
    public string orderStatus;
    public DateTime orderDeadline;
    public long orderId;
    public long productId;
    public string productName;
  }
}
