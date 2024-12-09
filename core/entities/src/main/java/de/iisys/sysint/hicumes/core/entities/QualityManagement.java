package de.iisys.sysint.hicumes.core.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@XmlRootElement(name = "qualityManagement")
@XmlAccessorType(XmlAccessType.FIELD)
@Table(indexes = @Index(columnList = "externalId"))
public class QualityManagement extends EntitySuperClass {

    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ToString.Exclude
    private SubProductionStep subProductionStep;

    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ToString.Exclude
    private CD_QualityControlFeature qualityControlFeature;

    private boolean qualityOk;

    public QualityManagement(SubProductionStep subProductionStep, CD_QualityControlFeature qualityControlFeature, boolean qualityOk) {
        subProductionStep.qualityManagementsAdd(this);
        this.subProductionStep = subProductionStep;
        this.qualityControlFeature = qualityControlFeature;
        this.qualityOk = qualityOk;
    }


}
