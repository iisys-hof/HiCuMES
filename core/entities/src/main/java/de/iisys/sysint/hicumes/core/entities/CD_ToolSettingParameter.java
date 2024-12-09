package de.iisys.sysint.hicumes.core.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import de.iisys.sysint.hicumes.core.entities.annotations.MapperIgnore;
import de.iisys.sysint.hicumes.core.entities.jsonViews.JsonViews;
import de.iisys.sysint.hicumes.core.entities.unit.EUnitType;
import lombok.Data;
import lombok.Getter;
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
@Getter
@ToString(callSuper = true)
@XmlRootElement(name = "cD_ToolSettingParameter")
@XmlAccessorType(XmlAccessType.FIELD)
@Cacheable
@Table(indexes = @Index(columnList = "externalId"))
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties({"id", "versionNr"})
public class CD_ToolSettingParameter extends EntitySuperClass {

    @ManyToOne
    @JsonView({JsonViews.ViewMachineOccupation.class, JsonViews.SchemaMapperDefault.class})
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ToString.Exclude
    private CD_ToolType toolType;

    @ManyToOne
    @JsonView({JsonViews.ViewMachineOccupation.class, JsonViews.SchemaMapperDefault.class})
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ToString.Exclude
    private CD_MachineType machineType;

    @Enumerated(EnumType.STRING)
    @JsonIgnore
    @ToString.Exclude
    private EUnitType unitType;
    @JsonView({JsonViews.ViewMachineOccupation.class, JsonViews.SchemaMapperDefault.class})
    private String name;

    /*@OneToMany(mappedBy = "toolSettingParameter", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonIgnore
    @ToString.Exclude
    private List<ToolSetting> toolSettings = new ArrayList<>();*/

    @ManyToMany(mappedBy = "toolSettingParameters")
    @MapperIgnore
    @ToString.Exclude
    @JsonIgnore
    private List<CD_ProductionStep> productionSteps = new ArrayList<>();

    public CD_ToolSettingParameter(CD_ToolType toolType, CD_MachineType machineType, EUnitType unitType, String name, List<CD_ProductionStep> productionSteps) {
        toolType.toolSettingParametersAdd(this);
        this.toolType = toolType;

        machineType.toolSettingParametersAdd(this);
        this.machineType = machineType;
        this.unitType = unitType;
        this.name = name;

        this.productionSteps = productionSteps;
    }

    /*@JsonIgnore
    public void toolSettingsAdd(ToolSetting input) {
        input.setToolSettingParameter(this);
        toolSettings.add(input);
    }*/

    @JsonIgnore
    public void productionStepsAdd(CD_ProductionStep input) {
        productionSteps.add(input);
    }

}
