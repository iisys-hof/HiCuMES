using System;
using System.Collections.Generic;

namespace HiCuMes.Persistence.Entities;

public partial class CdProduct
{
    public long Id { get; set; }

    public DateTime? CreateDateTime { get; set; }

    public string? ExternalId { get; set; }

    public DateTime? UpdateDateTime { get; set; }

    public string? VersionNr { get; set; }

    public string? Name { get; set; }

    public string? UnitType { get; set; }

    public  ICollection<CdProductionStep> CdProductionsteps { get; set; } = new List<CdProductionStep>();

    public  ICollection<CdProductrelationship> CdProductrelationshipParts { get; set; } = new List<CdProductrelationship>();

    public  ICollection<CdProductrelationship> CdProductrelationshipProducts { get; set; } = new List<CdProductrelationship>();

    public  ICollection<CdQualitycontrolfeature> CdQualitycontrolfeatures { get; set; } = new List<CdQualitycontrolfeature>();

    public  ICollection<Productionorder> Productionorders { get; set; } = new List<Productionorder>();
}
