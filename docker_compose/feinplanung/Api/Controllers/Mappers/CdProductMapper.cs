using Api.Controllers.DTOs;
using HiCuMes.Persistence.Entities;
using Riok.Mapperly.Abstractions;

namespace Api.Controllers.Mappers;

[Mapper]
public partial class CdProductMapper
{
  public partial CdProductDto CdProductToCdProductDto(CdProduct cdProduct);
}
