using System;
using System.Collections.Generic;

namespace HiCuMes.Persistence.Entities;

public partial class Camundamachineoccupation
{
    public long Id { get; set; }

    public DateTime? CreateDateTime { get; set; }

    public string? ExternalId { get; set; }

    public DateTime? UpdateDateTime { get; set; }

    public string? VersionNr { get; set; }

    public string? BusinessKey { get; set; }

    public string? CamundaProcessInstanceId { get; set; }

    public ulong IsSubMachineOccupation { get; set; }

    public long? ActiveProductionStepId { get; set; }

    public long? MachineOccupationId { get; set; }

    public  CdProductionStep? ActiveProductionStep { get; set; }

    public  ICollection<Camundasubproductionstep> Camundasubproductionsteps { get; set; } = new List<Camundasubproductionstep>();

    public  MachineOccupation? MachineOccupation { get; set; }
}
