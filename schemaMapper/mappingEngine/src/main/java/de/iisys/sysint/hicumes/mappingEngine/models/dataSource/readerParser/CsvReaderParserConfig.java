package de.iisys.sysint.hicumes.mappingEngine.models.dataSource.readerParser;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class CsvReaderParserConfig {
    @Id
    @GeneratedValue
    protected long id;

    private char separatorChar = ',';

    public CsvReaderParserConfig() {
    }

    public CsvReaderParserConfig(char separatorChar) {
        this.separatorChar = separatorChar;
    }
}
