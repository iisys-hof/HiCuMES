using System;
using System.Collections.Generic;

namespace HiCuMes.Persistence.Entities;

public partial class ProductionorderProductionorder
{
    public long ProductionOrderId { get; set; }

    public long SubProductionOrdersId { get; set; }

    public  Productionorder ProductionOrder { get; set; } = null!;

    public  Productionorder SubProductionOrders { get; set; } = null!;
}
