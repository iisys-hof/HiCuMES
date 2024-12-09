package de.iisys.sysint.hicumes.mappingEngine.models.dataSource.reader;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Data
@AllArgsConstructor
@Entity
public class ReaderResult {
    @Id
    @GeneratedValue
    protected long id;

    public ReaderResult() {
    }

    @Lob
    @Column( length = 1000000 )
    private String result;

    @Lob
    @Column( length = 1000000 )
    private String additionalData;

    public ReaderResult(String result) {
        this.result = result;
    }

    public ReaderResult(String result, String additionalData) {
        this.result = result;
        this.additionalData = additionalData;
    }
}
