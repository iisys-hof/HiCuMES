using System;
using System.Collections.Generic;

namespace HiCuMes.Persistence.Entities;

public partial class MachineoccupationActivetoolsetting
{
    public long MachineOccupation { get; set; }

    public long ToolSetting { get; set; }

    public  MachineOccupation MachineOccupationNavigation { get; set; } = null!;

    public  Toolsetting ToolSettingNavigation { get; set; } = null!;
}
