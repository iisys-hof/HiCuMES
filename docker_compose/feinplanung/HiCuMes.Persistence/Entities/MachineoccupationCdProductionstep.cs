using System;
using System.Collections.Generic;

namespace HiCuMes.Persistence.Entities;

public partial class MachineoccupationCdProductionstep
{
    public long MachineOccupationId { get; set; }

    public long ProductionStepId { get; set; }

    public MachineOccupation MachineOccupationNavigation { get; set; } = null!;

    public CdProductionStep ProductionStepNavigation { get; set; } = null!;
}
