package de.iisys.sysint.hicumes.core.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
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
@XmlRootElement(name = "cD_ToolType")
@XmlAccessorType(XmlAccessType.FIELD)
@Cacheable
@Table(indexes = @Index(columnList = "externalId"))
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CD_ToolType extends EntitySuperClass {

    @Column(unique = true)
    private int number;

    private String name;

    @JsonView(JsonViews.SchemaMapperDefault.class)
    @OneToMany(mappedBy = "toolType", cascade = CascadeType.ALL)
    private List<CD_ProductionStep> productionSteps = new ArrayList<>();
    @JsonView(JsonViews.SchemaMapperDefault.class)
    @OneToMany(mappedBy = "toolType", cascade = CascadeType.ALL)
    private List<CD_Tool> tools = new ArrayList<>();
    @JsonView(JsonViews.SchemaMapperDefault.class)
    @OneToMany(mappedBy = "toolType", cascade = CascadeType.ALL)
    private List<CD_ToolSettingParameter> toolSettingParameters = new ArrayList<>();


    public CD_ToolType(int number, String name) {
        this.number = number;
        this.name = name;
    }

    @JsonIgnore
    public void productionStepsAdd(CD_ProductionStep input) {
        input.setToolType(this);
        productionSteps.add(input);
    }

    @JsonIgnore
    public void toolsAdd(CD_Tool input) {
        input.setToolType(this);
        tools.add(input);
    }

    @JsonIgnore
    public void toolSettingParametersAdd(CD_ToolSettingParameter input) {
        input.setToolType(this);
        toolSettingParameters.add(input);
    }
}
