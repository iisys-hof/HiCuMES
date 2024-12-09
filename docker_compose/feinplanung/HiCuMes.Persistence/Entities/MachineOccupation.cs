using System;
using System.Collections.Generic;

namespace HiCuMes.Persistence.Entities;

public partial class MachineOccupation
{
    public long Id { get; set; }

    public DateTime? CreateDateTime { get; set; }

    public string? ExternalId { get; set; }

    public DateTime? UpdateDateTime { get; set; }

    public string? VersionNr { get; set; }

    public DateTime? ActualEndDateTime { get; set; }

    public DateTime? ActualStartDateTime { get; set; }

    public string? CamundaProcessName { get; set; }

    public string? Name { get; set; }

    public DateTime? PlannedEndDateTime { get; set; }

    public DateTime? PlannedStartDateTime { get; set; }

    public string? Status { get; set; }

    public long? DepartmentId { get; set; }

    public long? MachineId { get; set; }

    public long? ParentMachineOccupationId { get; set; }

    public long? ProductionOrderId { get; set; }

    public long? ToolId { get; set; }

    public long? TotalProductionNumbersId { get; set; }

    public  ICollection<Bookingentry> Bookingentries { get; set; } = new List<Bookingentry>();

    public  ICollection<Camundamachineoccupation> Camundamachineoccupations { get; set; } = new List<Camundamachineoccupation>();

    public  CdDepartment? Department { get; set; }

    public  ICollection<MachineOccupation> SubMachineOccupations { get; set; } = new List<MachineOccupation>();

    public  CdMachine? Machine { get; set; }

    public  MachineOccupation? ParentMachineOccupation { get; set; }

    public  Productionorder? ProductionOrder { get; set; }

    public  ICollection<Subproductionstep> Subproductionsteps { get; set; } = new List<Subproductionstep>();

    public  ICollection<TimeDuration> TimeDurations { get; set; } = new List<TimeDuration>();

    public  CdTool? Tool { get; set; }

    public  Productionnumber? TotalProductionNumbers { get; set; }
    // public  ICollection<MachineoccupationCdProductionstep> MachineoccupationCdProductionsteps { get; set; } = new List<MachineoccupationCdProductionstep>();
    public  ICollection<CdProductionStep> ProductionSteps { get; set; } = new List<CdProductionStep>();
}
