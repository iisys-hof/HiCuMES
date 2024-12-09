package de.iisys.sysint.hicumes.core.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@XmlRootElement(name = "setUp")
@XmlAccessorType(XmlAccessType.FIELD)
@Table(indexes = @Index(columnList = "externalId"))
public class SetUp extends EntitySuperClass {

    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ToString.Exclude
    private SubProductionStep subProductionStep;

    public SetUp(SubProductionStep subProductionStep) {
        subProductionStep.setUpsAdd(this);
        this.subProductionStep = subProductionStep;
    }

}
