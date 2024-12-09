using System;
using System.Collections.Generic;

namespace HiCuMes.Persistence.Entities;

public partial class Mappinganddatasource
{
    public long Id { get; set; }

    public string? ExternalId { get; set; }

    public string Name { get; set; } = null!;

    public long DataReaderId { get; set; }

    public long DataWriterId { get; set; }

    public long MappingConfigurationId { get; set; }

    public long ReaderResultId { get; set; }

    public  Datareader DataReader { get; set; } = null!;

    public  Datawriter DataWriter { get; set; } = null!;

    public  Mappingconfiguration MappingConfiguration { get; set; } = null!;

    public  Readerresult ReaderResult { get; set; } = null!;
}
