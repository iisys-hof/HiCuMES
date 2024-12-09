using System;
using System.Collections.Generic;

namespace HiCuMes.Persistence.Entities;

public partial class CdProductionstepCdToolsettingparameter
{
    public long ProductionStep { get; set; }

    public long ToolSettingParameter { get; set; }

    public  CdProductionStep ProductionStepNavigation { get; set; } = null!;

    public  CdToolsettingparameter ToolSettingParameterNavigation { get; set; } = null!;
}
