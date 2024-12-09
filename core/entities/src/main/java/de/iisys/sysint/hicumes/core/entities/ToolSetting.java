package de.iisys.sysint.hicumes.core.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import de.iisys.sysint.hicumes.core.entities.annotations.MapperIgnore;
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
import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@XmlRootElement(name = "toolSetting")
@XmlAccessorType(XmlAccessType.FIELD)
@Cacheable
@Table(indexes = @Index(columnList = "externalId"))
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties({"id", "versionNr"})
public class ToolSetting extends EntitySuperClass {

    //TODO: Datum hinzufügen, um danach zu gruppieren

    @ManyToOne
    @JsonView({JsonViews.SchemaMapperDefault.class})
    @ToString.Exclude
    private CD_Tool tool;

    @ManyToOne
    @JsonView({JsonViews.SchemaMapperDefault.class})
    @ToString.Exclude
    private CD_Machine machine;

    @ManyToOne
    @JsonView({JsonViews.SchemaMapperDefault.class, JsonViews.ViewMachineOccupation.class})
    @ToString.Exclude
    private CD_ToolSettingParameter toolSettingParameter;


    @ManyToMany(mappedBy = "activeToolSettings")
    @MapperIgnore
    @ToString.Exclude
    @JsonIgnore
    private List<MachineOccupation> machineOccupations = new ArrayList<>();

    /*@ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ToString.Exclude
    private MachineOccupation machineOccupation;*/

    @Embedded
    @ToString.Exclude
    @JsonIgnore
    private Measurement measurement = new Measurement();

    public ToolSetting(CD_Tool tool, CD_Machine machine, CD_ToolSettingParameter toolSettingParameter, /*MachineOccupation machineOccupation,*/ Measurement measurement) {
        tool.toolSettingsAdd(this);
        this.tool = tool;

        machine.toolSettingsAdd(this);
        this.machine = machine;

        //toolSettingParameter.toolSettingsAdd(this);
        this.toolSettingParameter = toolSettingParameter;

       /* machineOccupation.toolSettingsAdd(this);
        this.machineOccupation = machineOccupation;*/
        this.measurement = measurement;
    }

    public void setMachineOccupation(MachineOccupation machineOccupation) {
        //this.machineOccupation = machineOccupation;
        machineOccupation.overwriteToolSetting(this);
    }


}
