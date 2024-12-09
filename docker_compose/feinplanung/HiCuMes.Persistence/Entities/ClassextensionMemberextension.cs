using System;
using System.Collections.Generic;

namespace HiCuMes.Persistence.Entities;

public partial class ClassextensionMemberextension
{
    public long ClassExtensionFieldId { get; set; }

    public long MembersId { get; set; }

    public  Classextension ClassExtensionField { get; set; } = null!;
}
