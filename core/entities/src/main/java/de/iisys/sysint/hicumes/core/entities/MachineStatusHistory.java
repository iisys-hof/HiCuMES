package de.iisys.sysint.hicumes.core.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import de.iisys.sysint.hicumes.core.entities.adapters.LocalDateTimeAdapter;
import de.iisys.sysint.hicumes.core.entities.jsonViews.JsonViews;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@XmlRootElement(name = "machineStatusHistory")
@XmlAccessorType(XmlAccessType.FIELD)
@Table(indexes = @Index(columnList = "externalId"))
public class MachineStatusHistory extends EntitySuperClass {

    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JsonView(JsonViews.ViewMachineOccupation.class)
    @ToString.Exclude
    private CD_Machine machine;

    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JsonView({JsonViews.ViewMachineOccupation.class, JsonViews.SchemaMapperDefault.class})
    @ToString.Exclude
    private MachineStatus machineStatus;

    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime startDateTime;

    public MachineStatusHistory(CD_Machine machine, MachineStatus machineStatus, LocalDateTime startDateTime) {
        machine.machineStatusHistoriesAdd(this);
        this.machine = machine;
        this.machineStatus = machineStatus;
        this.startDateTime = startDateTime;
    }
}
