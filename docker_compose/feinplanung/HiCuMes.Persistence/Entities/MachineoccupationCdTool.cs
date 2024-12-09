using System;
using System.Collections.Generic;

namespace HiCuMes.Persistence.Entities;

public partial class MachineoccupationCdTool
{
    public long MachineOccupation { get; set; }

    public long Tool { get; set; }

    public  MachineOccupation MachineOccupationNavigation { get; set; } = null!;

    public  CdTool ToolNavigation { get; set; } = null!;
}
