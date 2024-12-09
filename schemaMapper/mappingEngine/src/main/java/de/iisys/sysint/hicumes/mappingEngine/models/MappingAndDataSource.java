package de.iisys.sysint.hicumes.mappingEngine.models;

import de.iisys.sysint.hicumes.mappingEngine.models.dataSource.reader.DataReader;
import de.iisys.sysint.hicumes.mappingEngine.models.dataSource.reader.ReaderResult;
import de.iisys.sysint.hicumes.mappingEngine.models.dataSource.writer.DataWriter;
import de.iisys.sysint.hicumes.mappingEngine.models.mapping.MappingConfiguration;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NamedQueries({
        @NamedQuery(name = "MappingAndDataSource.findAll", query = "SELECT t FROM MappingAndDataSource t")
        , @NamedQuery(name = "MappingAndDataSource.findById", query = "SELECT t FROM MappingAndDataSource t WHERE t.id = :id")
})
public class MappingAndDataSource {
    public MappingAndDataSource(@NotNull String name, @NotNull DataReader dataReader, @NotNull DataWriter dataWriter, @NotNull MappingConfiguration mappingConfiguration, @NotNull ReaderResult readerResult) {
        this.name = name;
        this.dataReader = dataReader;
        this.dataWriter = dataWriter;
        this.mappingConfiguration = mappingConfiguration;
        this.readerResult = readerResult;
    }

    public MappingAndDataSource() {
    }

    @Id
    protected long id;

    protected String externalId;

    @NotNull
    private String name;

    @NotNull
    @OneToOne(orphanRemoval = true, cascade = {CascadeType.REMOVE, CascadeType.MERGE, CascadeType.PERSIST})
    private DataReader dataReader;

    @NotNull
    @OneToOne(orphanRemoval = true, cascade = {CascadeType.REMOVE, CascadeType.MERGE, CascadeType.PERSIST})
    private DataWriter dataWriter;

    @NotNull
    @OneToOne(orphanRemoval = true, cascade = {CascadeType.REMOVE, CascadeType.MERGE, CascadeType.PERSIST})
    private MappingConfiguration mappingConfiguration;

    @NotNull
    @OneToOne(orphanRemoval = true, cascade = {CascadeType.REMOVE, CascadeType.MERGE, CascadeType.PERSIST})
    private ReaderResult readerResult;
}
