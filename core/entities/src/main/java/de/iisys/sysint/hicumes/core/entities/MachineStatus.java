package de.iisys.sysint.hicumes.core.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import de.iisys.sysint.hicumes.core.entities.jsonViews.JsonViews;
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
@XmlRootElement(name = "machineStatus")
@XmlAccessorType(XmlAccessType.FIELD)
@Table(indexes = @Index(columnList = "externalId"))
public class MachineStatus extends EntitySuperClass {

    private String name;

    @JsonView(JsonViews.SchemaMapperDefault.class)
    @OneToMany(mappedBy = "machineStatus", cascade = CascadeType.ALL)
    private List<MachineStatusHistory> machineStatusHistories = new ArrayList<>();

    public MachineStatus(String name) {
        this.name = name;
    }

    @JsonIgnore
    public void machineStatusHistoriesAdd(MachineStatusHistory input) {
        input.setMachineStatus(this);
        machineStatusHistories.add(input);
    }

}
