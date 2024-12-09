using System;
using System.Collections.Generic;

namespace HiCuMes.Persistence.Entities;

public partial class Suspensiontype
{
    public long Id { get; set; }

    public DateTime? CreateDateTime { get; set; }

    public string? ExternalId { get; set; }

    public DateTime? UpdateDateTime { get; set; }

    public string? VersionNr { get; set; }

    public string? Description { get; set; }

    public string? Name { get; set; }

    public  ICollection<Timerecord> Timerecords { get; set; } = new List<Timerecord>();
}
