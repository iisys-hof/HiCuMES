using System;
using System.Collections.Generic;

namespace HiCuMes.Persistence.Entities;

public partial class Cdproductionstepmachineoccupation
{
    public long MachineOccupationsId { get; set; }

    public long ProductionStepsId { get; set; }

    public MachineOccupation MachineOccupations { get; set; } = null!;

    public CdProductionStep ProductionSteps { get; set; } = null!;
}
