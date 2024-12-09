using Api.Controllers.DTOs;
using HiCuMes.Persistence.Entities;
using Riok.Mapperly.Abstractions;

namespace Api.Controllers.Mappers;

[Mapper]
public partial class MachineOccupationMapper
{
  public partial MachineOccupationDto MachineOccupationToMachineOccupationDto(MachineOccupation machineOccupation);
}
