using System;
using System.Collections.Generic;

namespace HiCuMes.Persistence.Entities;

public partial class ProductionorderNote
{
    public long ProductionOrderId { get; set; }

    public long NotesId { get; set; }

    public  Note Notes { get; set; } = null!;

    public  Productionorder ProductionOrder { get; set; } = null!;
}
