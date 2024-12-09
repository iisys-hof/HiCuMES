using System;
using System.Collections.Generic;

namespace HiCuMes.Persistence.Entities;

public partial class Csvreaderparserconfig
{
    public long Id { get; set; }

    public string SeparatorChar { get; set; } = null!;
}
