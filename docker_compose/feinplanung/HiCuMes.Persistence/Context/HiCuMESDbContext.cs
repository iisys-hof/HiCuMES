using System;
using System.Collections.Generic;
using HiCuMes.Persistence.Entities;
using Microsoft.EntityFrameworkCore;
using Pomelo.EntityFrameworkCore.MySql.Scaffolding.Internal;

namespace HiCuMes.Persistence.Context;

public partial class HiCuMESDbContext : DbContext
{
    public HiCuMESDbContext()
    {
    }

    public HiCuMESDbContext(DbContextOptions<HiCuMESDbContext> options)
        : base(options)
    {
    }

    public DbSet<Allclassextension> AllClassExtensions => Set<Allclassextension>();

    public DbSet<AllclassextensionClassextension> AllClassExtensionClassExtensions => Set<AllclassextensionClassextension>();

    public DbSet<Auxiliarymaterial> AuxiliaryMaterials => Set<Auxiliarymaterial>();

    public DbSet<Bookingentry> BookingEntries => Set<Bookingentry>();

    public DbSet<Camundamachineoccupation> CamundaMachineOccupations => Set<Camundamachineoccupation>();

    public DbSet<Camundasubproductionstep> CamundaSubProductionSteps => Set<Camundasubproductionstep>();

    public DbSet<CdDepartment> CdDepartments => Set<CdDepartment>();

    public DbSet<CdMachine> CdMachines => Set<CdMachine>();

    public DbSet<CdMachinetype> CdMachineTypes => Set<CdMachinetype>();

    public DbSet<CdMachinetypeCdDepartment> CdMachineTypeCdDepartments => Set<CdMachinetypeCdDepartment>();

    public DbSet<CdOverheadcostcenter> CdOverheadCostCenters => Set<CdOverheadcostcenter>();

    public DbSet<CdProduct> CdProducts => Set<CdProduct>();

    public DbSet<CdProductionStep> CdProductionSteps => Set<CdProductionStep>();

    public DbSet<CdProductionstepCdToolsettingparameter> CdProductionstepCdToolsettingparameters => Set<CdProductionstepCdToolsettingparameter>();

    public DbSet<CdProductrelationship> CdProductrelationships => Set<CdProductrelationship>();

    public DbSet<CdQualitycontrolfeature> CdQualitycontrolfeatures => Set<CdQualitycontrolfeature>();

    public DbSet<CdTool> CdTools => Set<CdTool>();

    public DbSet<CdToolsettingparameter> CdToolsettingparameters => Set<CdToolsettingparameter>();

    public DbSet<CdTooltype> CdTooltypes => Set<CdTooltype>();

    public DbSet<Classextension> Classextensions => Set<Classextension>();

    public DbSet<ClassextensionMemberextension> ClassextensionMemberextensions => Set<ClassextensionMemberextension>();

    public DbSet<Csvreaderparserconfig> Csvreaderparserconfigs => Set<Csvreaderparserconfig>();

    public DbSet<Customerorder> Customerorders => Set<Customerorder>();

    public DbSet<Datareader> Datareaders => Set<Datareader>();

    public DbSet<Datawriter> Datawriters => Set<Datawriter>();

    public DbSet<HibernateSequence> HibernateSequences => Set<HibernateSequence>();

    public DbSet<Hicumessetting> Hicumessettings => Set<Hicumessetting>();

    public DbSet<Keyvaluemapproductionorder> Keyvaluemapproductionorders => Set<Keyvaluemapproductionorder>();

    public DbSet<Keyvaluemapsubproductionstep> Keyvaluemapsubproductionsteps => Set<Keyvaluemapsubproductionstep>();

    public DbSet<MachineOccupation> MachineOccupations => Set<MachineOccupation>();

    public DbSet<MachineoccupationActivetoolsetting> MachineoccupationActivetoolsettings => Set<MachineoccupationActivetoolsetting>();

    public DbSet<MachineoccupationCdProductionstep> MachineoccupationCdProductionsteps => Set<MachineoccupationCdProductionstep>();

    public DbSet<MachineoccupationCdTool> MachineoccupationCdTools => Set<MachineoccupationCdTool>();

    public DbSet<MachineoccupationMachineoccupation> MachineoccupationMachineoccupations => Set<MachineoccupationMachineoccupation>();

    public DbSet<Machinestatus> Machinestatuses => Set<Machinestatus>();

    public DbSet<Machinestatushistory> Machinestatushistories => Set<Machinestatushistory>();

    public DbSet<Mappinganddatasource> Mappinganddatasources => Set<Mappinganddatasource>();

    public DbSet<Mappingconfiguration> Mappingconfigurations => Set<Mappingconfiguration>();

    public DbSet<MappingconfigurationMappingconfiguration> MappingconfigurationMappingconfigurations => Set<MappingconfigurationMappingconfiguration>();

    public DbSet<MappingconfigurationMappingrule> MappingconfigurationMappingrules => Set<MappingconfigurationMappingrule>();

    public DbSet<Mappingrule> Mappingrules => Set<Mappingrule>();

    public DbSet<Note> Notes => Set<Note>();

    public DbSet<Overheadcost> Overheadcosts => Set<Overheadcost>();

    public DbSet<Productionnumber> Productionnumbers => Set<Productionnumber>();

    public DbSet<Productionorder> ProductionOrders => Set<Productionorder>();

    public DbSet<ProductionorderNote> ProductionorderNotes => Set<ProductionorderNote>();

    public DbSet<ProductionorderProductionorder> ProductionorderProductionorders => Set<ProductionorderProductionorder>();

    public DbSet<Productionstepinfo> Productionstepinfos => Set<Productionstepinfo>();

    public DbSet<Qualitymanagement> Qualitymanagements => Set<Qualitymanagement>();

    public DbSet<Readerresult> Readerresults => Set<Readerresult>();

    public DbSet<Setup> Setups => Set<Setup>();

    public DbSet<Singlefilereaderconfig> Singlefilereaderconfigs => Set<Singlefilereaderconfig>();

    public DbSet<Subproductionstep> Subproductionsteps => Set<Subproductionstep>();

    public DbSet<Suspensiontype> Suspensiontypes => Set<Suspensiontype>();

    public DbSet<TimeDuration> TimeDurations => Set<TimeDuration>();

    public DbSet<TimeDurationsstep> TimeDurationssteps => Set<TimeDurationsstep>();

    public DbSet<Timerecord> Timerecords => Set<Timerecord>();

    public DbSet<Timerecordtype> Timerecordtypes => Set<Timerecordtype>();

    public DbSet<Toolsetting> Toolsettings => Set<Toolsetting>();

    public DbSet<User> Users => Set<User>();

//     protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
// #warning To protect potentially sensitive information in your connection string, you should move it out of source code. You can avoid scaffolding the connection string by using the Name= syntax to read it from configuration - see https://go.microsoft.com/fwlink/?linkid=2131148. For more guidance on storing connection strings, see https://go.microsoft.com/fwlink/?LinkId=723263.
//         => optionsBuilder.UseMySql("server=localhost;database=hicumes;uid=hicumes;pwd=PASSWORD", Microsoft.EntityFrameworkCore.ServerVersion.Parse("8.3.0-mysql"));

    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        modelBuilder
            .UseCollation("utf8mb4_0900_ai_ci")
            .HasCharSet("utf8mb4");

        modelBuilder.Entity<Allclassextension>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("PRIMARY");

            entity.ToTable("AllClassExtension");

            entity.Property(e => e.Id)
                .ValueGeneratedOnAdd()
                .HasColumnName("id");
        });

        modelBuilder.Entity<AllclassextensionClassextension>(entity =>
        {
            entity
                .HasNoKey()
                .ToTable("AllClassExtension_ClassExtension");

            entity.HasIndex(e => e.AllClassExtensionId, "FKsl9nqf0opi6qcsfkvouc3uvls");

            entity.HasIndex(e => e.ClassesFieldId, "UK_4ov0sqd9pv36j7s2jpffgpk9x").IsUnique();

            entity.Property(e => e.AllClassExtensionId).HasColumnName("AllClassExtension_id");
            entity.Property(e => e.ClassesFieldId).HasColumnName("classes_fieldId");

            entity.HasOne(d => d.AllClassExtension).WithMany()
                .HasForeignKey(d => d.AllClassExtensionId)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("FKsl9nqf0opi6qcsfkvouc3uvls");

            entity.HasOne(d => d.ClassesField).WithOne()
                .HasForeignKey<AllclassextensionClassextension>(d => d.ClassesFieldId)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("FKbmts0lnwomhe9542su3qj3tnf");
        });

        modelBuilder.Entity<Auxiliarymaterial>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("PRIMARY");

            entity.ToTable("AuxiliaryMaterials");

            entity.HasIndex(e => e.SubProductionStepId, "FKf7sx5xmth3q2iahypud0egnwl");

            entity.HasIndex(e => e.ExternalId, "IDXnkycpffhxwyirhuom4w3wj480").IsUnique();

            entity.Property(e => e.Id)
                .ValueGeneratedOnAdd()
                .HasColumnName("id");
            entity.Property(e => e.Amount).HasColumnName("amount");
            entity.Property(e => e.CreateDateTime)
                .HasMaxLength(6)
                .HasColumnName("createDateTime");
            entity.Property(e => e.ExternalId).HasColumnName("externalId");
            entity.Property(e => e.SubProductionStepId).HasColumnName("subProductionStep_id");
            entity.Property(e => e.UnitString)
                .HasMaxLength(255)
                .HasColumnName("unitString");
            entity.Property(e => e.UpdateDateTime)
                .HasMaxLength(6)
                .HasColumnName("updateDateTime");
            entity.Property(e => e.VersionNr)
                .HasMaxLength(255)
                .HasColumnName("versionNr");

            entity.HasOne(d => d.SubProductionStep).WithMany(p => p.Auxiliarymaterials)
                .HasForeignKey(d => d.SubProductionStepId)
                .HasConstraintName("FKf7sx5xmth3q2iahypud0egnwl");
        });

        modelBuilder.Entity<Bookingentry>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("PRIMARY");

            entity.ToTable("BookingEntry");

            entity.HasIndex(e => e.OverheadCostId, "FK2su0kingqav2ycdmq5nofvv9g");

            entity.HasIndex(e => e.MachineOccupationId, "FKe57614byrdted49wqd2p7dji6");

            entity.HasIndex(e => e.SubProductionStepId, "FKqltonrbscwefmvvccv11pq8ij");

            entity.HasIndex(e => e.UserId, "FKrk8p1g8i0wjcu45vhvbhdx8t6");

            entity.HasIndex(e => e.ExternalId, "IDXi2cw9fgwss9p2pddyw584315w").IsUnique();

            entity.Property(e => e.Id)
                .ValueGeneratedOnAdd()
                .HasColumnName("id");
            entity.Property(e => e.BookingState).HasColumnName("bookingState");
            entity.Property(e => e.CreateDateTime)
                .HasMaxLength(6)
                .HasColumnName("createDateTime");
            entity.Property(e => e.ExternalId).HasColumnName("externalId");
            entity.Property(e => e.IsStepTime)
                .HasColumnType("bit(1)")
                .HasColumnName("isStepTime");
            entity.Property(e => e.MachineOccupationId).HasColumnName("machineOccupation_id");
            entity.Property(e => e.Message).HasColumnName("message");
            entity.Property(e => e.OverheadCostId).HasColumnName("overheadCost_id");
            entity.Property(e => e.Response).HasColumnName("response");
            entity.Property(e => e.SubProductionStepId).HasColumnName("subProductionStep_id");
            entity.Property(e => e.UpdateDateTime)
                .HasMaxLength(6)
                .HasColumnName("updateDateTime");
            entity.Property(e => e.UserId).HasColumnName("user_id");
            entity.Property(e => e.VersionNr)
                .HasMaxLength(255)
                .HasColumnName("versionNr");

            entity.HasOne(d => d.MachineOccupation).WithMany(p => p.Bookingentries)
                .HasForeignKey(d => d.MachineOccupationId)
                .HasConstraintName("FKe57614byrdted49wqd2p7dji6");

            entity.HasOne(d => d.OverheadCost).WithMany(p => p.Bookingentries)
                .HasForeignKey(d => d.OverheadCostId)
                .HasConstraintName("FK2su0kingqav2ycdmq5nofvv9g");

            entity.HasOne(d => d.SubProductionStep).WithMany(p => p.Bookingentries)
                .HasForeignKey(d => d.SubProductionStepId)
                .HasConstraintName("FKqltonrbscwefmvvccv11pq8ij");

            entity.HasOne(d => d.User).WithMany(p => p.Bookingentries)
                .HasForeignKey(d => d.UserId)
                .HasConstraintName("FKrk8p1g8i0wjcu45vhvbhdx8t6");
        });

        modelBuilder.Entity<Camundamachineoccupation>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("PRIMARY");

            entity.ToTable("CamundaMachineOccupation");

            entity.HasIndex(e => e.MachineOccupationId, "FKc63xbi76wyvghct4ydefrommi");

            entity.HasIndex(e => e.ActiveProductionStepId, "FKq8s4j4t7w2q3k9agxjj74rry");

            entity.HasIndex(e => e.ExternalId, "UK_jdfx8unh43wgtlrp3k4v4nsvv").IsUnique();

            entity.Property(e => e.Id)
                .ValueGeneratedOnAdd()
                .HasColumnName("id");
            entity.Property(e => e.ActiveProductionStepId).HasColumnName("activeProductionStep_id");
            entity.Property(e => e.BusinessKey)
                .HasMaxLength(255)
                .HasColumnName("businessKey");
            entity.Property(e => e.CamundaProcessInstanceId)
                .HasMaxLength(255)
                .HasColumnName("camundaProcessInstanceId");
            entity.Property(e => e.CreateDateTime)
                .HasMaxLength(6)
                .HasColumnName("createDateTime");
            entity.Property(e => e.ExternalId).HasColumnName("externalId");
            entity.Property(e => e.IsSubMachineOccupation)
                .HasColumnType("bit(1)")
                .HasColumnName("isSubMachineOccupation");
            entity.Property(e => e.MachineOccupationId).HasColumnName("machineOccupation_id");
            entity.Property(e => e.UpdateDateTime)
                .HasMaxLength(6)
                .HasColumnName("updateDateTime");
            entity.Property(e => e.VersionNr)
                .HasMaxLength(255)
                .HasColumnName("versionNr");

            entity.HasOne(d => d.ActiveProductionStep).WithMany(p => p.Camundamachineoccupations)
                .HasForeignKey(d => d.ActiveProductionStepId)
                .HasConstraintName("FKq8s4j4t7w2q3k9agxjj74rry");

            entity.HasOne(d => d.MachineOccupation).WithMany(p => p.Camundamachineoccupations)
                .HasForeignKey(d => d.MachineOccupationId)
                .HasConstraintName("FKc63xbi76wyvghct4ydefrommi");
        });

        modelBuilder.Entity<Camundasubproductionstep>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("PRIMARY");

            entity.ToTable("CamundaSubProductionStep");

            entity.HasIndex(e => e.SubProductionStepId, "FKi2cd4bpxbv5kbvsywhwf42os7");

            entity.HasIndex(e => e.CamundaMachineOccupationId, "FKnjhhoiiqv41p8s56gevyywwan");

            entity.HasIndex(e => e.ExternalId, "UK_b2rm7d6x2w8ecim33cpa8bhev").IsUnique();

            entity.Property(e => e.Id)
                .ValueGeneratedOnAdd()
                .HasColumnName("id");
            entity.Property(e => e.CamundaMachineOccupationId).HasColumnName("camundaMachineOccupation_id");
            entity.Property(e => e.CamundaProcessVariables)
                .HasColumnType("text")
                .HasColumnName("camundaProcessVariables");
            entity.Property(e => e.CreateDateTime)
                .HasMaxLength(6)
                .HasColumnName("createDateTime");
            entity.Property(e => e.ExternalId).HasColumnName("externalId");
            entity.Property(e => e.FilledFormField)
                .HasColumnType("text")
                .HasColumnName("filledFormField");
            entity.Property(e => e.FormField)
                .HasColumnType("text")
                .HasColumnName("formField");
            entity.Property(e => e.FormKey)
                .HasMaxLength(255)
                .HasColumnName("formKey");
            entity.Property(e => e.Name)
                .HasMaxLength(255)
                .HasColumnName("name");
            entity.Property(e => e.SubProductionStepId).HasColumnName("subProductionStep_id");
            entity.Property(e => e.TaskDefinitionKey)
                .HasMaxLength(255)
                .HasColumnName("taskDefinitionKey");
            entity.Property(e => e.TaskId)
                .HasMaxLength(255)
                .HasColumnName("taskId");
            entity.Property(e => e.UpdateDateTime)
                .HasMaxLength(6)
                .HasColumnName("updateDateTime");
            entity.Property(e => e.VersionNr)
                .HasMaxLength(255)
                .HasColumnName("versionNr");

            entity.HasOne(d => d.CamundaMachineOccupation).WithMany(p => p.Camundasubproductionsteps)
                .HasForeignKey(d => d.CamundaMachineOccupationId)
                .HasConstraintName("FKnjhhoiiqv41p8s56gevyywwan");

            entity.HasOne(d => d.SubProductionStep).WithMany(p => p.Camundasubproductionsteps)
                .HasForeignKey(d => d.SubProductionStepId)
                .HasConstraintName("FKi2cd4bpxbv5kbvsywhwf42os7");
        });

        modelBuilder.Entity<CdDepartment>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("PRIMARY");

            entity.ToTable("CD_Department");

            entity.HasIndex(e => e.ExternalId, "IDX1r6kwjcm82mcqmyvhuffdvekw").IsUnique();

            entity.Property(e => e.Id)
                .ValueGeneratedOnAdd()
                .HasColumnName("id");
            entity.Property(e => e.CreateDateTime)
                .HasMaxLength(6)
                .HasColumnName("createDateTime");
            entity.Property(e => e.ExternalId).HasColumnName("externalId");
            entity.Property(e => e.Name)
                .HasMaxLength(255)
                .HasColumnName("name");
            entity.Property(e => e.UpdateDateTime)
                .HasMaxLength(6)
                .HasColumnName("updateDateTime");
            entity.Property(e => e.VersionNr)
                .HasMaxLength(255)
                .HasColumnName("versionNr");
        });

        modelBuilder.Entity<CdMachine>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("PRIMARY");

            entity.ToTable("CD_Machine");

            entity.HasIndex(e => e.MachineTypeId, "FK8lw9mactxdm38bbpevbsad5lm");

            entity.HasIndex(e => e.ExternalId, "IDXhr3iqab554n74p064cdgt4ohc").IsUnique();

            entity.Property(e => e.Id)
                .ValueGeneratedOnAdd()
                .HasColumnName("id");
            entity.Property(e => e.CreateDateTime)
                .HasMaxLength(6)
                .HasColumnName("createDateTime");
            entity.Property(e => e.ExternalId).HasColumnName("externalId");
            entity.Property(e => e.MachineTypeId).HasColumnName("machineType_id");
            entity.Property(e => e.Name)
                .HasMaxLength(255)
                .HasColumnName("name");
            entity.Property(e => e.UpdateDateTime)
                .HasMaxLength(6)
                .HasColumnName("updateDateTime");
            entity.Property(e => e.VersionNr)
                .HasMaxLength(255)
                .HasColumnName("versionNr");

            entity.HasOne(d => d.MachineType).WithMany(p => p.CdMachines)
                .HasForeignKey(d => d.MachineTypeId)
                .HasConstraintName("FK8lw9mactxdm38bbpevbsad5lm");
        });

        modelBuilder.Entity<CdMachinetype>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("PRIMARY");

            entity.ToTable("CD_MachineType");

            entity.HasIndex(e => e.ExternalId, "IDXkl2ctuplwq4ac7rarsvlv55oq").IsUnique();

            entity.Property(e => e.Id)
                .ValueGeneratedOnAdd()
                .HasColumnName("id");
            entity.Property(e => e.CreateDateTime)
                .HasMaxLength(6)
                .HasColumnName("createDateTime");
            entity.Property(e => e.ExternalId).HasColumnName("externalId");
            entity.Property(e => e.Name)
                .HasMaxLength(255)
                .HasColumnName("name");
            entity.Property(e => e.UpdateDateTime)
                .HasMaxLength(6)
                .HasColumnName("updateDateTime");
            entity.Property(e => e.VersionNr)
                .HasMaxLength(255)
                .HasColumnName("versionNr");
        });

        modelBuilder.Entity<CdMachinetypeCdDepartment>(entity =>
        {
            entity
                .HasNoKey()
                .ToTable("CD_MachineType_CD_Department");

            entity.HasIndex(e => e.Department, "FK199tkfduon9efd11fx49ljsxy");

            entity.HasIndex(e => e.MachineType, "FK6bekpt5aptm36gbevhfl6lgwy");

            entity.Property(e => e.Department).HasColumnName("department");
            entity.Property(e => e.MachineType).HasColumnName("machineType");

            entity.HasOne(d => d.DepartmentNavigation).WithMany()
                .HasForeignKey(d => d.Department)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("FK199tkfduon9efd11fx49ljsxy");

            entity.HasOne(d => d.MachineTypeNavigation).WithMany()
                .HasForeignKey(d => d.MachineType)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("FK6bekpt5aptm36gbevhfl6lgwy");
        });

        modelBuilder.Entity<CdOverheadcostcenter>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("PRIMARY");

            entity.ToTable("CD_OverheadCostCenter");

            entity.HasIndex(e => e.ExternalId, "IDXfb6deh7co5w5tck3ps4tn7yde").IsUnique();

            entity.Property(e => e.Id)
                .ValueGeneratedOnAdd()
                .HasColumnName("id");
            entity.Property(e => e.CreateDateTime)
                .HasMaxLength(6)
                .HasColumnName("createDateTime");
            entity.Property(e => e.ExternalId).HasColumnName("externalId");
            entity.Property(e => e.Name)
                .HasMaxLength(255)
                .HasColumnName("name");
            entity.Property(e => e.UpdateDateTime)
                .HasMaxLength(6)
                .HasColumnName("updateDateTime");
            entity.Property(e => e.VersionNr)
                .HasMaxLength(255)
                .HasColumnName("versionNr");
        });

        modelBuilder.Entity<CdProduct>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("PRIMARY");

            entity.ToTable("CD_Product");

            entity.HasIndex(e => e.ExternalId, "IDX6iwyl105cpk2ygqg2ylbs5v6t").IsUnique();

            entity.Property(e => e.Id)
                .ValueGeneratedOnAdd()
                .HasColumnName("id");
            entity.Property(e => e.CreateDateTime)
                .HasMaxLength(6)
                .HasColumnName("createDateTime");
            entity.Property(e => e.ExternalId).HasColumnName("externalId");
            entity.Property(e => e.Name)
                .HasMaxLength(255)
                .HasColumnName("name");
            entity.Property(e => e.UnitType)
                .HasMaxLength(255)
                .HasColumnName("unitType");
            entity.Property(e => e.UpdateDateTime)
                .HasMaxLength(6)
                .HasColumnName("updateDateTime");
            entity.Property(e => e.VersionNr)
                .HasMaxLength(255)
                .HasColumnName("versionNr");
        });

        modelBuilder.Entity<CdProductionStep>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("PRIMARY");

            entity.ToTable("CD_ProductionStep");

            entity.HasIndex(e => e.MachineTypeId, "FKbjk4kl61mdfkrgcspu5e2b78v");

            entity.HasIndex(e => e.ToolTypeId, "FKjumxna5ixaku6nx666nfsemtu");

            entity.HasIndex(e => e.ProductId, "FKmr55o5mi3p20tlv64594hlqvi");

            entity.HasIndex(e => e.ProductionStepInfoId, "FKrvi0x20nmjhjrns7mk684bnlr");

            entity.HasIndex(e => e.ExternalId, "IDXp8gw6l96nu65li6p642951scs").IsUnique();

            entity.Property(e => e.Id)
                .ValueGeneratedOnAdd()
                .HasColumnName("id");
            entity.Property(e => e.CamundaProcessName)
                .HasMaxLength(255)
                .HasColumnName("camundaProcessName");
            entity.Property(e => e.CreateDateTime)
                .HasMaxLength(6)
                .HasColumnName("createDateTime");
            entity.Property(e => e.ExternalId).HasColumnName("externalId");
            entity.Property(e => e.MachineTypeId).HasColumnName("machineType_id");
            entity.Property(e => e.Name)
                .HasMaxLength(255)
                .HasColumnName("name");
            entity.Property(e => e.ProductId).HasColumnName("product_id");
            entity.Property(e => e.ProductionDuration).HasColumnName("productionDuration");
            entity.Property(e => e.ProductionStepInfoId).HasColumnName("productionStepInfo_id");
            entity.Property(e => e.Sequence).HasColumnName("sequence");
            entity.Property(e => e.SetupTime).HasColumnName("setupTime");
            entity.Property(e => e.ToolTypeId).HasColumnName("toolType_id");
            entity.Property(e => e.UpdateDateTime)
                .HasMaxLength(6)
                .HasColumnName("updateDateTime");
            entity.Property(e => e.VersionNr)
                .HasMaxLength(255)
                .HasColumnName("versionNr");

            entity.HasOne(d => d.MachineType).WithMany(p => p.CdProductionsteps)
                .HasForeignKey(d => d.MachineTypeId)
                .HasConstraintName("FKbjk4kl61mdfkrgcspu5e2b78v");

            entity.HasOne(d => d.Product).WithMany(p => p.CdProductionsteps)
                .HasForeignKey(d => d.ProductId)
                .HasConstraintName("FKmr55o5mi3p20tlv64594hlqvi");

            entity.HasOne(d => d.ProductionStepInfo).WithMany(p => p.CdProductionsteps)
                .HasForeignKey(d => d.ProductionStepInfoId)
                .HasConstraintName("FKrvi0x20nmjhjrns7mk684bnlr");

            entity.HasOne(d => d.ToolType).WithMany(p => p.CdProductionsteps)
                .HasForeignKey(d => d.ToolTypeId)
                .HasConstraintName("FKjumxna5ixaku6nx666nfsemtu");
        });

        modelBuilder.Entity<CdProductionstepCdToolsettingparameter>(entity =>
        {
            entity
                .HasNoKey()
                .ToTable("CD_ProductionStep_CD_ToolSettingParameter");

            entity.HasIndex(e => e.ToolSettingParameter, "FKacp0g1is6sywjh764l7ux9nso");

            entity.HasIndex(e => e.ProductionStep, "FKe581p7l5x6ncl6qeqx6p97hu2");

            entity.Property(e => e.ProductionStep).HasColumnName("productionStep");
            entity.Property(e => e.ToolSettingParameter).HasColumnName("toolSettingParameter");

            entity.HasOne(d => d.ProductionStepNavigation).WithMany()
                .HasForeignKey(d => d.ProductionStep)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("FKe581p7l5x6ncl6qeqx6p97hu2");

            entity.HasOne(d => d.ToolSettingParameterNavigation).WithMany()
                .HasForeignKey(d => d.ToolSettingParameter)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("FKacp0g1is6sywjh764l7ux9nso");
        });

        modelBuilder.Entity<CdProductrelationship>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("PRIMARY");

            entity.ToTable("CD_ProductRelationship");

            entity.HasIndex(e => e.PartId, "FKk9bn38khwphcd8i8nwjnlu1aq");

            entity.HasIndex(e => e.ProductId, "FKr3ja421hlnm417gvujip09mjd");

            entity.HasIndex(e => e.ExternalId, "IDXntvu9568cpp9f8anheia8mnyt").IsUnique();

            entity.Property(e => e.Id)
                .ValueGeneratedOnAdd()
                .HasColumnName("id");
            entity.Property(e => e.Amount).HasColumnName("amount");
            entity.Property(e => e.CreateDateTime)
                .HasMaxLength(6)
                .HasColumnName("createDateTime");
            entity.Property(e => e.ExtIdProductionOrder)
                .HasMaxLength(255)
                .HasColumnName("extIdProductionOrder");
            entity.Property(e => e.ExternalId).HasColumnName("externalId");
            entity.Property(e => e.PartId).HasColumnName("part_id");
            entity.Property(e => e.ProductId).HasColumnName("product_id");
            entity.Property(e => e.UnitString)
                .HasMaxLength(255)
                .HasColumnName("unitString");
            entity.Property(e => e.UpdateDateTime)
                .HasMaxLength(6)
                .HasColumnName("updateDateTime");
            entity.Property(e => e.VersionNr)
                .HasMaxLength(255)
                .HasColumnName("versionNr");

            entity.HasOne(d => d.Part).WithMany(p => p.CdProductrelationshipParts)
                .HasForeignKey(d => d.PartId)
                .HasConstraintName("FKk9bn38khwphcd8i8nwjnlu1aq");

            entity.HasOne(d => d.Product).WithMany(p => p.CdProductrelationshipProducts)
                .HasForeignKey(d => d.ProductId)
                .HasConstraintName("FKr3ja421hlnm417gvujip09mjd");
        });

        modelBuilder.Entity<CdQualitycontrolfeature>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("PRIMARY");

            entity.ToTable("CD_QualityControlFeature");

            entity.HasIndex(e => e.ProductId, "FKnadd80qngiko3cnbn3d2tp14c");

            entity.HasIndex(e => e.ExternalId, "IDXgmh0tejmfqtyujfl3yfojko31").IsUnique();

            entity.Property(e => e.Id)
                .ValueGeneratedOnAdd()
                .HasColumnName("id");
            entity.Property(e => e.CreateDateTime)
                .HasMaxLength(6)
                .HasColumnName("createDateTime");
            entity.Property(e => e.ExternalId).HasColumnName("externalId");
            entity.Property(e => e.Name)
                .HasMaxLength(255)
                .HasColumnName("name");
            entity.Property(e => e.ProductId).HasColumnName("product_id");
            entity.Property(e => e.Tolerance)
                .HasMaxLength(255)
                .HasColumnName("tolerance");
            entity.Property(e => e.UpdateDateTime)
                .HasMaxLength(6)
                .HasColumnName("updateDateTime");
            entity.Property(e => e.Value)
                .HasMaxLength(255)
                .HasColumnName("value");
            entity.Property(e => e.VersionNr)
                .HasMaxLength(255)
                .HasColumnName("versionNr");

            entity.HasOne(d => d.Product).WithMany(p => p.CdQualitycontrolfeatures)
                .HasForeignKey(d => d.ProductId)
                .HasConstraintName("FKnadd80qngiko3cnbn3d2tp14c");
        });

        modelBuilder.Entity<CdTool>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("PRIMARY");

            entity.ToTable("CD_Tool");

            entity.HasIndex(e => e.ToolTypeId, "FK752h21a2xeipy5pik45fxtux5");

            entity.HasIndex(e => e.ExternalId, "IDXkyrv9gs2duv7em4j7qung98v9").IsUnique();

            entity.Property(e => e.Id)
                .ValueGeneratedOnAdd()
                .HasColumnName("id");
            entity.Property(e => e.CreateDateTime)
                .HasMaxLength(6)
                .HasColumnName("createDateTime");
            entity.Property(e => e.ExternalId).HasColumnName("externalId");
            entity.Property(e => e.Name)
                .HasMaxLength(255)
                .HasColumnName("name");
            entity.Property(e => e.ToolTypeId).HasColumnName("toolType_id");
            entity.Property(e => e.UpdateDateTime)
                .HasMaxLength(6)
                .HasColumnName("updateDateTime");
            entity.Property(e => e.VersionNr)
                .HasMaxLength(255)
                .HasColumnName("versionNr");

            entity.HasOne(d => d.ToolType).WithMany(p => p.CdTools)
                .HasForeignKey(d => d.ToolTypeId)
                .HasConstraintName("FK752h21a2xeipy5pik45fxtux5");
        });

        modelBuilder.Entity<CdToolsettingparameter>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("PRIMARY");

            entity.ToTable("CD_ToolSettingParameter");

            entity.HasIndex(e => e.ToolTypeId, "FKeftav1vkqik90p7w15yh10998");

            entity.HasIndex(e => e.MachineTypeId, "FKjr5o9ihndlx7wrabkd4wkl3fp");

            entity.HasIndex(e => e.ExternalId, "IDXpdyffwa6hsyswaxot0str1m4n").IsUnique();

            entity.Property(e => e.Id)
                .ValueGeneratedOnAdd()
                .HasColumnName("id");
            entity.Property(e => e.CreateDateTime)
                .HasMaxLength(6)
                .HasColumnName("createDateTime");
            entity.Property(e => e.ExternalId).HasColumnName("externalId");
            entity.Property(e => e.MachineTypeId).HasColumnName("machineType_id");
            entity.Property(e => e.Name)
                .HasMaxLength(255)
                .HasColumnName("name");
            entity.Property(e => e.ToolTypeId).HasColumnName("toolType_id");
            entity.Property(e => e.UnitType)
                .HasMaxLength(255)
                .HasColumnName("unitType");
            entity.Property(e => e.UpdateDateTime)
                .HasMaxLength(6)
                .HasColumnName("updateDateTime");
            entity.Property(e => e.VersionNr)
                .HasMaxLength(255)
                .HasColumnName("versionNr");

            entity.HasOne(d => d.MachineType).WithMany(p => p.CdToolsettingparameters)
                .HasForeignKey(d => d.MachineTypeId)
                .HasConstraintName("FKjr5o9ihndlx7wrabkd4wkl3fp");

            entity.HasOne(d => d.ToolType).WithMany(p => p.CdToolsettingparameters)
                .HasForeignKey(d => d.ToolTypeId)
                .HasConstraintName("FKeftav1vkqik90p7w15yh10998");
        });

        modelBuilder.Entity<CdTooltype>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("PRIMARY");

            entity.ToTable("CD_ToolType");

            entity.HasIndex(e => e.ExternalId, "IDXeo5ad83wtyp3uj1ym0dc5ty2n").IsUnique();

            entity.HasIndex(e => e.Number, "UK_o1hdgibtmjcsl270ta0s4tee8").IsUnique();

            entity.Property(e => e.Id)
                .ValueGeneratedOnAdd()
                .HasColumnName("id");
            entity.Property(e => e.CreateDateTime)
                .HasMaxLength(6)
                .HasColumnName("createDateTime");
            entity.Property(e => e.ExternalId).HasColumnName("externalId");
            entity.Property(e => e.Name)
                .HasMaxLength(255)
                .HasColumnName("name");
            entity.Property(e => e.Number).HasColumnName("number");
            entity.Property(e => e.UpdateDateTime)
                .HasMaxLength(6)
                .HasColumnName("updateDateTime");
            entity.Property(e => e.VersionNr)
                .HasMaxLength(255)
                .HasColumnName("versionNr");
        });

        modelBuilder.Entity<Classextension>(entity =>
        {
            entity.HasKey(e => e.FieldId).HasName("PRIMARY");

            entity.ToTable("ClassExtension");

            entity.Property(e => e.FieldId)
                .ValueGeneratedOnAdd()
                .HasColumnName("fieldId");
            entity.Property(e => e.Id)
                .HasMaxLength(255)
                .HasColumnName("id");
            entity.Property(e => e.Name)
                .HasMaxLength(255)
                .HasColumnName("name");
        });

        modelBuilder.Entity<ClassextensionMemberextension>(entity =>
        {
            entity
                .HasNoKey()
                .ToTable("ClassExtension_MemberExtension");

            entity.HasIndex(e => e.ClassExtensionFieldId, "FKoar6p2ya1ktyqmno9pmydwtyo");

            entity.HasIndex(e => e.MembersId, "UK_sobie9ohpokq1sxu834elb3tq").IsUnique();

            entity.Property(e => e.ClassExtensionFieldId).HasColumnName("ClassExtension_fieldId");
            entity.Property(e => e.MembersId).HasColumnName("members_id");

            entity.HasOne(d => d.ClassExtensionField).WithMany()
                .HasForeignKey(d => d.ClassExtensionFieldId)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("FKoar6p2ya1ktyqmno9pmydwtyo");
        });

        modelBuilder.Entity<Csvreaderparserconfig>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("PRIMARY");

            entity.ToTable("CsvReaderParserConfig");

            entity.Property(e => e.Id)
                .ValueGeneratedOnAdd()
                .HasColumnName("id");
            entity.Property(e => e.SeparatorChar)
                .HasMaxLength(1)
                .IsFixedLength()
                .HasColumnName("separatorChar");
        });

        modelBuilder.Entity<Customerorder>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("PRIMARY");

            entity.ToTable("CustomerOrder");

            entity.HasIndex(e => e.ExternalId, "IDX65ufwkhi2uw8m1c4yhgaj1p7d").IsUnique();

            entity.Property(e => e.Id)
                .ValueGeneratedOnAdd()
                .HasColumnName("id");
            entity.Property(e => e.CreateDateTime)
                .HasMaxLength(6)
                .HasColumnName("createDateTime");
            entity.Property(e => e.CustomerName)
                .HasMaxLength(255)
                .HasColumnName("customerName");
            entity.Property(e => e.Deadline)
                .HasMaxLength(6)
                .HasColumnName("deadline");
            entity.Property(e => e.ExternalId).HasColumnName("externalId");
            entity.Property(e => e.Name)
                .HasMaxLength(255)
                .HasColumnName("name");
            entity.Property(e => e.Priority).HasDefaultValue(0).HasColumnName("priority");
            entity.Property(e => e.UpdateDateTime)
                .HasMaxLength(6)
                .HasColumnName("updateDateTime");
            entity.Property(e => e.VersionNr)
                .HasMaxLength(255)
                .HasColumnName("versionNr");
        });

        modelBuilder.Entity<Datareader>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("PRIMARY");

            entity.ToTable("DataReader");

            entity.Property(e => e.Id)
                .ValueGeneratedOnAdd()
                .HasColumnName("id");
            entity.Property(e => e.ParserConfigId).HasColumnName("ParserConfigID");
            entity.Property(e => e.ParserId)
                .HasMaxLength(255)
                .HasColumnName("parserID");
            entity.Property(e => e.ParserKeyValueConfigs).HasColumnName("parserKeyValueConfigs");
            entity.Property(e => e.ReaderConfigId).HasColumnName("ReaderConfigID");
            entity.Property(e => e.ReaderId)
                .HasMaxLength(255)
                .HasColumnName("readerID");
            entity.Property(e => e.ReaderKeyValueConfigs).HasColumnName("readerKeyValueConfigs");
        });

        modelBuilder.Entity<Datawriter>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("PRIMARY");

            entity.ToTable("DataWriter");

            entity.Property(e => e.Id)
                .ValueGeneratedOnAdd()
                .HasColumnName("id");
            entity.Property(e => e.ParserConfigId).HasColumnName("ParserConfigID");
            entity.Property(e => e.ParserId)
                .HasMaxLength(255)
                .HasColumnName("parserID");
            entity.Property(e => e.ParserKeyValueConfigs).HasColumnName("parserKeyValueConfigs");
            entity.Property(e => e.WriterConfigId).HasColumnName("WriterConfigID");
            entity.Property(e => e.WriterId)
                .HasMaxLength(255)
                .HasColumnName("writerID");
            entity.Property(e => e.WriterKeyValueConfigs).HasColumnName("writerKeyValueConfigs");
        });

        modelBuilder.Entity<HibernateSequence>(entity =>
        {
            entity
                .HasNoKey()
                .ToTable("hibernate_sequence");

            entity.Property(e => e.NextVal).HasColumnName("next_val");
        });

        modelBuilder.Entity<Hicumessetting>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("PRIMARY");

            entity.ToTable("HicumesSettings");

            entity.HasIndex(e => e.ExternalId, "UK_gik9mabqtm3yrq1rdgp3upef7").IsUnique();

            entity.Property(e => e.Id)
                .ValueGeneratedOnAdd()
                .HasColumnName("id");
            entity.Property(e => e.CreateDateTime)
                .HasMaxLength(6)
                .HasColumnName("createDateTime");
            entity.Property(e => e.DisableBooking)
                .HasColumnType("bit(1)")
                .HasColumnName("disableBooking");
            entity.Property(e => e.ExternalId).HasColumnName("externalId");
            entity.Property(e => e.UpdateDateTime)
                .HasMaxLength(6)
                .HasColumnName("updateDateTime");
            entity.Property(e => e.VersionNr)
                .HasMaxLength(255)
                .HasColumnName("versionNr");
        });

        modelBuilder.Entity<Keyvaluemapproductionorder>(entity =>
        {
            entity.HasKey(e => new { e.ProductionOrder, e.KeyString })
                .HasName("PRIMARY")
                .HasAnnotation("MySql:IndexPrefixLength", new[] { 0, 0 });

            entity.ToTable("KeyValueMapProductionOrder");

            entity.Property(e => e.ProductionOrder).HasColumnName("productionOrder");
            entity.Property(e => e.KeyString).HasColumnName("keyString");
            entity.Property(e => e.ValueString)
                .HasMaxLength(255)
                .HasColumnName("valueString");

            entity.HasOne(d => d.ProductionOrderNavigation).WithMany(p => p.Keyvaluemapproductionorders)
                .HasForeignKey(d => d.ProductionOrder)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("FKrklijthe294bnc8o9qrba7axj");
        });

        modelBuilder.Entity<Keyvaluemapsubproductionstep>(entity =>
        {
            entity.HasKey(e => new { e.SubProductionStep, e.KeyString })
                .HasName("PRIMARY")
                .HasAnnotation("MySql:IndexPrefixLength", new[] { 0, 0 });

            entity.ToTable("KeyValueMapSubProductionStep");

            entity.Property(e => e.SubProductionStep).HasColumnName("subProductionStep");
            entity.Property(e => e.KeyString).HasColumnName("keyString");
            entity.Property(e => e.ValueString)
                .HasMaxLength(255)
                .HasColumnName("valueString");

            entity.HasOne(d => d.SubProductionStepNavigation).WithMany(p => p.Keyvaluemapsubproductionsteps)
                .HasForeignKey(d => d.SubProductionStep)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("FKiomamnm5mgfhgpl62cbgyntg7");
        });

        modelBuilder.Entity<MachineOccupation>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("PRIMARY");

            entity.ToTable("MachineOccupation");

            entity.HasIndex(e => e.TotalProductionNumbersId, "FK3yji2srysnd57nnk562oeubd0");

            entity.HasIndex(e => e.ProductionOrderId, "FK71cuuk7wt8so2o6wds6wl2o9a");

            entity.HasIndex(e => e.MachineId, "FK8rafq5vg60f11lbjnjbr8112u");

            entity.HasIndex(e => e.ToolId, "FK9ra0cssg869tj1gfb83nanctb");

            entity.HasIndex(e => e.ParentMachineOccupationId, "FKbcknes1rbi1eldfa1b6eg6pa0");

            entity.HasIndex(e => e.DepartmentId, "FKl0eis6d23p4ufhooiqbbaoc6t");

            entity.HasIndex(e => e.ExternalId, "IDX3xcyi8n2dljdcecsewtgle3u8").IsUnique();

            entity.Property(e => e.Id)
                .ValueGeneratedOnAdd()
                .HasColumnName("id");
            entity.Property(e => e.ActualEndDateTime)
                .HasMaxLength(6)
                .HasColumnName("actualEndDateTime");
            entity.Property(e => e.ActualStartDateTime)
                .HasMaxLength(6)
                .HasColumnName("actualStartDateTime");
            entity.Property(e => e.CamundaProcessName)
                .HasMaxLength(255)
                .HasColumnName("camundaProcessName");
            entity.Property(e => e.CreateDateTime)
                .HasMaxLength(6)
                .HasColumnName("createDateTime");
            entity.Property(e => e.DepartmentId).HasColumnName("department_id");
            entity.Property(e => e.ExternalId).HasColumnName("externalId");
            entity.Property(e => e.MachineId).HasColumnName("machine_id");
            entity.Property(e => e.Name)
                .HasMaxLength(255)
                .HasColumnName("name");
            entity.Property(e => e.ParentMachineOccupationId).HasColumnName("parentMachineOccupation_id");
            entity.Property(e => e.PlannedEndDateTime)
                .HasMaxLength(6)
                .HasColumnName("plannedEndDateTime");
            entity.Property(e => e.PlannedStartDateTime)
                .HasMaxLength(6)
                .HasColumnName("plannedStartDateTime");
            entity.Property(e => e.ProductionOrderId).HasColumnName("productionOrder_id");
            entity.Property(e => e.Status)
                .HasMaxLength(255)
                .HasColumnName("status");
            entity.Property(e => e.ToolId).HasColumnName("tool_id");
            entity.Property(e => e.TotalProductionNumbersId).HasColumnName("totalProductionNumbers_id");
            entity.Property(e => e.UpdateDateTime)
                .HasMaxLength(6)
                .HasColumnName("updateDateTime");
            entity.Property(e => e.VersionNr)
                .HasMaxLength(255)
                .HasColumnName("versionNr");

            entity.HasOne(d => d.Department).WithMany(p => p.Machineoccupations)
                .HasForeignKey(d => d.DepartmentId)
                .HasConstraintName("FKl0eis6d23p4ufhooiqbbaoc6t");

            entity.HasOne(d => d.Machine).WithMany(p => p.Machineoccupations)
                .HasForeignKey(d => d.MachineId)
                .HasConstraintName("FK8rafq5vg60f11lbjnjbr8112u");

            entity.HasOne(d => d.ParentMachineOccupation).WithMany(p => p.SubMachineOccupations)
                .HasForeignKey(d => d.ParentMachineOccupationId)
                .HasConstraintName("FKbcknes1rbi1eldfa1b6eg6pa0");

            entity.HasOne(d => d.ProductionOrder).WithMany(p => p.MachineOccupations)
                .HasForeignKey(d => d.ProductionOrderId)
                .HasConstraintName("FK71cuuk7wt8so2o6wds6wl2o9a");

            entity.HasOne(d => d.Tool).WithMany(p => p.Machineoccupations)
                .HasForeignKey(d => d.ToolId)
                .HasConstraintName("FK9ra0cssg869tj1gfb83nanctb");

            entity.HasOne(d => d.TotalProductionNumbers).WithMany(p => p.Machineoccupations)
                .HasForeignKey(d => d.TotalProductionNumbersId)
                .HasConstraintName("FK3yji2srysnd57nnk562oeubd0");
        });

        modelBuilder.Entity<MachineoccupationActivetoolsetting>(entity =>
        {
            entity
                .HasNoKey()
                .ToTable("MachineOccupation_ActiveToolSetting");

            entity.HasIndex(e => e.MachineOccupation, "FKg2oc9prf9tcav5rqvjx7u1m8k");

            entity.HasIndex(e => e.ToolSetting, "FKtqtu7tmbcfexydwyh43wilmex");

            entity.Property(e => e.MachineOccupation).HasColumnName("machineOccupation");
            entity.Property(e => e.ToolSetting).HasColumnName("toolSetting");

            entity.HasOne(d => d.MachineOccupationNavigation).WithMany()
                .HasForeignKey(d => d.MachineOccupation)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("FKg2oc9prf9tcav5rqvjx7u1m8k");

            entity.HasOne(d => d.ToolSettingNavigation).WithMany()
                .HasForeignKey(d => d.ToolSetting)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("FKtqtu7tmbcfexydwyh43wilmex");
        });

        modelBuilder.Entity<MachineoccupationCdProductionstep>(entity =>
        {
            entity
                // .HasNoKey()
                .ToTable("MachineOccupation_CD_ProductionStep");

            entity.HasKey(x => new { x.MachineOccupationId,x.ProductionStepId });

            entity.HasIndex(e => e.MachineOccupationId, "FKampj3ryea75d0rj4y7u8l5l7q");
            entity.HasIndex(e => e.ProductionStepId, "FKhaawoaio20mes04uc46rsb1j4");

            entity.Property(e => e.MachineOccupationId).HasColumnName("machineOccupation");
            entity.Property(e => e.ProductionStepId).HasColumnName("productionStep");

            entity.HasOne(d => d.MachineOccupationNavigation).WithMany()
                .HasForeignKey(d => d.MachineOccupationId)
                .OnDelete(DeleteBehavior.Cascade)
                .HasConstraintName("FKampj3ryea75d0rj4y7u8l5l7q");

            entity.HasOne(d => d.ProductionStepNavigation).WithMany()
                .HasForeignKey(d => d.ProductionStepId)
                .OnDelete(DeleteBehavior.Cascade)
                .HasConstraintName("FKhaawoaio20mes04uc46rsb1j4");
        });

        modelBuilder.Entity<MachineoccupationCdTool>(entity =>
        {
            entity
                .HasNoKey()
                .ToTable("MachineOccupation_CD_Tool");

            entity.HasIndex(e => e.Tool, "FKfbaawecmlecy8sc50sh4xv78o");

            entity.HasIndex(e => e.MachineOccupation, "FKn0fi8tjlw6q9cyh1xlie6wsti");

            entity.Property(e => e.MachineOccupation).HasColumnName("machineOccupation");
            entity.Property(e => e.Tool).HasColumnName("tool");

            entity.HasOne(d => d.MachineOccupationNavigation).WithMany()
                .HasForeignKey(d => d.MachineOccupation)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("FKn0fi8tjlw6q9cyh1xlie6wsti");

            entity.HasOne(d => d.ToolNavigation).WithMany()
                .HasForeignKey(d => d.Tool)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("FKfbaawecmlecy8sc50sh4xv78o");
        });

        modelBuilder.Entity<MachineoccupationMachineoccupation>(entity =>
        {
            entity
                .HasNoKey()
                .ToTable("MachineOccupation_MachineOccupation");

            entity.HasIndex(e => e.MachineOccupationId, "FKpooy52fnqrmlo9rjxt0qkkr5u");

            entity.HasIndex(e => e.SubMachineOccupationsId, "UK_ija76x9clcdjwhxioqp8e9id5").IsUnique();

            entity.Property(e => e.MachineOccupationId).HasColumnName("MachineOccupation_id");
            entity.Property(e => e.SubMachineOccupationsId).HasColumnName("subMachineOccupations_id");

            entity.HasOne(d => d.MachineOccupation).WithMany()
                .HasForeignKey(d => d.MachineOccupationId)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("FKpooy52fnqrmlo9rjxt0qkkr5u");

            entity.HasOne(d => d.SubMachineOccupations).WithOne()
                .HasForeignKey<MachineoccupationMachineoccupation>(d => d.SubMachineOccupationsId)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("FK3np88b9h7ynds5av3jb64pvlp");
        });

        modelBuilder.Entity<Machinestatus>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("PRIMARY");

            entity.ToTable("MachineStatus");

            entity.HasIndex(e => e.ExternalId, "IDXla7dijvden5bwt9i7anspo1oo").IsUnique();

            entity.Property(e => e.Id)
                .ValueGeneratedOnAdd()
                .HasColumnName("id");
            entity.Property(e => e.CreateDateTime)
                .HasMaxLength(6)
                .HasColumnName("createDateTime");
            entity.Property(e => e.ExternalId).HasColumnName("externalId");
            entity.Property(e => e.Name)
                .HasMaxLength(255)
                .HasColumnName("name");
            entity.Property(e => e.UpdateDateTime)
                .HasMaxLength(6)
                .HasColumnName("updateDateTime");
            entity.Property(e => e.VersionNr)
                .HasMaxLength(255)
                .HasColumnName("versionNr");
        });

        modelBuilder.Entity<Machinestatushistory>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("PRIMARY");

            entity.ToTable("MachineStatusHistory");

            entity.HasIndex(e => e.MachineId, "FK930w6pvtvjkgho0opl49p831e");

            entity.HasIndex(e => e.MachineStatusId, "FKcejxxsdtm136smxell90ho65m");

            entity.HasIndex(e => e.ExternalId, "IDX78fn6n46l8ua9kx1o6j6q99fa").IsUnique();

            entity.Property(e => e.Id)
                .ValueGeneratedOnAdd()
                .HasColumnName("id");
            entity.Property(e => e.CreateDateTime)
                .HasMaxLength(6)
                .HasColumnName("createDateTime");
            entity.Property(e => e.ExternalId).HasColumnName("externalId");
            entity.Property(e => e.MachineId).HasColumnName("machine_id");
            entity.Property(e => e.MachineStatusId).HasColumnName("machineStatus_id");
            entity.Property(e => e.StartDateTime)
                .HasMaxLength(6)
                .HasColumnName("startDateTime");
            entity.Property(e => e.UpdateDateTime)
                .HasMaxLength(6)
                .HasColumnName("updateDateTime");
            entity.Property(e => e.VersionNr)
                .HasMaxLength(255)
                .HasColumnName("versionNr");

            entity.HasOne(d => d.Machine).WithMany(p => p.Machinestatushistories)
                .HasForeignKey(d => d.MachineId)
                .HasConstraintName("FK930w6pvtvjkgho0opl49p831e");

            entity.HasOne(d => d.MachineStatus).WithMany(p => p.Machinestatushistories)
                .HasForeignKey(d => d.MachineStatusId)
                .HasConstraintName("FKcejxxsdtm136smxell90ho65m");
        });

        modelBuilder.Entity<Mappinganddatasource>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("PRIMARY");

            entity.ToTable("MappingAndDataSource");

            entity.HasIndex(e => e.DataReaderId, "FK3jvm5cwc8bjx01qj7425da0y6");

            entity.HasIndex(e => e.MappingConfigurationId, "FK7e3wx6ddpad5vb7menb6a54r3");

            entity.HasIndex(e => e.DataWriterId, "FK9q1h2asdtruwh0kgvm880ufse");

            entity.HasIndex(e => e.ReaderResultId, "FKoslde01e9uvfcsa6vcrged1sp");

            entity.Property(e => e.Id)
                .ValueGeneratedOnAdd()
                .HasColumnName("id");
            entity.Property(e => e.DataReaderId).HasColumnName("dataReader_id");
            entity.Property(e => e.DataWriterId).HasColumnName("dataWriter_id");
            entity.Property(e => e.ExternalId)
                .HasMaxLength(255)
                .HasColumnName("externalId");
            entity.Property(e => e.MappingConfigurationId).HasColumnName("mappingConfiguration_id");
            entity.Property(e => e.Name)
                .HasMaxLength(255)
                .HasColumnName("name");
            entity.Property(e => e.ReaderResultId).HasColumnName("readerResult_id");

            entity.HasOne(d => d.DataReader).WithMany(p => p.Mappinganddatasources)
                .HasForeignKey(d => d.DataReaderId)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("FK3jvm5cwc8bjx01qj7425da0y6");

            entity.HasOne(d => d.DataWriter).WithMany(p => p.Mappinganddatasources)
                .HasForeignKey(d => d.DataWriterId)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("FK9q1h2asdtruwh0kgvm880ufse");

            entity.HasOne(d => d.MappingConfiguration).WithMany(p => p.Mappinganddatasources)
                .HasForeignKey(d => d.MappingConfigurationId)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("FK7e3wx6ddpad5vb7menb6a54r3");

            entity.HasOne(d => d.ReaderResult).WithMany(p => p.Mappinganddatasources)
                .HasForeignKey(d => d.ReaderResultId)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("FKoslde01e9uvfcsa6vcrged1sp");
        });

        modelBuilder.Entity<Mappingconfiguration>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("PRIMARY");

            entity.ToTable("MappingConfiguration");

            entity.Property(e => e.Id)
                .ValueGeneratedOnAdd()
                .HasColumnName("id");
            entity.Property(e => e.InputSelector)
                .HasMaxLength(255)
                .HasColumnName("inputSelector");
            entity.Property(e => e.OutputSelector)
                .HasMaxLength(255)
                .HasColumnName("outputSelector");
            entity.Property(e => e.XsltRules).HasColumnName("xsltRules");
        });

        modelBuilder.Entity<MappingconfigurationMappingconfiguration>(entity =>
        {
            entity
                .HasNoKey()
                .ToTable("MappingConfiguration_MappingConfiguration");

            entity.HasIndex(e => e.MappingConfigurationId, "FK68pg626gb6i9exnt74a0t936g");

            entity.HasIndex(e => e.LoopsId, "UK_fofyof6ibiywpjvijmai6s15b").IsUnique();

            entity.Property(e => e.LoopsId).HasColumnName("loops_id");
            entity.Property(e => e.MappingConfigurationId).HasColumnName("MappingConfiguration_id");

            entity.HasOne(d => d.Loops).WithOne()
                .HasForeignKey<MappingconfigurationMappingconfiguration>(d => d.LoopsId)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("FKl269oqd7vl258tsq2d1j6pcap");

            entity.HasOne(d => d.MappingConfiguration).WithMany()
                .HasForeignKey(d => d.MappingConfigurationId)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("FK68pg626gb6i9exnt74a0t936g");
        });

        modelBuilder.Entity<MappingconfigurationMappingrule>(entity =>
        {
            entity
                .HasNoKey()
                .ToTable("MappingConfiguration_MappingRule");

            entity.HasIndex(e => e.MappingConfigurationId, "FK58hhu7m8euyh3yklyktirdrt4");

            entity.HasIndex(e => e.MappingsId, "UK_ef78odnj9l2qyrmvnar18yg00").IsUnique();

            entity.Property(e => e.MappingConfigurationId).HasColumnName("MappingConfiguration_id");
            entity.Property(e => e.MappingsId).HasColumnName("mappings_id");

            entity.HasOne(d => d.MappingConfiguration).WithMany()
                .HasForeignKey(d => d.MappingConfigurationId)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("FK58hhu7m8euyh3yklyktirdrt4");

            entity.HasOne(d => d.Mappings).WithOne()
                .HasForeignKey<MappingconfigurationMappingrule>(d => d.MappingsId)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("FKoexpbd19h7v8bco33iwwaor45");
        });

        modelBuilder.Entity<Mappingrule>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("PRIMARY");

            entity.ToTable("MappingRule");

            entity.Property(e => e.Id)
                .ValueGeneratedOnAdd()
                .HasColumnName("id");
            entity.Property(e => e.InputSelector)
                .HasMaxLength(255)
                .HasColumnName("inputSelector");
            entity.Property(e => e.OutputSelector)
                .HasMaxLength(255)
                .HasColumnName("outputSelector");
            entity.Property(e => e.Rule)
                .HasMaxLength(1020)
                .HasColumnName("rule");
            entity.Property(e => e.UiId).HasColumnName("uiId");
        });

        modelBuilder.Entity<Note>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("PRIMARY");

            entity.ToTable("Note");

            entity.HasIndex(e => e.ExternalId, "IDX9l2gnbkopst0al8q9wkfs69f1").IsUnique();

            entity.Property(e => e.Id)
                .ValueGeneratedOnAdd()
                .HasColumnName("id");
            entity.Property(e => e.CreateDateTime)
                .HasMaxLength(6)
                .HasColumnName("createDateTime");
            entity.Property(e => e.CreationDateTime)
                .HasMaxLength(6)
                .HasColumnName("creationDateTime");
            entity.Property(e => e.ExternalId).HasColumnName("externalId");
            entity.Property(e => e.NoteString)
                .HasMaxLength(255)
                .HasColumnName("noteString");
            entity.Property(e => e.UpdateDateTime)
                .HasMaxLength(6)
                .HasColumnName("updateDateTime");
            entity.Property(e => e.UserName)
                .HasMaxLength(255)
                .HasColumnName("userName");
            entity.Property(e => e.VersionNr)
                .HasMaxLength(255)
                .HasColumnName("versionNr");
        });

        modelBuilder.Entity<Overheadcost>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("PRIMARY");

            entity.ToTable("OverheadCost");

            entity.HasIndex(e => e.TimeRecordId, "FKjfwq0ofxb3sftuw60h0vbsx7u");

            entity.HasIndex(e => e.OverheadCostCenterId, "FKpqltdd4uddr6vl4lchp84s1iu");

            entity.HasIndex(e => e.UserId, "FKqb8mkux22v5i5qsvsl1yts4sm");

            entity.HasIndex(e => e.ExternalId, "IDXqsgpjhvs212mxbkdfm7hvsl8n").IsUnique();

            entity.Property(e => e.Id)
                .ValueGeneratedOnAdd()
                .HasColumnName("id");
            entity.Property(e => e.CreateDateTime)
                .HasMaxLength(6)
                .HasColumnName("createDateTime");
            entity.Property(e => e.ExternalId).HasColumnName("externalId");
            entity.Property(e => e.Note)
                .HasMaxLength(255)
                .HasColumnName("note");
            entity.Property(e => e.OverheadCostCenterId).HasColumnName("overheadCostCenter_id");
            entity.Property(e => e.TimeDuration).HasColumnName("timeDuration");
            entity.Property(e => e.TimeRecordId).HasColumnName("timeRecord_id");
            entity.Property(e => e.UpdateDateTime)
                .HasMaxLength(6)
                .HasColumnName("updateDateTime");
            entity.Property(e => e.UserId).HasColumnName("user_id");
            entity.Property(e => e.VersionNr)
                .HasMaxLength(255)
                .HasColumnName("versionNr");

            entity.HasOne(d => d.OverheadCostCenter).WithMany(p => p.Overheadcosts)
                .HasForeignKey(d => d.OverheadCostCenterId)
                .HasConstraintName("FKpqltdd4uddr6vl4lchp84s1iu");

            entity.HasOne(d => d.TimeRecord).WithMany(p => p.Overheadcosts)
                .HasForeignKey(d => d.TimeRecordId)
                .HasConstraintName("FKjfwq0ofxb3sftuw60h0vbsx7u");

            entity.HasOne(d => d.User).WithMany(p => p.Overheadcosts)
                .HasForeignKey(d => d.UserId)
                .HasConstraintName("FKqb8mkux22v5i5qsvsl1yts4sm");
        });

        modelBuilder.Entity<Productionnumber>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("PRIMARY");

            entity.ToTable("ProductionNumbers");

            entity.HasIndex(e => e.SubProductionStepId, "FK4nf7dlw8dgj6x5tr37qlitl13");

            entity.HasIndex(e => e.ExternalId, "IDX9n875m5j3nnnvj4sfk1j2090i").IsUnique();

            entity.Property(e => e.Id)
                .ValueGeneratedOnAdd()
                .HasColumnName("id");
            entity.Property(e => e.AcceptedAmount).HasColumnName("acceptedAmount");
            entity.Property(e => e.AcceptedUnitString)
                .HasMaxLength(255)
                .HasColumnName("acceptedUnitString");
            entity.Property(e => e.CreateDateTime)
                .HasMaxLength(6)
                .HasColumnName("createDateTime");
            entity.Property(e => e.ExternalId).HasColumnName("externalId");
            entity.Property(e => e.RejectedAmount).HasColumnName("rejectedAmount");
            entity.Property(e => e.RejectedUnitString)
                .HasMaxLength(255)
                .HasColumnName("rejectedUnitString");
            entity.Property(e => e.SubProductionStepId).HasColumnName("subProductionStep_id");
            entity.Property(e => e.UpdateDateTime)
                .HasMaxLength(6)
                .HasColumnName("updateDateTime");
            entity.Property(e => e.VersionNr)
                .HasMaxLength(255)
                .HasColumnName("versionNr");

            entity.HasOne(d => d.SubProductionStep).WithMany(p => p.Productionnumbers)
                .HasForeignKey(d => d.SubProductionStepId)
                .HasConstraintName("FK4nf7dlw8dgj6x5tr37qlitl13");
        });

        modelBuilder.Entity<Productionorder>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("PRIMARY");

            entity.ToTable("ProductionOrder");

            entity.HasIndex(e => e.CustomerOrderId, "FK552vq4xmu35hcqkafd73yc7gr");

            entity.HasIndex(e => e.ParentProductionOrderId, "FK75qygmafsjjk2su018wgntfbl");

            entity.HasIndex(e => e.ProductId, "FKq2refjqg82s38bj9yr8ktoqk4");

            entity.HasIndex(e => e.ExternalId, "IDX8e8qe7q92ugbkr5f8iyae90g9").IsUnique();

            entity.Property(e => e.Id)
                .ValueGeneratedOnAdd()
                .HasColumnName("id");
            entity.Property(e => e.Amount).HasColumnName("amount");
            entity.Property(e => e.CreateDateTime)
                .HasMaxLength(6)
                .HasColumnName("createDateTime");
            entity.Property(e => e.CustomerOrderId).HasColumnName("customerOrder_id");
            entity.Property(e => e.Deadline)
                .HasMaxLength(6)
                .HasColumnName("deadline");
            entity.Property(e => e.ExternalId).HasColumnName("externalId");
            entity.Property(e => e.Name)
                .HasMaxLength(255)
                .HasColumnName("name");
            entity.Property(e => e.ParentProductionOrderId).HasColumnName("parentProductionOrder_id");
            entity.Property(e => e.Priority).HasDefaultValue(0).HasColumnName("priority");
            entity.Property(e => e.ProductId).HasColumnName("product_id");
            entity.Property(e => e.Status)
                .HasMaxLength(255)
                .HasColumnName("status");
            entity.Property(e => e.UnitString)
                .HasMaxLength(255)
                .HasColumnName("unitString");
            entity.Property(e => e.UpdateDateTime)
                .HasMaxLength(6)
                .HasColumnName("updateDateTime");
            entity.Property(e => e.VersionNr)
                .HasMaxLength(255)
                .HasColumnName("versionNr");

            entity.HasOne(d => d.CustomerOrder).WithMany(p => p.Productionorders)
                .HasForeignKey(d => d.CustomerOrderId)
                .HasConstraintName("FK552vq4xmu35hcqkafd73yc7gr");

            entity.HasOne(d => d.ParentProductionOrder).WithMany(p => p.SubProductionOrders)
                .HasForeignKey(d => d.ParentProductionOrderId)
                .HasConstraintName("FK75qygmafsjjk2su018wgntfbl");

            entity.HasOne(d => d.Product).WithMany(p => p.Productionorders)
                .HasForeignKey(d => d.ProductId)
                .HasConstraintName("FKq2refjqg82s38bj9yr8ktoqk4");
        });

        modelBuilder.Entity<ProductionorderNote>(entity =>
        {
            entity
                .HasNoKey()
                .ToTable("ProductionOrder_Note");

            entity.HasIndex(e => e.ProductionOrderId, "FKtmo8e5b1n2y3ukfabyae66a4u");

            entity.HasIndex(e => e.NotesId, "UK_6ufet95f3av1oas3t5bydsj5y").IsUnique();

            entity.Property(e => e.NotesId).HasColumnName("notes_id");
            entity.Property(e => e.ProductionOrderId).HasColumnName("ProductionOrder_id");

            entity.HasOne(d => d.Notes).WithOne()
                .HasForeignKey<ProductionorderNote>(d => d.NotesId)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("FKgg5ilmt97dfbrf28f8971peo5");

            entity.HasOne(d => d.ProductionOrder).WithMany()
                .HasForeignKey(d => d.ProductionOrderId)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("FKtmo8e5b1n2y3ukfabyae66a4u");
        });

        modelBuilder.Entity<ProductionorderProductionorder>(entity =>
        {
            entity
                .HasNoKey()
                .ToTable("ProductionOrder_ProductionOrder");

            entity.HasIndex(e => e.ProductionOrderId, "FKphqkki5fl6oqv9btpc1rfhmtm");

            entity.HasIndex(e => e.SubProductionOrdersId, "UK_pmx4fs8e3qjqt6vnw899je6ee").IsUnique();

            entity.Property(e => e.ProductionOrderId).HasColumnName("ProductionOrder_id");
            entity.Property(e => e.SubProductionOrdersId).HasColumnName("subProductionOrders_id");

            entity.HasOne(d => d.ProductionOrder).WithMany()
                .HasForeignKey(d => d.ProductionOrderId)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("FKphqkki5fl6oqv9btpc1rfhmtm");

            entity.HasOne(d => d.SubProductionOrders).WithOne()
                .HasForeignKey<ProductionorderProductionorder>(d => d.SubProductionOrdersId)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("FK2acr870w3ppcvtppn1m1v36oj");
        });

        modelBuilder.Entity<Productionstepinfo>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("PRIMARY");

            entity.ToTable("ProductionStepInfo");

            entity.HasIndex(e => e.ExternalId, "IDXr229svqoyg312bo7pjyy804ah").IsUnique();

            entity.Property(e => e.Id)
                .ValueGeneratedOnAdd()
                .HasColumnName("id");
            entity.Property(e => e.CreateDateTime)
                .HasMaxLength(6)
                .HasColumnName("createDateTime");
            entity.Property(e => e.ExternalId).HasColumnName("externalId");
            entity.Property(e => e.StepGroup)
                .HasMaxLength(255)
                .HasColumnName("stepGroup");
            entity.Property(e => e.StepIdent)
                .HasMaxLength(255)
                .HasColumnName("stepIdent");
            entity.Property(e => e.StepNr).HasColumnName("stepNr");
            entity.Property(e => e.StepType).HasColumnName("stepType");
            entity.Property(e => e.UpdateDateTime)
                .HasMaxLength(6)
                .HasColumnName("updateDateTime");
            entity.Property(e => e.VersionNr)
                .HasMaxLength(255)
                .HasColumnName("versionNr");
        });

        modelBuilder.Entity<Qualitymanagement>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("PRIMARY");

            entity.ToTable("QualityManagement");

            entity.HasIndex(e => e.QualityControlFeatureId, "FKb6r6p170m75adxm7dbfq1bmme");

            entity.HasIndex(e => e.SubProductionStepId, "FKkvqh03inro2bwr922unurnixa");

            entity.HasIndex(e => e.ExternalId, "IDXr7ajptobxu1y3yc3rayit29t7").IsUnique();

            entity.Property(e => e.Id)
                .ValueGeneratedOnAdd()
                .HasColumnName("id");
            entity.Property(e => e.CreateDateTime)
                .HasMaxLength(6)
                .HasColumnName("createDateTime");
            entity.Property(e => e.ExternalId).HasColumnName("externalId");
            entity.Property(e => e.QualityControlFeatureId).HasColumnName("qualityControlFeature_id");
            entity.Property(e => e.QualityOk)
                .HasColumnType("bit(1)")
                .HasColumnName("qualityOk");
            entity.Property(e => e.SubProductionStepId).HasColumnName("subProductionStep_id");
            entity.Property(e => e.UpdateDateTime)
                .HasMaxLength(6)
                .HasColumnName("updateDateTime");
            entity.Property(e => e.VersionNr)
                .HasMaxLength(255)
                .HasColumnName("versionNr");

            entity.HasOne(d => d.QualityControlFeature).WithMany(p => p.Qualitymanagements)
                .HasForeignKey(d => d.QualityControlFeatureId)
                .HasConstraintName("FKb6r6p170m75adxm7dbfq1bmme");

            entity.HasOne(d => d.SubProductionStep).WithMany(p => p.Qualitymanagements)
                .HasForeignKey(d => d.SubProductionStepId)
                .HasConstraintName("FKkvqh03inro2bwr922unurnixa");
        });

        modelBuilder.Entity<Readerresult>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("PRIMARY");

            entity.ToTable("ReaderResult");

            entity.Property(e => e.Id)
                .ValueGeneratedOnAdd()
                .HasColumnName("id");
            entity.Property(e => e.AdditionalData).HasColumnName("additionalData");
            entity.Property(e => e.Result).HasColumnName("result");
        });

        modelBuilder.Entity<Setup>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("PRIMARY");

            entity.ToTable("SetUp");

            entity.HasIndex(e => e.SubProductionStepId, "FK5qp0v4rdx3yc3m3dvqx2wsy0b");

            entity.HasIndex(e => e.ExternalId, "IDXevanv4l23n7bw1f4t3l6xb3ok").IsUnique();

            entity.Property(e => e.Id)
                .ValueGeneratedOnAdd()
                .HasColumnName("id");
            entity.Property(e => e.CreateDateTime)
                .HasMaxLength(6)
                .HasColumnName("createDateTime");
            entity.Property(e => e.ExternalId).HasColumnName("externalId");
            entity.Property(e => e.SubProductionStepId).HasColumnName("subProductionStep_id");
            entity.Property(e => e.UpdateDateTime)
                .HasMaxLength(6)
                .HasColumnName("updateDateTime");
            entity.Property(e => e.VersionNr)
                .HasMaxLength(255)
                .HasColumnName("versionNr");

            entity.HasOne(d => d.SubProductionStep).WithMany(p => p.Setups)
                .HasForeignKey(d => d.SubProductionStepId)
                .HasConstraintName("FK5qp0v4rdx3yc3m3dvqx2wsy0b");
        });

        modelBuilder.Entity<Singlefilereaderconfig>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("PRIMARY");

            entity.ToTable("SingleFileReaderConfig");

            entity.Property(e => e.Id)
                .ValueGeneratedOnAdd()
                .HasColumnName("id");
            entity.Property(e => e.Path)
                .HasMaxLength(255)
                .HasColumnName("path");
        });

        modelBuilder.Entity<Subproductionstep>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("PRIMARY");

            entity.ToTable("SubProductionStep");

            entity.HasIndex(e => e.MachineOccupationId, "FKi4mr81mnur112pq3bwvrt2uuy");

            entity.HasIndex(e => e.ExternalId, "IDX5y3d6s5rjk1a4h7mq5snlyiso").IsUnique();

            entity.Property(e => e.Id)
                .ValueGeneratedOnAdd()
                .HasColumnName("id");
            entity.Property(e => e.CreateDateTime)
                .HasMaxLength(6)
                .HasColumnName("createDateTime");
            entity.Property(e => e.ExternalId).HasColumnName("externalId");
            entity.Property(e => e.MachineOccupationId).HasColumnName("machineOccupation_id");
            entity.Property(e => e.Note)
                .HasMaxLength(255)
                .HasColumnName("note");
            entity.Property(e => e.SubmitType).HasColumnName("submitType");
            entity.Property(e => e.Type).HasColumnName("type");
            entity.Property(e => e.UpdateDateTime)
                .HasMaxLength(6)
                .HasColumnName("updateDateTime");
            entity.Property(e => e.VersionNr)
                .HasMaxLength(255)
                .HasColumnName("versionNr");

            entity.HasOne(d => d.MachineOccupation).WithMany(p => p.Subproductionsteps)
                .HasForeignKey(d => d.MachineOccupationId)
                .HasConstraintName("FKi4mr81mnur112pq3bwvrt2uuy");
        });

        modelBuilder.Entity<Suspensiontype>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("PRIMARY");

            entity.ToTable("SuspensionType");

            entity.HasIndex(e => e.ExternalId, "IDXa11bbwceychq04k0jhayfnkv0").IsUnique();

            entity.HasIndex(e => e.Name, "UK_mq1yap4v1dlxlx11sjmmh8uss").IsUnique();

            entity.Property(e => e.Id)
                .ValueGeneratedOnAdd()
                .HasColumnName("id");
            entity.Property(e => e.CreateDateTime)
                .HasMaxLength(6)
                .HasColumnName("createDateTime");
            entity.Property(e => e.Description)
                .HasMaxLength(255)
                .HasColumnName("description");
            entity.Property(e => e.ExternalId).HasColumnName("externalId");
            entity.Property(e => e.Name).HasColumnName("name");
            entity.Property(e => e.UpdateDateTime)
                .HasMaxLength(6)
                .HasColumnName("updateDateTime");
            entity.Property(e => e.VersionNr)
                .HasMaxLength(255)
                .HasColumnName("versionNr");
        });

        modelBuilder.Entity<TimeDuration>(entity =>
        {
            entity.HasKey(e => new { e.MachineOccupation, e.TimeType })
                .HasName("PRIMARY")
                .HasAnnotation("MySql:IndexPrefixLength", new[] { 0, 0 });

            entity.ToTable("Time_Durations");

            entity.Property(e => e.MachineOccupation).HasColumnName("machineOccupation");
            entity.Property(e => e.TimeType).HasColumnName("timeType");
            entity.Property(e => e.Duration).HasColumnName("duration");

            entity.HasOne(d => d.MachineOccupationNavigation).WithMany(p => p.TimeDurations)
                .HasForeignKey(d => d.MachineOccupation)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("FK3nh7qlesuy1bdar5gtfqi4hke");
        });

        modelBuilder.Entity<TimeDurationsstep>(entity =>
        {
            entity.HasKey(e => new { e.SubProductionStep, e.TimeType })
                .HasName("PRIMARY")
                .HasAnnotation("MySql:IndexPrefixLength", new[] { 0, 0 });

            entity.ToTable("Time_DurationsStep");

            entity.Property(e => e.SubProductionStep).HasColumnName("subProductionStep");
            entity.Property(e => e.TimeType).HasColumnName("timeType");
            entity.Property(e => e.Duration).HasColumnName("duration");

            entity.HasOne(d => d.SubProductionStepNavigation).WithMany(p => p.TimeDurationssteps)
                .HasForeignKey(d => d.SubProductionStep)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("FKbw711s57116je969wf5c6esuc");
        });

        modelBuilder.Entity<Timerecord>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("PRIMARY");

            entity.ToTable("TimeRecord");

            entity.HasIndex(e => e.TimeRecordTypeId, "FKc4r6bc780sga59tv3256wnxog");

            entity.HasIndex(e => e.SubProductionStepId, "FKkht0wcxmqrry62qpkdfxcoeuv");

            entity.HasIndex(e => e.SuspensionTypeId, "FKspdh3ry0153ot0dy0sdkfhs3g");

            entity.HasIndex(e => e.ResponseUserId, "FKtjoc22405hi1xd3x6hp9l0cna");

            entity.HasIndex(e => e.ExternalId, "IDXochw1gewvtbeyixwlh7ygidn4").IsUnique();

            entity.Property(e => e.Id)
                .ValueGeneratedOnAdd()
                .HasColumnName("id");
            entity.Property(e => e.CreateDateTime)
                .HasMaxLength(6)
                .HasColumnName("createDateTime");
            entity.Property(e => e.EndDateTime)
                .HasMaxLength(6)
                .HasColumnName("endDateTime");
            entity.Property(e => e.ExternalId).HasColumnName("externalId");
            entity.Property(e => e.ResponseUserId).HasColumnName("responseUser_id");
            entity.Property(e => e.StartDateTime)
                .HasMaxLength(6)
                .HasColumnName("startDateTime");
            entity.Property(e => e.SubProductionStepId).HasColumnName("subProductionStep_id");
            entity.Property(e => e.SuspensionTypeId).HasColumnName("suspensionType_id");
            entity.Property(e => e.TimeRecordTypeId).HasColumnName("timeRecordType_id");
            entity.Property(e => e.UpdateDateTime)
                .HasMaxLength(6)
                .HasColumnName("updateDateTime");
            entity.Property(e => e.VersionNr)
                .HasMaxLength(255)
                .HasColumnName("versionNr");

            entity.HasOne(d => d.ResponseUser).WithMany(p => p.Timerecords)
                .HasForeignKey(d => d.ResponseUserId)
                .HasConstraintName("FKtjoc22405hi1xd3x6hp9l0cna");

            entity.HasOne(d => d.SubProductionStep).WithMany(p => p.Timerecords)
                .HasForeignKey(d => d.SubProductionStepId)
                .HasConstraintName("FKkht0wcxmqrry62qpkdfxcoeuv");

            entity.HasOne(d => d.SuspensionType).WithMany(p => p.Timerecords)
                .HasForeignKey(d => d.SuspensionTypeId)
                .HasConstraintName("FKspdh3ry0153ot0dy0sdkfhs3g");

            entity.HasOne(d => d.TimeRecordType).WithMany(p => p.Timerecords)
                .HasForeignKey(d => d.TimeRecordTypeId)
                .HasConstraintName("FKc4r6bc780sga59tv3256wnxog");
        });

        modelBuilder.Entity<Timerecordtype>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("PRIMARY");

            entity.ToTable("TimeRecordType");

            entity.HasIndex(e => e.ExternalId, "IDX6fl0d99lpw1omdcw20qitso0o").IsUnique();

            entity.HasIndex(e => e.Name, "UK_c72bw80yt7gbgwuro9atysavy").IsUnique();

            entity.Property(e => e.Id)
                .ValueGeneratedOnAdd()
                .HasColumnName("id");
            entity.Property(e => e.CreateDateTime)
                .HasMaxLength(6)
                .HasColumnName("createDateTime");
            entity.Property(e => e.Description)
                .HasMaxLength(255)
                .HasColumnName("description");
            entity.Property(e => e.ExternalId).HasColumnName("externalId");
            entity.Property(e => e.Name).HasColumnName("name");
            entity.Property(e => e.UpdateDateTime)
                .HasMaxLength(6)
                .HasColumnName("updateDateTime");
            entity.Property(e => e.VersionNr)
                .HasMaxLength(255)
                .HasColumnName("versionNr");
        });

        modelBuilder.Entity<Toolsetting>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("PRIMARY");

            entity.ToTable("ToolSetting");

            entity.HasIndex(e => e.ToolId, "FKbprwp6w2b72drca4khe2gxuri");

            entity.HasIndex(e => e.MachineId, "FKda7u57okrn7bcd4s8t8o3oili");

            entity.HasIndex(e => e.ToolSettingParameterId, "FKjpykeo7mcb57vdi216kxj5217");

            entity.HasIndex(e => e.ExternalId, "IDX2oxkkvhqjt9m086nmxupp0gah").IsUnique();

            entity.Property(e => e.Id)
                .ValueGeneratedOnAdd()
                .HasColumnName("id");
            entity.Property(e => e.Amount).HasColumnName("amount");
            entity.Property(e => e.CreateDateTime)
                .HasMaxLength(6)
                .HasColumnName("createDateTime");
            entity.Property(e => e.ExternalId).HasColumnName("externalId");
            entity.Property(e => e.MachineId).HasColumnName("machine_id");
            entity.Property(e => e.ToolId).HasColumnName("tool_id");
            entity.Property(e => e.ToolSettingParameterId).HasColumnName("toolSettingParameter_id");
            entity.Property(e => e.UnitString)
                .HasMaxLength(255)
                .HasColumnName("unitString");
            entity.Property(e => e.UpdateDateTime)
                .HasMaxLength(6)
                .HasColumnName("updateDateTime");
            entity.Property(e => e.VersionNr)
                .HasMaxLength(255)
                .HasColumnName("versionNr");

            entity.HasOne(d => d.Machine).WithMany(p => p.Toolsettings)
                .HasForeignKey(d => d.MachineId)
                .HasConstraintName("FKda7u57okrn7bcd4s8t8o3oili");

            entity.HasOne(d => d.Tool).WithMany(p => p.Toolsettings)
                .HasForeignKey(d => d.ToolId)
                .HasConstraintName("FKbprwp6w2b72drca4khe2gxuri");

            entity.HasOne(d => d.ToolSettingParameter).WithMany(p => p.Toolsettings)
                .HasForeignKey(d => d.ToolSettingParameterId)
                .HasConstraintName("FKjpykeo7mcb57vdi216kxj5217");
        });

        modelBuilder.Entity<User>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("PRIMARY");

            entity.ToTable("User");

            entity.HasIndex(e => e.ExternalId, "IDX3p8a52yq3023p357hb6b4jalv").IsUnique();

            entity.HasIndex(e => e.UserName, "UK_hl8fftx66p59oqgkkcfit3eay").IsUnique();

            entity.Property(e => e.Id)
                .ValueGeneratedOnAdd()
                .HasColumnName("id");
            entity.Property(e => e.CreateDateTime)
                .HasMaxLength(6)
                .HasColumnName("createDateTime");
            entity.Property(e => e.ExternalId).HasColumnName("externalId");
            entity.Property(e => e.FullUserName)
                .HasMaxLength(255)
                .HasColumnName("fullUserName");
            entity.Property(e => e.UpdateDateTime)
                .HasMaxLength(6)
                .HasColumnName("updateDateTime");
            entity.Property(e => e.UserName).HasColumnName("userName");
            entity.Property(e => e.VersionNr)
                .HasMaxLength(255)
                .HasColumnName("versionNr");
        });

        OnModelCreatingPartial(modelBuilder);
    }

    partial void OnModelCreatingPartial(ModelBuilder modelBuilder);
}
