using System;
using System.Collections.Generic;

namespace HiCuMes.Persistence.Entities;

public partial class TimeDurationsstep
{
    public long SubProductionStep { get; set; }

    public long? Duration { get; set; }

    public string TimeType { get; set; } = null!;

    public  Subproductionstep SubProductionStepNavigation { get; set; } = null!;
}
