using System;
using System.Collections.Generic;

namespace HiCuMes.Persistence.Entities;

public partial class CdDepartment
{
    public long Id { get; set; }

    public DateTime? CreateDateTime { get; set; }

    public string? ExternalId { get; set; }

    public DateTime? UpdateDateTime { get; set; }

    public string? VersionNr { get; set; }

    public string? Name { get; set; }

    public  ICollection<MachineOccupation> Machineoccupations { get; set; } = new List<MachineOccupation>();
}
