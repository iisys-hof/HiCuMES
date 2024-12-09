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

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@XmlRootElement(name = "productionNumbers")
@XmlAccessorType(XmlAccessType.FIELD)
@Table(indexes = @Index(columnList = "externalId"))
public class ProductionNumbers extends EntitySuperClass {

    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ToString.Exclude
    private SubProductionStep subProductionStep;

    @AttributeOverrides({
            @AttributeOverride(name = "unitString", column = @Column(name = "acceptedUnitString")),
            @AttributeOverride(name = "amount", column = @Column(name = "acceptedAmount")),
    })
    @Embedded
    private Measurement acceptedMeasurement = new Measurement();

    @AttributeOverrides({
            @AttributeOverride(name = "unitString", column = @Column(name = "rejectedUnitString")),
            @AttributeOverride(name = "amount", column = @Column(name = "rejectedAmount")),
    })
    @Embedded
    private Measurement rejectedMeasurement = new Measurement();

    public ProductionNumbers(SubProductionStep subProductionStep, Measurement acceptedMeasurement, Measurement rejectedMeasurement) {
        subProductionStep.productionNumbersAdd(this);
        this.subProductionStep = subProductionStep;
        this.acceptedMeasurement = acceptedMeasurement;
        this.rejectedMeasurement = rejectedMeasurement;
    }

    public void addProductionNumbers(ProductionNumbers addProductionNumbers)
    {
        addProductionNumbers.getAcceptedMeasurement().generateQuantity();
        addProductionNumbers.getRejectedMeasurement().generateQuantity();
        this.acceptedMeasurement.setQuantity(this.acceptedMeasurement.getQuantity().add(addProductionNumbers.acceptedMeasurement.getQuantity()));
        this.rejectedMeasurement.setQuantity(this.rejectedMeasurement.getQuantity().add(addProductionNumbers.rejectedMeasurement.getQuantity()));
    }
}
