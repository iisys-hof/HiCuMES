package de.iisys.sysint.hicumes.core.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import de.iisys.sysint.hicumes.core.entities.jsonViews.JsonViews;
import de.iisys.sysint.hicumes.core.entities.unit.Measurement;
import de.iisys.sysint.hicumes.core.entities.unit.Unit;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@XmlRootElement(name = "cD_ProductRelationship")
@XmlAccessorType(XmlAccessType.FIELD)
@Cacheable
@Table(indexes = @Index(columnList = "externalId"))
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CD_ProductRelationship extends EntitySuperClass {

    @Embedded
    private Measurement measurement = new Measurement();

    @ManyToOne
    @JsonView({JsonViews.ViewCoreData.class, JsonViews.SchemaMapperDefault.class})
    @JsonIgnoreProperties({"productRelationships", "partRelationships", "productionSteps", "productionOrders"})
    @ToString.Exclude
    private CD_Product product;

    @ManyToOne
    @JsonView({JsonViews.ViewCoreData.class, JsonViews.SchemaMapperDefault.class})
    @JsonIgnoreProperties({"productRelationships", "partRelationships", "productionSteps", "productionOrders"})
    @ToString.Exclude
    private CD_Product part;

    //If productRelationship is dependent on a production order you can set the externalId of the production order here
    //The rest-call for the product relationships will then filter only those for the specific production order
    private String extIdProductionOrder;

    public CD_ProductRelationship(Measurement measurement, CD_Product product, CD_Product part) {
        this.measurement = measurement;
        product.productRelationshipsAdd(this);
        this.product = product;

        part.productRelationshipsAdd(this);
        this.part = part;
    }
}
