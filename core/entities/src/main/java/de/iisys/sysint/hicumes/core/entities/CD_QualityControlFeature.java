package de.iisys.sysint.hicumes.core.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import de.iisys.sysint.hicumes.core.entities.jsonViews.JsonViews;
import de.iisys.sysint.hicumes.core.entities.unit.EUnitType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
@XmlRootElement(name = "cD_QualityControlFeature")
@XmlAccessorType(XmlAccessType.FIELD)
@Table(indexes = @Index(columnList = "externalId"))
public class CD_QualityControlFeature extends EntitySuperClass {

    private String name;

    private String value;

    private String tolerance;

    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JsonView(JsonViews.SchemaMapperDefault.class)
    @ToString.Exclude
    private CD_Product product;

    public CD_QualityControlFeature(CD_Product product, String name) {
        product.qualityControlFeaturesAdd(this);
        this.product = product;
        this.name = name;
    }

    @JsonIgnore
    public void qualityManagementsAdd(QualityManagement input) {
        input.setQualityControlFeature(this);
    }
}
