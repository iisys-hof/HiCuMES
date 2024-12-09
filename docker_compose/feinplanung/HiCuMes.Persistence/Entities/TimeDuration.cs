using System;
using System.Collections.Generic;

namespace HiCuMes.Persistence.Entities;

public partial class TimeDuration
{
    public long MachineOccupation { get; set; }

    public long? Duration { get; set; }

    public string TimeType { get; set; } = null!;

    public  MachineOccupation MachineOccupationNavigation { get; set; } = null!;
}
