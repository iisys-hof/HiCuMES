package de.iisys.sysint.hicumes.core.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import de.iisys.sysint.hicumes.core.entities.jsonViews.JsonViews;
import de.iisys.sysint.hicumes.core.entities.unit.EUnitType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@XmlRootElement(name = "cD_Product")
@XmlAccessorType(XmlAccessType.FIELD)
@Cacheable
@Table(indexes = @Index(columnList = "externalId"))
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CD_Product extends EntitySuperClass {

    private String name;
    @Enumerated(EnumType.STRING)
    private EUnitType unitType;
    @JsonView(JsonViews.SchemaMapperDefault.class)
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductionOrder> productionOrders = new ArrayList<>();
    @JsonView(JsonViews.SchemaMapperDefault.class)
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<CD_ProductionStep> productionSteps = new ArrayList<>();
    @JsonView(JsonViews.SchemaMapperDefault.class)
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.TRUE)
    private List<CD_ProductRelationship> productRelationships = new ArrayList<>();
    @JsonView(JsonViews.SchemaMapperDefault.class)
    @OneToMany(mappedBy = "part", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.TRUE)
    private List<CD_ProductRelationship> partRelationships = new ArrayList<>();
    @JsonView(JsonViews.ViewMachineOccupation.class)
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<CD_QualityControlFeature> qualityControlFeatures = new ArrayList<>();

    public CD_Product(String name, EUnitType unitType) {
        this.name = name;
        this.unitType = unitType;
    }

    @JsonIgnore
    public void productionOrdersAdd(ProductionOrder input) {
        input.setProduct(this);
        productionOrders.add(input);
    }

    @JsonIgnore
    public void productionStepsAdd(CD_ProductionStep input) {
        input.setProduct(this);
        productionSteps.add(input);
    }

    @JsonIgnore
    public void productRelationshipsAdd(CD_ProductRelationship input) {
        input.setProduct(this);
        productRelationships.add(input);
    }

    @JsonIgnore
    public void qualityControlFeaturesAdd(CD_QualityControlFeature input) {
        input.setProduct(this);
        qualityControlFeatures.add(input);
    }

    @JsonIgnore
    public void partRelationshipsAdd(CD_ProductRelationship input) {
        input.setPart(this);
        productRelationships.add(input);
    }


}
