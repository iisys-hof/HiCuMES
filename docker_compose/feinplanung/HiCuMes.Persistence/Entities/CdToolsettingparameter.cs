using System;
using System.Collections.Generic;

namespace HiCuMes.Persistence.Entities;

public partial class CdToolsettingparameter
{
    public long Id { get; set; }

    public DateTime? CreateDateTime { get; set; }

    public string? ExternalId { get; set; }

    public DateTime? UpdateDateTime { get; set; }

    public string? VersionNr { get; set; }

    public string? Name { get; set; }

    public string? UnitType { get; set; }

    public long? MachineTypeId { get; set; }

    public long? ToolTypeId { get; set; }

    public  CdMachinetype? MachineType { get; set; }

    public  CdTooltype? ToolType { get; set; }

    public  ICollection<Toolsetting> Toolsettings { get; set; } = new List<Toolsetting>();
}
