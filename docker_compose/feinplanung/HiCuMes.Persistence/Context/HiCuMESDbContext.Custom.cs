using HiCuMes.Persistence.Entities;
using Microsoft.EntityFrameworkCore;

namespace HiCuMes.Persistence.Context;

public partial class HiCuMESDbContext
{
  partial void OnModelCreatingPartial(ModelBuilder modelBuilder)
  {
    modelBuilder.Entity<MachineOccupation>()
      .HasMany(x => x.ProductionSteps)
      .WithMany(x => x.MachineOccupations)
      .UsingEntity<MachineoccupationCdProductionstep>();
  }
 }
