package de.iisys.sysint.hicumes.core.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.iisys.sysint.hicumes.core.entities.unit.Measurement;
import de.iisys.sysint.hicumes.core.entities.unit.Unit;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@XmlRootElement(name = "auxiliaryMaterials")
@XmlAccessorType(XmlAccessType.FIELD)
@Table(indexes = @Index(columnList = "externalId"))
public class AuxiliaryMaterials extends EntitySuperClass {

    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ToString.Exclude
    private SubProductionStep subProductionStep;

    @Embedded
    private Measurement measurement = new Measurement();

    public AuxiliaryMaterials(SubProductionStep subProductionStep, Measurement measurement) {
        subProductionStep.auxiliaryMaterialsAdd(this);
        this.subProductionStep = subProductionStep;
        this.measurement = measurement;
    }
}
