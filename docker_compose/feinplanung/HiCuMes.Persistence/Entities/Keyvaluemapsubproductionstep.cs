using System;
using System.Collections.Generic;

namespace HiCuMes.Persistence.Entities;

public partial class Keyvaluemapsubproductionstep
{
    public long SubProductionStep { get; set; }

    public string? ValueString { get; set; }

    public string KeyString { get; set; } = null!;

    public  Subproductionstep SubProductionStepNavigation { get; set; } = null!;
}
