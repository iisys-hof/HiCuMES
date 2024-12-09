using System;
using System.Collections.Generic;

namespace HiCuMes.Persistence.Entities;

public partial class MappingconfigurationMappingrule
{
    public long MappingConfigurationId { get; set; }

    public long MappingsId { get; set; }

    public  Mappingconfiguration MappingConfiguration { get; set; } = null!;

    public  Mappingrule Mappings { get; set; } = null!;
}
