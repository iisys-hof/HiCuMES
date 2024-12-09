package de.iisys.sysint.hicumes.core.entities;


import com.fasterxml.jackson.annotation.JsonView;
import de.iisys.sysint.hicumes.core.entities.enums.EProductionStepType;
import de.iisys.sysint.hicumes.core.entities.jsonViews.JsonViews;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;

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
@XmlRootElement(name = "productionStepInfo")
@XmlAccessorType(XmlAccessType.FIELD)
@Cacheable
@Table(indexes = @Index(columnList = "externalId"))
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProductionStepInfo extends EntitySuperClass {

    private String stepGroup;
    private String stepIdent;
    private EProductionStepType stepType;
    private int stepNr;

    @JsonView(JsonViews.SchemaMapperDefault.class)
    @OneToMany(mappedBy = "productionStepInfo", cascade = CascadeType.ALL)
    private List<CD_ProductionStep> productionSteps = new ArrayList<>();
}

