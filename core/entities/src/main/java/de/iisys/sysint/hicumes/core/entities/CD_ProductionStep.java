package de.iisys.sysint.hicumes.core.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import de.iisys.sysint.hicumes.core.entities.adapters.DurationAdapter;
import de.iisys.sysint.hicumes.core.entities.adapters.JPADurationAdapter;
import de.iisys.sysint.hicumes.core.entities.annotations.MapperIgnore;
import de.iisys.sysint.hicumes.core.entities.jsonViews.JsonViews;
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
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@XmlRootElement(name = "cD_ProductionStep")
@XmlAccessorType(XmlAccessType.FIELD)
@Cacheable
@Table(indexes = @Index(columnList = "externalId"))
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CD_ProductionStep extends EntitySuperClass {


    private String camundaProcessName;

    @ManyToOne
    @JsonView({JsonViews.ViewMachineOccupation.class, JsonViews.SchemaMapperDefault.class})
    @ToString.Exclude
    private CD_MachineType machineType;

    @ManyToOne
    @JsonView({JsonViews.ViewMachineOccupation.class, JsonViews.SchemaMapperDefault.class})
    @ToString.Exclude
    private CD_ToolType toolType;

    @ManyToOne
    @JsonView({JsonViews.ViewMachineOccupation.class, JsonViews.SchemaMapperDefault.class})
    @ToString.Exclude
    private CD_Product product;

    private String name;
    @XmlJavaTypeAdapter(DurationAdapter.class)
    @Convert(converter = JPADurationAdapter.class)
    private Duration productionDuration;
    @XmlJavaTypeAdapter(DurationAdapter.class)
    @Convert(converter = JPADurationAdapter.class)
    private Duration setupTime;
    private int sequence;

    @ManyToOne
    @JsonView({JsonViews.ViewMachineOccupation.class, JsonViews.SchemaMapperDefault.class})
    @ToString.Exclude
    private ProductionStepInfo productionStepInfo;

    @ManyToMany(mappedBy = "productionSteps")
    @MapperIgnore
    @ToString.Exclude
    @JsonIgnore
    private List<MachineOccupation> machineOccupations = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(
            name = "CD_ProductionStep_CD_ToolSettingParameter",
            joinColumns = @JoinColumn(name = "productionStep"),
            inverseJoinColumns = @JoinColumn(name = "toolSettingParameter"))
    @LazyCollection(LazyCollectionOption.TRUE)
    @JsonView({JsonViews.SchemaMapperDefault.class})
    @ToString.Exclude
    private List<CD_ToolSettingParameter> toolSettingParameters = new ArrayList<>();

    public CD_ProductionStep(CD_MachineType machineType, CD_ToolType toolType, CD_Product product, CD_ToolSettingParameter toolSettingParameter, String name, Duration productionDuration, Duration setupTime, int sequence, String camundaProcessName) {
        machineType.productionStepsAdd(this);
        this.machineType = machineType;

        toolType.productionStepsAdd(this);
        this.toolType = toolType;

        product.productionStepsAdd(this);
        this.product = product;

        this.toolSettingParametersAdd(toolSettingParameter);

        this.name = name;
        this.productionDuration = productionDuration;
        this.setupTime = setupTime;
        this.sequence = sequence;
        this.camundaProcessName = camundaProcessName;
    }

    public CD_ProductionStep(CD_MachineType machineType, CD_ToolType toolType, CD_Product product, List<CD_ToolSettingParameter> toolSettingParameters, String name, Duration productionDuration, Duration setupTime, int sequence, String camundaProcessName) {
        machineType.productionStepsAdd(this);
        this.machineType = machineType;

        toolType.productionStepsAdd(this);
        this.toolType = toolType;

        product.productionStepsAdd(this);
        this.product = product;

        this.toolSettingParameters = toolSettingParameters;

        this.name = name;
        this.productionDuration = productionDuration;
        this.setupTime = setupTime;
        this.sequence = sequence;
        this.camundaProcessName = camundaProcessName;
    }

    /*
    @JsonIgnore
    public void machineOccupationsAdd(MachineOccupation input) {
        input.setCDProductionSteps(this);
        machineOccupations.add(input);
    }*/

    @JsonIgnore
    public void toolSettingParametersAdd(CD_ToolSettingParameter input) {
        input.productionStepsAdd(this);
        toolSettingParameters.add(input);
    }
}
