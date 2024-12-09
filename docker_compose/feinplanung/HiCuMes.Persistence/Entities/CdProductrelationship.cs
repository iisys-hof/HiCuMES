using System;
using System.Collections.Generic;

namespace HiCuMes.Persistence.Entities;

public partial class CdProductrelationship
{
    public long Id { get; set; }

    public DateTime? CreateDateTime { get; set; }

    public string? ExternalId { get; set; }

    public DateTime? UpdateDateTime { get; set; }

    public string? VersionNr { get; set; }

    public string? ExtIdProductionOrder { get; set; }

    public double Amount { get; set; }

    public string? UnitString { get; set; }

    public long? PartId { get; set; }

    public long? ProductId { get; set; }

    public  CdProduct? Part { get; set; }

    public  CdProduct? Product { get; set; }
}
