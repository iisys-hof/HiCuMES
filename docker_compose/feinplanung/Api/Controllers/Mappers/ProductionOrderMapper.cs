using System.Linq;
using Api.Controllers.DTOs;
using HiCuMes.Persistence.Entities;
using Riok.Mapperly.Abstractions;

namespace Api.Controllers.Mappers;

[Mapper]
public partial class ProductionOrderMapper
{
  public partial ProductionOrderDto ProductionOrderToProductionOrderDto(Productionorder productionOrder);
}
