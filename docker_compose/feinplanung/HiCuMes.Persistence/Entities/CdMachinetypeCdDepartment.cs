using System;
using System.Collections.Generic;

namespace HiCuMes.Persistence.Entities;

public partial class CdMachinetypeCdDepartment
{
    public long MachineType { get; set; }

    public long Department { get; set; }

    public  CdDepartment DepartmentNavigation { get; set; } = null!;

    public  CdMachinetype MachineTypeNavigation { get; set; } = null!;
}
