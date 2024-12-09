using System;
using System.Collections.Generic;

namespace Api.Controllers.DTOs;

public class CdMachineDto
{
  public long Id { get; set; }

  public DateTime? CreateDateTime { get; set; }

  public DateTime? UpdateDateTime { get; set; }

  public string Name { get; set; }

  public long? MachineTypeId { get; set; }

  public ICollection<MachineOccupationDto> MachineOccupations { get; set; } = new List<MachineOccupationDto>();

  // public  CdMachineType MachineType { get; set; }
}
