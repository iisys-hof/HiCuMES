using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.CompilerServices;
using System.Text.Json;
using System.Text.Json.Nodes;
using System.Threading.Tasks;
using Api.Controllers.DTOs;
using Api.Controllers.Mappers;
using HiCuMes.Persistence.Context;
using HiCuMes.Persistence.DataAccessRepository;
using HiCuMes.Persistence.Entities;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Logging;
using Serilog;

namespace Api.Controllers;

[ApiController]
[Route("api/hicumes")]
public partial class ProductionDataController : ControllerBase
{
  // private static readonly Log.Logger = LogManager.GetLogger(MethodBase.GetCurrentMethod()?.DeclaringType);

  private readonly HiCuMESDbContext _hicumesContext;
  private readonly ILogger<ProductionDataController> _logger;

  public ProductionDataController(HiCuMESDbContext hicumesContext, ILogger<ProductionDataController> logger)
  {
    _hicumesContext = hicumesContext;
    _logger = logger;
  }

  [HttpGet("machinesOfProductionOrder")]
  public IEnumerable<CdMachine> MachinesOfProductionOrder([FromQuery]long productId)
  {
    try
    {
      var machineTypeIdsOfProduct = _hicumesContext.CdProductionSteps
        .Where(x => x.ProductId == productId)
        .Select(x => x.MachineTypeId);

      var machines = _hicumesContext.CdMachines
        .Include(x => x.MachineType)
        .Where(x => machineTypeIdsOfProduct
          .Contains(x.MachineTypeId));

      return machines;
    }
    catch (Exception e)
    {
      LogException(e);
      throw;
    }
  }


  [HttpGet("machineGroupsOfProductionOrder")]
  public IEnumerable<CdMachinetype> MachineGroupsOfProductionOrder([FromQuery] long productId)
  {
    try
    {
      var machineTypeIdsOfProduct = _hicumesContext.CdProductionSteps.Where(x => x.ProductId == productId)
        .Select(x => x.MachineTypeId);

      var machineTypes = _hicumesContext.CdMachineTypes.Include(x => x.CdMachines)
        .Where(x => machineTypeIdsOfProduct.Contains(x.Id));

      return machineTypes;
    }
    catch (Exception e)
    {
      LogException(e);
      throw;
    }
  }

  // [HttpGet("productionOrders")]
  // public async Task<IEnumerable<ProductionOrderDto>> ProductionOrders([FromQuery] List<long?> filteredMachineTypes)
  // {
  //   try
  //   {
  //     var mapper = new ProductionOrderMapper();
  //     var res = _hicumesContext.ProductionOrders
  //       .Include(x => x.Product)
  //       .Include(x => x.SubProductionOrders)
  //       .ThenInclude(x => x.Product)
  //       .Where(x => x.ParentProductionOrderId == null);
  //     var productIds = res.Select(x => x.ProductId);
  //     var hasChildrenWithDifferentProductId = res.Select(x => x.SubProductionOrders.Where(so => productIds.Contains(so.ProductId)).Count()).Sum();

  //     if (filteredMachineTypes.Any()) {
  //       var filteredProducts = _hicumesContext.CdProductionSteps.Where(x => filteredMachineTypes.Contains(x.MachineTypeId)).Select(x => x.ProductId);

  //       if (hasChildrenWithDifferentProductId > 0) {
  //         res = res.Where(x => filteredProducts.Contains(x.ProductId));
  //       } else {
  //         res = res.Include(x => x.SubProductionOrders.Where(so => filteredProducts.Contains(so.ProductId)));

  //         var ordersToInclude = new List<Productionorder>();
  //         var productionOrders = res.ToList();

  //         foreach (var productionOrder in productionOrders) {
  //           if (productionOrder.SubProductionOrders.Count > 0) {
  //             ordersToInclude.Add(productionOrder);
  //           }
  //         }

  //         res = res.Where(x => ordersToInclude.Contains(x));
  //       }
  //     }

  //     return await res.Select(x => mapper.ProductionOrderToProductionOrderDto(x)).ToListAsync().ConfigureAwait(false);
  //   }
  //   catch (Exception e)
  //   {
  //     LogException(e);
  //     throw;
  //   }
  // }

  [HttpGet("productionOrders")]
  public async Task<IEnumerable<ProductionOrderDto>> ProductionOrders([FromQuery] List<long?> filteredMachineTypes, int pageNumber = 1, int pageSize = 100, string searchTerm = "")
  {
    try
    {
      var mapper = new ProductionOrderMapper();
      var res = _hicumesContext.ProductionOrders
        .Include(x => x.Product)
        .Include(x => x.SubProductionOrders)
        .ThenInclude(x => x.Product)
        .AsNoTracking()
        .Where(p => p.ParentProductionOrderId == null);
      if (!string.IsNullOrEmpty(searchTerm))
      {
        res = res.Where(x => x.Name != null && x.Name.StartsWith(searchTerm));
      }

      if (filteredMachineTypes.Any()) {
        var filteredProducts = _hicumesContext.CdProductionSteps.Where(x => filteredMachineTypes.Contains(x.MachineTypeId)).Select(x => x.ProductId);
        res = res.Where(x => filteredProducts.Contains(x.ProductId));
      }
      return await res
        .OrderByDescending(x => x.Deadline)
        .ThenByDescending(x => x.Name)
        .Skip((pageNumber - 1) * pageSize)
        .Take(pageSize)
        .Select(x => mapper.ProductionOrderToProductionOrderDto(x)).ToListAsync().ConfigureAwait(false);
    }
    catch (Exception e)
    {
      LogException(e);
      throw;
    }
  }

  [HttpGet("productionStepsOfProduct")]
  public IEnumerable<CdProductionStep> ProductionStepsOfProduct([FromQuery] long productId, [FromQuery] long ProductionOrderId)
  {
    try
    {
      var productionSteps = _hicumesContext.CdProductionSteps.Include(x => x.MachineType).Where(x => x.ProductId == productId);
      return productionSteps;
    }
    catch (Exception e)
    {
      LogException(e);
      throw;
    }
  }

  [HttpGet("productionSteps")]
  public async Task<IEnumerable<CdProductionStep>> UniqueProductionSteps([FromQuery] List<long?> filteredMachineTypes, int pageNumber = 1, int pageSize = 100, string searchTerm = "")
  {
    try
    {
      var mapper = new CdProductionStepMapper();
      var query = _hicumesContext.CdProductionSteps
          .Include(x => x.MachineType)
          .AsNoTracking();
      if (filteredMachineTypes.Count != 0)
      {
        query = query.Where(x => filteredMachineTypes.Contains(x.MachineTypeId));
      }


      if (!string.IsNullOrEmpty(searchTerm))
      {
        query = query.Where(x => x.Name.StartsWith(searchTerm));
      }

      var res = await query.GroupBy(x => x.Name)
        .Select(x => x.First())
        .ToListAsync()
        .ConfigureAwait(false);


      return res
        .OrderBy(x => x.Name)
        .ThenBy(x => x.MachineType.Name)
        .Skip((pageNumber - 1) * pageSize)
        .Take(pageSize);
    }
    catch (Exception e)
    {
      LogException(e);
      throw;
    }
  }
  [HttpGet("ordersContainingStep")]
  public async Task<IEnumerable<Productionorder>> OrdersContainingSteps([FromQuery] string productionStepName, [FromQuery] long machineTypeId, int pageNumber = 1, int pageSize = 100, string searchTerm = "")
  {
    try
    {
      var products =
        _hicumesContext.CdProducts.Where(x => x.CdProductionsteps.Select(y => y.Name).Contains(productionStepName));

      var occupationsOfStep = _hicumesContext.MachineOccupations
        .Include(x => x.ProductionOrder)
        .ThenInclude(productionOrder => productionOrder.SubProductionOrders)
        .Include(x => x.ProductionSteps)
        .Include(machineoccupation => machineoccupation.SubMachineOccupations)
        .Where(x => x.ProductionSteps.Any(step => step.Name == productionStepName && step.ProductId == x.ProductionOrder.ProductId && step.MachineTypeId == machineTypeId));

      var occupationsWithSubOccupations = occupationsOfStep.Where(x => x.SubMachineOccupations.Any()).ToList();

      var orderIdsOfOccupations = occupationsOfStep.Select(x => x.ProductionOrderId).ToList();
      var orderIdsToExclude = new List<long>();
      // Occupations of order with suborders (suboccupations)
      foreach (var parentOccupation in occupationsWithSubOccupations)
      {
        var orderOfParent = parentOccupation.ProductionOrder;
          var allSubOccupations = parentOccupation.SubMachineOccupations.Concat(
            occupationsOfStep.Where(y => y.ProductionOrder != null && orderOfParent != null && y.ProductionOrder.ParentProductionOrderId == orderOfParent.Id && !parentOccupation.SubMachineOccupations.Contains(y)));
          if (orderOfParent != null && allSubOccupations.Count() == orderOfParent.SubProductionOrders.Count)
        {
          orderIdsToExclude.Add(orderOfParent.Id);
        }
      }
      orderIdsToExclude.AddRange(occupationsOfStep.Where(x => x.ProductionOrder.ParentProductionOrderId == null && !x.ProductionOrder.SubProductionOrders.Any()).Select(x => x.ProductionOrder.Id));
      var orders = _hicumesContext.ProductionOrders
        .Include(x => x.Product)
        .AsNoTracking()
        // .Include(x => x.Machineoccupations)
        // .Include(x => x.SubProductionOrders.Where(x => !orderIdsOfOccupations.Contains(x.Id)))
        .Where(x => products.Select(z => z.Id).Contains((long)x.ProductId) && !orderIdsToExclude.Contains(x.Id) && x.ParentProductionOrderId == null);

      if (!string.IsNullOrEmpty(searchTerm))
      {
        orders = orders.Where(x => x.Product.Name.Contains(searchTerm));
      }

      orders = orders.OrderByDescending(x => x.Deadline)
        .Skip((pageNumber - 1) * pageSize)
        .Take(pageSize);

      return await orders.ToListAsync().ConfigureAwait(false);
    }
    catch (Exception e)
    {
      LogException(e);
      throw;
    }
  }

  [HttpGet("machineTypes")]
  public async Task<IEnumerable<CdMachinetype>> MachineTypes([FromQuery] List<long> filteredIds)
  {
    try
    {
      var result = _hicumesContext.CdMachineTypes.OrderBy(x => x.Name)
        .Include(x => x.CdMachines);
        // .Include(x => x.CdProductionSteps);

      if (!filteredIds.Any())
      {
        return await result.ToListAsync().ConfigureAwait(false);
      }
      // var Ids = filteredIds.ToObject<List<long>>();
      return await result.Where(x => filteredIds.Contains(x.Id)).ToListAsync().ConfigureAwait(false);
    }
    catch (Exception e)
    {
      LogException(e);
      throw;
    }
  }

  [HttpGet("assignedMachineOccupations")]
  public async Task<IEnumerable<MachineOccupationDto>> AssignedMachineOccupations()
  {
    var mapper = new MachineOccupationMapper();
    try
    {
      var result = await _hicumesContext.MachineOccupations
        .Include(x => x.ProductionOrder)
        .Include(x => x.ProductionSteps)
        .ThenInclude(x => x.Product)
        .Include(x => x.SubMachineOccupations)
        .ThenInclude(x => x.ProductionOrder)
        .Include(x => x.SubMachineOccupations)
        .ThenInclude(x => x.ProductionSteps)
        .Where(x => x.ParentMachineOccupationId == null)
        .Select(x => mapper.MachineOccupationToMachineOccupationDto(x))
        .ToListAsync().ConfigureAwait(false);


      return result;
    }
    catch (Exception e)
    {
      LogException(e);
      throw;
    }
  }

  [HttpPost("createmachineoccupation")]
  public async Task<ActionResult<IEnumerable<MachineOccupation>>> CreateMachineoccupation([FromBody] ICollection<MachineOccupation> machineOccupations, IWriteRepository<MachineOccupation> repository)
  {
    try
    {

      if (machineOccupations.Count == 0)
        return BadRequest();
      // var objects = machineOccupations.Deserialize<List<MachineOccupation>>();

      var created = new List<MachineOccupation>();

      foreach (var mo in machineOccupations)
      {
        var steps = _hicumesContext.CdProductionSteps.Where(x => mo.ProductionSteps.Select(y => y.Id).Contains(x.Id));
        mo.ProductionSteps = new List<CdProductionStep>(steps);

        var subMachineOccupations = mo.SubMachineOccupations;
        mo.SubMachineOccupations = new List<MachineOccupation>();

        mo.Status = "PLANNED";
        mo.UpdateDateTime = DateTime.Now;
        var createdMachineOccupation = await repository.Create(mo, _hicumesContext);
        created.Add(createdMachineOccupation);

        foreach (var subMachineOccupation in subMachineOccupations)
        {
          subMachineOccupation.ParentMachineOccupationId = createdMachineOccupation.Id;
          subMachineOccupation.ProductionSteps = new List<CdProductionStep>(steps);
          var createdSubMachineOccupation = await repository.Create(subMachineOccupation, _hicumesContext);
          created.Add(createdSubMachineOccupation);
        }
      }
      return Ok(created);
    }
    catch (Exception e)
    {
      LogException(e);

      return StatusCode(StatusCodes.Status500InternalServerError, e.Message);
    }
  }

  [HttpPut("updateMachineoccupations")]
  public async Task<ActionResult<IEnumerable<MachineOccupation>>> UpdateMachineoccupations([FromBody] ICollection<MachineOccupation> machineOccupations,
    IReadRepository<MachineOccupation> readRepository, IWriteRepository<MachineOccupation> writeRepository)
  {
    try
    {
      if (machineOccupations.Count == 0)
        return BadRequest();

      var updatedList = new List<MachineOccupation>();

      foreach (var machineOccupation in machineOccupations)
      {
        var entity = readRepository.GetById(machineOccupation.Id, _hicumesContext);
        if (entity == null) return NotFound("Entity not found: " + machineOccupation);

        if (!string.IsNullOrEmpty(machineOccupation.Name) && machineOccupation.Name != entity.Name)
        {
          entity.Name = machineOccupation.Name;
        }

        if (machineOccupation.MachineId != null)
        {
          entity.MachineId = machineOccupation.MachineId;
        }
        entity.ActualStartDateTime = machineOccupation.ActualStartDateTime;
        entity.ActualEndDateTime = machineOccupation.ActualEndDateTime;

        entity.PlannedStartDateTime = machineOccupation.PlannedStartDateTime;
        entity.PlannedEndDateTime = machineOccupation.PlannedEndDateTime;
        entity.UpdateDateTime = DateTime.Now;
        if (machineOccupation.SubMachineOccupations.Count != 0)
        {
          foreach (var subMachineoccupation in machineOccupation.SubMachineOccupations)
          {
            var subEntity = readRepository.GetById(subMachineoccupation.Id, _hicumesContext);
            if (subEntity == null) continue;
            subEntity.ActualStartDateTime = machineOccupation.ActualStartDateTime;
            subEntity.ActualEndDateTime = machineOccupation.ActualEndDateTime;
            var subEntityUpdated = await writeRepository.Update(subEntity, _hicumesContext);
            updatedList.Add(subEntityUpdated);
          }
        }

        var updated = await writeRepository.Update(entity, _hicumesContext);
        updatedList.Add(updated);
      }
      return Ok(updatedList);
    }
    catch (Exception e)
    {
      const string msg = "Error updating machine occupation record";

      Log.Error("Endpoint UpdateMachineoccupations {@e}", e);

      return StatusCode(StatusCodes.Status500InternalServerError,
        msg);
    }
  }

  [HttpDelete("deleteMachineoccupations")]
  public async Task<ActionResult<IEnumerable<MachineOccupation>>> DeleteMachineoccupations(
    [FromBody] ICollection<long> idsToDelete,
    IReadRepository<MachineOccupation> readRepository, IWriteRepository<MachineOccupation> writeRepository)
  {
    try
    {
      if (!idsToDelete.Any())
        return BadRequest();

      // var IdsToDelete = idsToDelete.Deserialize<List<long>>();
      var entitiesToDelete = new List<MachineOccupation>();

      foreach (var id in idsToDelete)
      {
        var camundaMachineOccupations = _hicumesContext.CamundaMachineOccupations
          .Where(x => x.MachineOccupationId == id);
        if (camundaMachineOccupations.Any())
        {
          var deletedCamundaMachineOccupations = await camundaMachineOccupations.ExecuteDeleteAsync().ConfigureAwait(false);
        }

        var entity = _hicumesContext.MachineOccupations
          .Include(x => x.SubMachineOccupations)
          .ThenInclude(x => x.ProductionSteps)
          .Include(x => x.ProductionSteps)
          .SingleOrDefault(x => x.Id == id);

        if (entity == null) return NotFound("Entity not found: " + id);

        entitiesToDelete.Add(entity);

        if (entity.SubMachineOccupations.Any())
        {
          entitiesToDelete.AddRange(entity.SubMachineOccupations);
        }
      }

      var deleted = await writeRepository.Delete(entitiesToDelete, _hicumesContext);

      return Ok(deleted);
    }
    catch (Exception e)
    {
      const string msg = "Error deleting machine occupation record";

      LogException(e);

      return StatusCode(StatusCodes.Status500InternalServerError, msg);
    }
  }

  #region Logging

  [LoggerMessage(LogLevel.Debug, Message = "Endpoint {CallerMemberName} caused an exception")]
  protected partial void LogException(Exception exception, [CallerMemberName] string callerMemberName = "");


  #endregion

}
