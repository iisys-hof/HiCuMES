package de.iisys.sysint.hicumes.core.entities;

import com.fasterxml.jackson.annotation.JsonView;
import de.iisys.sysint.hicumes.core.entities.enums.EBookingState;
import de.iisys.sysint.hicumes.core.entities.jsonViews.JsonViews;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Data
@ToString(callSuper = true)
@XmlRootElement(name = "bookingEntry")
@XmlAccessorType(XmlAccessType.FIELD)
@Table(indexes = @Index(columnList = "externalId"))
public class BookingEntry extends EntitySuperClass{

    @OneToOne(cascade = CascadeType.ALL)
    @JsonView({JsonViews.ViewMachineOccupation.class, JsonViews.SchemaMapperDefault.class})
    User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonView({JsonViews.ViewMachineOccupation.class, JsonViews.SchemaMapperDefault.class})
    MachineOccupation machineOccupation;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonView({JsonViews.ViewMachineOccupation.class, JsonViews.SchemaMapperDefault.class})
    SubProductionStep subProductionStep;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonView({JsonViews.ViewMachineOccupation.class, JsonViews.SchemaMapperDefault.class})
    OverheadCost overheadCost;

    EBookingState bookingState;

    @Lob
    @Column( length = 1000000 )
    String message;

    @Lob
    @Column( length = 1000000 )
    String response;

    private boolean isStepTime = false;

    public BookingEntry() {
    }
}
