using HiCuMes.Persistence.Context;

namespace HiCuMes.Persistence.DataAccessRepository;

public interface IReadRepository<out TEntity> where TEntity : class
{
  TEntity GetById(long id, HiCuMESDbContext dbContext);
  IQueryable<TEntity> GetAll(HiCuMESDbContext dbContext);
}
