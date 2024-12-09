using System;
using System.Collections.Generic;

namespace HiCuMes.Persistence.Entities;

public partial class Productionnumber
{
    public long Id { get; set; }

    public DateTime? CreateDateTime { get; set; }

    public string? ExternalId { get; set; }

    public DateTime? UpdateDateTime { get; set; }

    public string? VersionNr { get; set; }

    public double? AcceptedAmount { get; set; }

    public string? AcceptedUnitString { get; set; }

    public double? RejectedAmount { get; set; }

    public string? RejectedUnitString { get; set; }

    public long? SubProductionStepId { get; set; }

    public  ICollection<MachineOccupation> Machineoccupations { get; set; } = new List<MachineOccupation>();

    public  Subproductionstep? SubProductionStep { get; set; }
}
