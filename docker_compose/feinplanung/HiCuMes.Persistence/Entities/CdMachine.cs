using System;
using System.Collections.Generic;

namespace HiCuMes.Persistence.Entities;

public partial class CdMachine
{
    public long Id { get; set; }

    public DateTime? CreateDateTime { get; set; }

    public string? ExternalId { get; set; }

    public DateTime? UpdateDateTime { get; set; }

    public string? VersionNr { get; set; }

    public string? Name { get; set; }

    public long? MachineTypeId { get; set; }

    public  CdMachinetype? MachineType { get; set; }

    public  ICollection<MachineOccupation> Machineoccupations { get; set; } = new List<MachineOccupation>();

    public  ICollection<Machinestatushistory> Machinestatushistories { get; set; } = new List<Machinestatushistory>();

    public  ICollection<Toolsetting> Toolsettings { get; set; } = new List<Toolsetting>();
}
