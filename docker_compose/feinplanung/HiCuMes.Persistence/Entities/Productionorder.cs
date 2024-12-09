using System;
using System.Collections.Generic;

namespace HiCuMes.Persistence.Entities;

public partial class Productionorder
{
    public long Id { get; set; }

    public DateTime? CreateDateTime { get; set; }

    public string? ExternalId { get; set; }

    public DateTime? UpdateDateTime { get; set; }

    public string? VersionNr { get; set; }

    public DateTime? Deadline { get; set; }

    public double Amount { get; set; }

    public string? UnitString { get; set; }

    public string? Name { get; set; }

    public int Priority { get; set; }

    public string? Status { get; set; }

    public long? CustomerOrderId { get; set; }

    public long? ParentProductionOrderId { get; set; }

    public long? ProductId { get; set; }

    public  Customerorder? CustomerOrder { get; set; }

    public  ICollection<Productionorder> SubProductionOrders { get; set; } = new List<Productionorder>();

    public  ICollection<Keyvaluemapproductionorder> Keyvaluemapproductionorders { get; set; } = new List<Keyvaluemapproductionorder>();

    public  ICollection<MachineOccupation> MachineOccupations { get; set; } = new List<MachineOccupation>();

    public  Productionorder? ParentProductionOrder { get; set; }

    public  CdProduct? Product { get; set; }
}
