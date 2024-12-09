package de.iisys.sysint.hicumes.core.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import de.iisys.sysint.hicumes.core.entities.adapters.LocalDateTimeAdapter;
import de.iisys.sysint.hicumes.core.entities.jsonViews.JsonViews;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@XmlRootElement(name = "timeRecord")
@XmlAccessorType(XmlAccessType.FIELD)
@Table(indexes = @Index(columnList = "externalId"))
public class TimeRecord extends EntitySuperClass {

    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ToString.Exclude
    private SubProductionStep subProductionStep;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private SuspensionType suspensionType;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private TimeRecordType timeRecordType;

    @OneToOne
    private User responseUser;

    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime startDateTime;
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime endDateTime;

    public TimeRecord(SubProductionStep subProductionStep, SuspensionType suspensionType, TimeRecordType timeRecordType) {
        subProductionStep.timeRecordsAdd(this);
        this.subProductionStep = subProductionStep;
        this.suspensionType = suspensionType;
        this.timeRecordType = timeRecordType;
    }

    public TimeRecord(SuspensionType suspensionType, TimeRecordType timeRecordType) {
        this.suspensionType = suspensionType;
        this.timeRecordType = timeRecordType;
    }

    public Duration getDuration()
    {
        if(this.startDateTime != null && this.endDateTime != null)
        {
            return Duration.between(this.startDateTime, this.endDateTime);
        }
        else return null;
    }

}
