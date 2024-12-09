package de.iisys.sysint.hicumes.core.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import de.iisys.sysint.hicumes.core.entities.annotations.MapperIgnore;
import de.iisys.sysint.hicumes.core.entities.jsonViews.JsonViews;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@XmlRootElement(name = "cD_Tool")
@XmlAccessorType(XmlAccessType.FIELD)
@Cacheable
@Table(indexes = @Index(columnList = "externalId"))
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CD_Tool extends EntitySuperClass {

    private String name;

    @ManyToOne
    @JsonView({JsonViews.ViewMachineOccupation.class, JsonViews.SchemaMapperDefault.class})
    @ToString.Exclude
    private CD_ToolType toolType;

    @OneToMany(mappedBy = "tool", cascade = CascadeType.ALL)
    @ToString.Exclude
    @JsonIgnore
    @XmlTransient
    @MapperIgnore
    private List<MachineOccupation> machineOccupations = new ArrayList<>();

    @JsonView({JsonViews.ViewMachineOccupation.class, JsonViews.SchemaMapperDefault.class})
    @OneToMany(mappedBy = "tool", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<ToolSetting> toolSettings = new ArrayList<>();

    public CD_Tool(String name, CD_ToolType toolType) {
        this.name = name;
        toolType.toolsAdd(this);
        this.toolType = toolType;
    }

    @JsonIgnore
    public void machineOccupationsAdd(MachineOccupation input) {
        input.setTool(this);
        machineOccupations.add(input);
    }

    @JsonIgnore
    public void toolSettingsAdd(ToolSetting input) {
        input.setTool(this);
        toolSettings.add(input);
    }
}
