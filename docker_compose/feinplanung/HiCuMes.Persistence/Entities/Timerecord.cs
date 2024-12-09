using System;
using System.Collections.Generic;

namespace HiCuMes.Persistence.Entities;

public partial class Timerecord
{
    public long Id { get; set; }

    public DateTime? CreateDateTime { get; set; }

    public string? ExternalId { get; set; }

    public DateTime? UpdateDateTime { get; set; }

    public string? VersionNr { get; set; }

    public DateTime? EndDateTime { get; set; }

    public DateTime? StartDateTime { get; set; }

    public long? ResponseUserId { get; set; }

    public long? SubProductionStepId { get; set; }

    public long? SuspensionTypeId { get; set; }

    public long? TimeRecordTypeId { get; set; }

    public  ICollection<Overheadcost> Overheadcosts { get; set; } = new List<Overheadcost>();

    public  User? ResponseUser { get; set; }

    public  Subproductionstep? SubProductionStep { get; set; }

    public  Suspensiontype? SuspensionType { get; set; }

    public  Timerecordtype? TimeRecordType { get; set; }
}
