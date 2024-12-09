using System;
using System.Collections.Generic;

namespace Api.Controllers.DTOs;

public class CdProductionStepDto
{
  public long Id { get; set; }

  public string Name { get; set; }

  public long? ProductionDuration { get; set; }

  public int Sequence { get; set; }

  public long? SetupTime { get; set; }

  public long? MachineTypeId { get; set; }

  public long? ProductId { get; set; }

  public CdMachineTypeDto MachineType { get; set; }

  public CdProductDto Product { get; set; }
  // public  ICollection<MachineOccupationDto> MachineOccupations { get; set; } = new List<MachineOccupationDto>();
}
