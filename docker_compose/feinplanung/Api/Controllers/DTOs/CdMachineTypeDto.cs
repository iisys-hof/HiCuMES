using System.Collections.Generic;

namespace Api.Controllers.DTOs;

public class CdMachineTypeDto
{
  public long Id { get; set; }

  public string Name { get; set; }

  public ICollection<CdMachineDto> CdMachines { get; set; } = new List<CdMachineDto>();

  public ICollection<CdProductionStepDto> CdProductionSteps { get; set; } = new List<CdProductionStepDto>();
}
