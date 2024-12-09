using System;
using System.Collections.Generic;

namespace HiCuMes.Persistence.Entities;

public partial class MachineoccupationMachineoccupation
{
    public long MachineOccupationId { get; set; }

    public long SubMachineOccupationsId { get; set; }

    public  MachineOccupation MachineOccupation { get; set; } = null!;

    public  MachineOccupation SubMachineOccupations { get; set; } = null!;
}
