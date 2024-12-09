using System;
using System.Collections.Generic;

namespace HiCuMes.Persistence.Entities;

public partial class Customerorder
{
    public long Id { get; set; }

    public DateTime? CreateDateTime { get; set; }

    public string? ExternalId { get; set; }

    public DateTime? UpdateDateTime { get; set; }

    public string? VersionNr { get; set; }

    public string? CustomerName { get; set; }

    public DateTime? Deadline { get; set; }

    public string? Name { get; set; }

    public int Priority { get; set; }

    public  ICollection<Productionorder> Productionorders { get; set; } = new List<Productionorder>();
}
