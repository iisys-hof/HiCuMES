package de.iisys.sysint.hicumes.mappingEngine.models.mapping;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@Entity
public class MappingRule {
    @Id
    @GeneratedValue
    protected long id;

    private long uiId;
    @Column(length = 1020)
    private String rule;
    private String inputSelector;
    private String outputSelector;


    public MappingRule() {
    }
}
