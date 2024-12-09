using System;
using System.Collections.Generic;

namespace HiCuMes.Persistence.Entities;

public partial class Datareader
{
    public long Id { get; set; }

    public long? ParserConfigId { get; set; }

    public string? ParserId { get; set; }

    public string? ParserKeyValueConfigs { get; set; }

    public long? ReaderConfigId { get; set; }

    public string? ReaderId { get; set; }

    public string? ReaderKeyValueConfigs { get; set; }

    public  ICollection<Mappinganddatasource> Mappinganddatasources { get; set; } = new List<Mappinganddatasource>();
}
