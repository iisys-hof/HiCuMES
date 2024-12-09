using System;
using System.Collections.Generic;

namespace HiCuMes.Persistence.Entities;

public partial class Machinestatushistory
{
    public long Id { get; set; }

    public DateTime? CreateDateTime { get; set; }

    public string? ExternalId { get; set; }

    public DateTime? UpdateDateTime { get; set; }

    public string? VersionNr { get; set; }

    public DateTime? StartDateTime { get; set; }

    public long? MachineId { get; set; }

    public long? MachineStatusId { get; set; }

    public  CdMachine? Machine { get; set; }

    public  Machinestatus? MachineStatus { get; set; }
}
