using System;
using System.Collections.Generic;

namespace HiCuMes.Persistence.Entities;

public partial class User
{
    public long Id { get; set; }

    public DateTime? CreateDateTime { get; set; }

    public string? ExternalId { get; set; }

    public DateTime? UpdateDateTime { get; set; }

    public string? VersionNr { get; set; }

    public string? FullUserName { get; set; }

    public string? UserName { get; set; }

    public  ICollection<Bookingentry> Bookingentries { get; set; } = new List<Bookingentry>();

    public  ICollection<Overheadcost> Overheadcosts { get; set; } = new List<Overheadcost>();

    public  ICollection<Timerecord> Timerecords { get; set; } = new List<Timerecord>();
}
