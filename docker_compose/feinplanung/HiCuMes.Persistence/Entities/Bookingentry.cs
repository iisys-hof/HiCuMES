using System;
using System.Collections.Generic;

namespace HiCuMes.Persistence.Entities;

public partial class Bookingentry
{
    public long Id { get; set; }

    public DateTime? CreateDateTime { get; set; }

    public string? ExternalId { get; set; }

    public DateTime? UpdateDateTime { get; set; }

    public string? VersionNr { get; set; }

    public int? BookingState { get; set; }

    public ulong IsStepTime { get; set; }

    public string? Message { get; set; }

    public string? Response { get; set; }

    public long? MachineOccupationId { get; set; }

    public long? OverheadCostId { get; set; }

    public long? SubProductionStepId { get; set; }

    public long? UserId { get; set; }

    public  MachineOccupation? MachineOccupation { get; set; }

    public  Overheadcost? OverheadCost { get; set; }

    public  Subproductionstep? SubProductionStep { get; set; }

    public  User? User { get; set; }
}
