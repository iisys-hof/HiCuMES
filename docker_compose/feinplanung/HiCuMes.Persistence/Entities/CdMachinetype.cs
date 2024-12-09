using System;
using System.Collections.Generic;

namespace HiCuMes.Persistence.Entities;

public partial class CdMachinetype
{
    public long Id { get; set; }

    public DateTime? CreateDateTime { get; set; }

    public string? ExternalId { get; set; }

    public DateTime? UpdateDateTime { get; set; }

    public string? VersionNr { get; set; }

    public string? Name { get; set; }

    public  ICollection<CdMachine> CdMachines { get; set; } = new List<CdMachine>();

    public  ICollection<CdProductionStep> CdProductionsteps { get; set; } = new List<CdProductionStep>();

    public  ICollection<CdToolsettingparameter> CdToolsettingparameters { get; set; } = new List<CdToolsettingparameter>();
}
