using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq.Expressions;
using System.Threading.Tasks;
using HiCuMes.Persistence.Context;

namespace HiCuMes.Persistence.DataAccessRepository;

public interface IWriteRepository<TEntity> where TEntity : class
{
  Task<TEntity> Create(TEntity entity, HiCuMESDbContext dbContext);
  Task<TEntity> Update(TEntity entity, HiCuMESDbContext dbContext);
  Task<IEnumerable<TEntity>> Delete(IEnumerable<TEntity> entitiesToDelete, HiCuMESDbContext dbContext);

}
