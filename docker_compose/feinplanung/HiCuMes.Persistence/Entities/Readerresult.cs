using System;
using System.Collections.Generic;

namespace HiCuMes.Persistence.Entities;

public partial class Readerresult
{
    public long Id { get; set; }

    public string? AdditionalData { get; set; }

    public string? Result { get; set; }

    public  ICollection<Mappinganddatasource> Mappinganddatasources { get; set; } = new List<Mappinganddatasource>();
}
