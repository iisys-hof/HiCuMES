using System;
using System.Collections.Generic;

namespace HiCuMes.Persistence.Entities;

public partial class Keyvaluemapproductionorder
{
    public long ProductionOrder { get; set; }

    public string? ValueString { get; set; }

    public string KeyString { get; set; } = null!;

    public  Productionorder ProductionOrderNavigation { get; set; } = null!;
}
