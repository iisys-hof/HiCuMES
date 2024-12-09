using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using HiCuMes.Persistence.Context;

namespace HiCuMes.Persistence.DataAccessRepository.Implementation;

public class DefaultWriteRepository<TEntity> : IWriteRepository<TEntity> where TEntity : class
{

  public  async Task<TEntity> Create(TEntity entity, HiCuMESDbContext dbContext)
  {
    var dbSet = dbContext.Set<TEntity>();
    var added = await dbSet.AddAsync(entity).ConfigureAwait(false);
    await dbContext.SaveChangesAsync().ConfigureAwait(false);

    return added.Entity;
  }

  public  async Task<TEntity> Update(TEntity entity, HiCuMESDbContext dbContext)
  {
    var entityEntry = dbContext.Update(entity);

    await dbContext.SaveChangesAsync().ConfigureAwait(false);

    return entityEntry.Entity;
  }

  public  async Task<IEnumerable<TEntity>> Delete(IEnumerable<TEntity> entitiesToDelete, HiCuMESDbContext dbContext)
  {
    if (!entitiesToDelete.Any()) return null;

    var dbSet = dbContext.Set<TEntity>();
      try
      {
        dbSet.RemoveRange(entitiesToDelete);
        await dbContext.SaveChangesAsync().ConfigureAwait(false);
        return entitiesToDelete;
      }
      catch (InvalidOperationException)
      {
      }
    return null;
  }
}
