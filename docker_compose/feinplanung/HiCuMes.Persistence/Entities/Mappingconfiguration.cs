using System;
using System.Collections.Generic;

namespace HiCuMes.Persistence.Entities;

public partial class Mappingconfiguration
{
    public long Id { get; set; }

    public string? InputSelector { get; set; }

    public string? OutputSelector { get; set; }

    public string? XsltRules { get; set; }

    public  ICollection<Mappinganddatasource> Mappinganddatasources { get; set; } = new List<Mappinganddatasource>();
}
