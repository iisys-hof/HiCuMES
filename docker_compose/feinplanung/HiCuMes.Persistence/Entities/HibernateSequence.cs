using System;
using System.Collections.Generic;

namespace HiCuMes.Persistence.Entities;

public partial class HibernateSequence
{
    public long? NextVal { get; set; }
}
