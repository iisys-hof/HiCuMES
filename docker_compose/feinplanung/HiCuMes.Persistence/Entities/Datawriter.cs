using System;
using System.Collections.Generic;

namespace HiCuMes.Persistence.Entities;

public partial class Datawriter
{
    public long Id { get; set; }

    public long? ParserConfigId { get; set; }

    public string? ParserId { get; set; }

    public string? ParserKeyValueConfigs { get; set; }

    public long? WriterConfigId { get; set; }

    public string? WriterId { get; set; }

    public string? WriterKeyValueConfigs { get; set; }

    public  ICollection<Mappinganddatasource> Mappinganddatasources { get; set; } = new List<Mappinganddatasource>();
}
