using System;
using System.Collections.Generic;

namespace HiCuMes.Persistence.Entities;

public partial class Hicumessetting
{
    public long Id { get; set; }

    public DateTime? CreateDateTime { get; set; }

    public string? ExternalId { get; set; }

    public DateTime? UpdateDateTime { get; set; }

    public string? VersionNr { get; set; }

    public ulong DisableBooking { get; set; }
}
