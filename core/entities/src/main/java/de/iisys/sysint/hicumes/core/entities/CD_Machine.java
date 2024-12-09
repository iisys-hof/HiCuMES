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
@XmlRootElement(name = "cD_Machine")
@XmlAccessorType(XmlAccessType.FIELD)
@Cacheable
@Table(indexes = @Index(columnList = "externalId"))
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CD_Machine extends EntitySuperClass {

    @ManyToOne
    @JsonView({JsonViews.ViewMachineOccupation.class, JsonViews.SchemaMapperDefault.class})
    @ToString.Exclude
    private CD_MachineType machineType;

    private String name;

    @JsonView(JsonViews.SchemaMapperDefault.class)
    @OneToMany(mappedBy = "machine", cascade = CascadeType.ALL)
    private List<MachineOccupation> machineOccupations = new ArrayList<>();

    @JsonView(JsonViews.SchemaMapperDefault.class)
    @OneToMany(mappedBy = "machine", cascade = CascadeType.ALL)
    private List<MachineStatusHistory> machineStatusHistories = new ArrayList<>();

    @JsonView(JsonViews.SchemaMapperDefault.class)
    @OneToMany(mappedBy = "machine", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<ToolSetting> toolSettings = new ArrayList<>();


    public CD_Machine(String name, CD_MachineType machineType) {
        machineType.machinesAdd(this);
        this.name = name;
        this.machineType = machineType;
    }

    @JsonIgnore
    public void machineOccupationsAdd(MachineOccupation input) {
        input.setMachine(this);
        machineOccupations.add(input);
    }

    @JsonIgnore
    public void machineStatusHistoriesAdd(MachineStatusHistory input) {
        input.setMachine(this);
        machineStatusHistories.add(input);
    }

    @JsonIgnore
    public void toolSettingsAdd(ToolSetting input) {
        input.setMachine(this);
        toolSettings.add(input);
    }


}
