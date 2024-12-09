using System;
using System.Collections.Generic;

namespace HiCuMes.Persistence.Entities;

public partial class Toolsetting
{
    public long Id { get; set; }

    public DateTime? CreateDateTime { get; set; }

    public string? ExternalId { get; set; }

    public DateTime? UpdateDateTime { get; set; }

    public string? VersionNr { get; set; }

    public double Amount { get; set; }

    public string? UnitString { get; set; }

    public long? MachineId { get; set; }

    public long? ToolId { get; set; }

    public long? ToolSettingParameterId { get; set; }

    public  CdMachine? Machine { get; set; }

    public  CdTool? Tool { get; set; }

    public  CdToolsettingparameter? ToolSettingParameter { get; set; }
}
