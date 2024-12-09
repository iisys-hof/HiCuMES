using System;
using System.Collections.Generic;

namespace HiCuMes.Persistence.Entities;

public partial class CdTooltype
{
    public long Id { get; set; }

    public DateTime? CreateDateTime { get; set; }

    public string? ExternalId { get; set; }

    public DateTime? UpdateDateTime { get; set; }

    public string? VersionNr { get; set; }

    public string? Name { get; set; }

    public int? Number { get; set; }

    public  ICollection<CdProductionStep> CdProductionsteps { get; set; } = new List<CdProductionStep>();

    public  ICollection<CdTool> CdTools { get; set; } = new List<CdTool>();

    public  ICollection<CdToolsettingparameter> CdToolsettingparameters { get; set; } = new List<CdToolsettingparameter>();
}
