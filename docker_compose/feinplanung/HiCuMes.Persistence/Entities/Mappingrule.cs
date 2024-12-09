using System;
using System.Collections.Generic;

namespace HiCuMes.Persistence.Entities;

public partial class Mappingrule
{
    public long Id { get; set; }

    public string? InputSelector { get; set; }

    public string? OutputSelector { get; set; }

    public string? Rule { get; set; }

    public long UiId { get; set; }
}
