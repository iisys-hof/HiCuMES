using System;
using System.Collections.Generic;

namespace HiCuMes.Persistence.Entities;

public partial class Setup
{
    public long Id { get; set; }

    public DateTime? CreateDateTime { get; set; }

    public string? ExternalId { get; set; }

    public DateTime? UpdateDateTime { get; set; }

    public string? VersionNr { get; set; }

    public long? SubProductionStepId { get; set; }

    public  Subproductionstep? SubProductionStep { get; set; }
}
