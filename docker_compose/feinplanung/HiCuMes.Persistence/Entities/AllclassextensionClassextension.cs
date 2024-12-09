using System;
using System.Collections.Generic;

namespace HiCuMes.Persistence.Entities;

public partial class AllclassextensionClassextension
{
    public long AllClassExtensionId { get; set; }

    public long ClassesFieldId { get; set; }

    public  Allclassextension AllClassExtension { get; set; } = null!;

    public  Classextension ClassesField { get; set; } = null!;
}
