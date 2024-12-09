using System;
using System.Collections.Generic;

namespace HiCuMes.Persistence.Entities;

public partial class Overheadcost
{
    public long Id { get; set; }

    public DateTime? CreateDateTime { get; set; }

    public string? ExternalId { get; set; }

    public DateTime? UpdateDateTime { get; set; }

    public string? VersionNr { get; set; }

    public string? Note { get; set; }

    public long? TimeDuration { get; set; }

    public long? OverheadCostCenterId { get; set; }

    public long? TimeRecordId { get; set; }

    public long? UserId { get; set; }

    public  ICollection<Bookingentry> Bookingentries { get; set; } = new List<Bookingentry>();

    public  CdOverheadcostcenter? OverheadCostCenter { get; set; }

    public  Timerecord? TimeRecord { get; set; }

    public  User? User { get; set; }
}
