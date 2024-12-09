using System;
using System.Collections.Generic;

namespace HiCuMes.Persistence.Entities;

public partial class Subproductionstep
{
    public long Id { get; set; }

    public DateTime? CreateDateTime { get; set; }

    public string? ExternalId { get; set; }

    public DateTime? UpdateDateTime { get; set; }

    public string? VersionNr { get; set; }

    public string? Note { get; set; }

    public int? SubmitType { get; set; }

    public int? Type { get; set; }

    public long? MachineOccupationId { get; set; }

    public  ICollection<Auxiliarymaterial> Auxiliarymaterials { get; set; } = new List<Auxiliarymaterial>();

    public  ICollection<Bookingentry> Bookingentries { get; set; } = new List<Bookingentry>();

    public  ICollection<Camundasubproductionstep> Camundasubproductionsteps { get; set; } = new List<Camundasubproductionstep>();

    public  ICollection<Keyvaluemapsubproductionstep> Keyvaluemapsubproductionsteps { get; set; } = new List<Keyvaluemapsubproductionstep>();

    public  MachineOccupation? MachineOccupation { get; set; }

    public  ICollection<Productionnumber> Productionnumbers { get; set; } = new List<Productionnumber>();

    public  ICollection<Qualitymanagement> Qualitymanagements { get; set; } = new List<Qualitymanagement>();

    public  ICollection<Setup> Setups { get; set; } = new List<Setup>();

    public  ICollection<TimeDurationsstep> TimeDurationssteps { get; set; } = new List<TimeDurationsstep>();

    public  ICollection<Timerecord> Timerecords { get; set; } = new List<Timerecord>();
}
