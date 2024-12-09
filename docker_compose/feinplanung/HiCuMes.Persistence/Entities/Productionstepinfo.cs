using System;
using System.Collections.Generic;

namespace HiCuMes.Persistence.Entities;

public partial class Productionstepinfo
{
    public long Id { get; set; }

    public DateTime? CreateDateTime { get; set; }

    public string? ExternalId { get; set; }

    public DateTime? UpdateDateTime { get; set; }

    public string? VersionNr { get; set; }

    public string? StepGroup { get; set; }

    public string? StepIdent { get; set; }

    public int StepNr { get; set; }

    public int? StepType { get; set; }

    public  ICollection<CdProductionStep> CdProductionsteps { get; set; } = new List<CdProductionStep>();
}
