using System;
using System.Collections.Generic;

namespace HiCuMes.Persistence.Entities;

public partial class CdProductionStep
{
    public long Id { get; set; }

    public DateTime? CreateDateTime { get; set; }

    public string? ExternalId { get; set; }

    public DateTime? UpdateDateTime { get; set; }

    public string? VersionNr { get; set; }

    public string? CamundaProcessName { get; set; }

    public string? Name { get; set; }

    public long? ProductionDuration { get; set; }

    public int Sequence { get; set; }

    public long? SetupTime { get; set; }

    public long? MachineTypeId { get; set; }

    public long? ProductId { get; set; }

    public long? ProductionStepInfoId { get; set; }

    public long? ToolTypeId { get; set; }

    public  ICollection<Camundamachineoccupation> Camundamachineoccupations { get; set; } = new List<Camundamachineoccupation>();

    public  CdMachinetype? MachineType { get; set; }

    public  CdProduct? Product { get; set; }

    public  Productionstepinfo? ProductionStepInfo { get; set; }

    public  CdTooltype? ToolType { get; set; }
    public  ICollection<MachineOccupation> MachineOccupations { get; set; } = new List<MachineOccupation>();
    // public  ICollection<MachineoccupationCdProductionstep> MachineoccupationCdProductionsteps { get; set; } = new List<MachineoccupationCdProductionstep>();
}
