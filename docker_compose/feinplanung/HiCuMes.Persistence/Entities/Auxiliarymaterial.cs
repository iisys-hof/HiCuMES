﻿using System;
using System.Collections.Generic;

namespace HiCuMes.Persistence.Entities;

public partial class Auxiliarymaterial
{
    public long Id { get; set; }

    public DateTime? CreateDateTime { get; set; }

    public string? ExternalId { get; set; }

    public DateTime? UpdateDateTime { get; set; }

    public string? VersionNr { get; set; }

    public double Amount { get; set; }

    public string? UnitString { get; set; }

    public long? SubProductionStepId { get; set; }

    public  Subproductionstep? SubProductionStep { get; set; }
}
