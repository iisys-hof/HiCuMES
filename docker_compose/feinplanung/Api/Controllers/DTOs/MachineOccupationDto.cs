using System;
using System.Collections.Generic;

namespace Api.Controllers.DTOs;

public class MachineOccupationDto
{
  public long Id { get; set; }

  public DateTime? ActualEndDateTime { get; set; }

  public DateTime? ActualStartDateTime { get; set; }

  public DateTime? PlannedEndDateTime { get; set; }

  public DateTime? PlannedStartDateTime { get; set; }

  public string Name { get; set; }

  public string Status { get; set; }

  public long? MachineId { get; set; }

  public long? ParentMachineOccupationId { get; set; }

  public long? ProductionOrderId { get; set; }

  public ICollection<MachineOccupationDto> SubMachineOccupations { get; set; } = new List<MachineOccupationDto>();

  // public CdMachineDto Machine { get; set; }

  // // public  MachineOccupationDto ParentMachineOccupation { get; set; }

  public ProductionOrderDto ProductionOrder { get; set; }
  public ICollection<CdProductionStepDto> ProductionSteps { get; set; } = new List<CdProductionStepDto>();
}
