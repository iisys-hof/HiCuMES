using System.Linq;
using HiCuMes.Persistence.Context;

namespace HiCuMes.Persistence.DataAccessRepository.Implementation;

public class DefaultReadRepository<TEntity> : IReadRepository<TEntity> where TEntity : class
{
  public  TEntity GetById(long id, HiCuMESDbContext dbContext)
  {
    var dbSet = dbContext.Set<TEntity>();

    return dbSet.Find(id);
  }

  public  IQueryable<TEntity> GetAll(HiCuMESDbContext dbContext)
  {
    var dbSet = dbContext.Set<TEntity>();
    return dbSet;
  }
}
