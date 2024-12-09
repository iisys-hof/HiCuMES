using System;
using System.Collections.Generic;

namespace HiCuMes.Persistence.Entities;

public partial class MappingconfigurationMappingconfiguration
{
    public long MappingConfigurationId { get; set; }

    public long LoopsId { get; set; }

    public  Mappingconfiguration Loops { get; set; } = null!;

    public  Mappingconfiguration MappingConfiguration { get; set; } = null!;
}
