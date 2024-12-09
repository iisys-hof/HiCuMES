using System;
using System.Collections.Generic;

namespace HiCuMes.Persistence.Entities;

public partial class CdQualitycontrolfeature
{
    public long Id { get; set; }

    public DateTime? CreateDateTime { get; set; }

    public string? ExternalId { get; set; }

    public DateTime? UpdateDateTime { get; set; }

    public string? VersionNr { get; set; }

    public string? Name { get; set; }

    public string? Tolerance { get; set; }

    public string? Value { get; set; }

    public long? ProductId { get; set; }

    public  CdProduct? Product { get; set; }

    public  ICollection<Qualitymanagement> Qualitymanagements { get; set; } = new List<Qualitymanagement>();
}
