using System.Collections.Generic;

namespace Api.Controllers.DTOs;

public class CdProductDto
{
  public long Id { get; set; }

  public string Name { get; set; }

  public string UnitType { get; set; }

  //public  ICollection<CdProductionStepDto> CdProductionSteps { get; set; } = new List<CdProductionStepDto>();

  //public  ICollection<ProductionOrderDto> ProductionOrders { get; set; } = new List<ProductionOrderDto>();
}
