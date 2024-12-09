using System;
using System.Collections.Generic;

namespace Api.Controllers.DTOs;

public class ProductionOrderDto
{
  public long Id { get; set; }

  public DateTime? Deadline { get; set; }

  public double Amount { get; set; }

  public string UnitString { get; set; }

  public string Name { get; set; }

  public string Status { get; set; }

  public long? ParentProductionOrderId { get; set; }

  public long? ProductId { get; set; }

  // public  ProductionOrderDto ParentProductionOrder { get; set; }

  // public  ICollection<MachineOccupation> MachineOccupations { get; set; } = new List<MachineOccupation>();
  public ICollection<ProductionOrderDto> SubProductionOrders { get; set; } = new List<ProductionOrderDto>();

  public CdProductDto Product { get; set; }
}
