using System;
using System.Collections.Generic;

namespace HiCuMes.Persistence.Entities;

public partial class Camundasubproductionstep
{
    public long Id { get; set; }

    public DateTime? CreateDateTime { get; set; }

    public string? ExternalId { get; set; }

    public DateTime? UpdateDateTime { get; set; }

    public string? VersionNr { get; set; }

    public string? CamundaProcessVariables { get; set; }

    public string? FilledFormField { get; set; }

    public string? FormField { get; set; }

    public string? FormKey { get; set; }

    public string? Name { get; set; }

    public string? TaskDefinitionKey { get; set; }

    public string? TaskId { get; set; }

    public long? CamundaMachineOccupationId { get; set; }

    public long? SubProductionStepId { get; set; }

    public  Camundamachineoccupation? CamundaMachineOccupation { get; set; }

    public  Subproductionstep? SubProductionStep { get; set; }
}
