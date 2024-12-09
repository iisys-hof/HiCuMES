package de.iisys.sysint.hicumes.core.entities.unit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import de.iisys.sysint.hicumes.core.entities.adapters.MeasurementAdapter;
import de.iisys.sysint.hicumes.core.entities.jsonViews.JsonViews;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import tech.units.indriya.quantity.Quantities;

import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.quantity.Length;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import static tech.units.indriya.AbstractUnit.ONE;

@Embeddable
@Data
@NoArgsConstructor
@XmlRootElement(name = "measurement")
@XmlAccessorType(XmlAccessType.FIELD)
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@XmlJavaTypeAdapter(MeasurementAdapter.class)
public class Measurement {

    public double amount = 0;

    public String unitString = "";

    @Convert(converter = QuantityConverter.class)
    @JsonIgnore
    @Transient
    @XmlTransient
    private Quantity quantity;

    @Convert(converter = UnitConverter.class)
    @JsonIgnore
    @Transient
    @XmlTransient
    private Unit unit = ONE;

    public Measurement(Quantity quantity){
        this.quantity = quantity;
        this.amount = quantity.getValue().doubleValue();
        this.unit = quantity.getUnit();
    }

    public Measurement(double amount, String unitString){
        this.amount = amount;
        UnitConverter converter = new UnitConverter();
        var unit = converter.convertToEntityAttribute(unitString);
        this.unit = unit;
        this.generateQuantity();
    }

    @PostLoad
    @PrePersist
    public void generateQuantity(){
        try {
            UnitConverter converter = new UnitConverter();
            var unit = converter.convertToEntityAttribute(unitString);
            this.unit = unit;
            this.quantity = Quantities.getQuantity(this.amount, this.unit);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setQuantity(Quantity quantity)
    {
        this.quantity = quantity;
        this.amount = quantity.getValue().doubleValue();
        this.unit = quantity.getUnit();
    }

}
