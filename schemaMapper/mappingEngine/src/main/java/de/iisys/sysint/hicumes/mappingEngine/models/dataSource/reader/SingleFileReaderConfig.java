package de.iisys.sysint.hicumes.mappingEngine.models.dataSource.reader;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class SingleFileReaderConfig {
    @Id
    @GeneratedValue
    protected long id;
    private String path;

    public SingleFileReaderConfig() {
    }

    public SingleFileReaderConfig(String path) {
        this.path = path;
    }
}
