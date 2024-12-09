package de.iisys.sysint.hicumes.core.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import de.iisys.sysint.hicumes.core.entities.annotations.MapperIgnore;
import de.iisys.sysint.hicumes.core.entities.jsonViews.JsonViews;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Entity
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@XmlRootElement(name = "userOccupation")
@XmlAccessorType(XmlAccessType.FIELD)
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserOccupation extends EntitySuperClass {

    @JsonView(JsonViews.ViewMachineOccupation.class)
    @OneToOne(cascade = CascadeType.ALL)
    @MapperIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private MachineOccupation machineOccupation;

    @JsonView({JsonViews.ViewMachineOccupation.class, JsonViews.SchemaMapperDefault.class})
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @MapperIgnore
    @ToString.Exclude
    @JoinTable(
            name = "UserOccupation_ActiveUsers",
            joinColumns = @JoinColumn(name = "user_occupation_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @LazyCollection(LazyCollectionOption.TRUE)
    private List<User> activeUsers = new ArrayList<>();

    @JsonView({JsonViews.ViewMachineOccupation.class, JsonViews.SchemaMapperDefault.class})
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @MapperIgnore
    @ToString.Exclude
    @JoinTable(
            name = "UserOccupation_InactiveUsers",
            joinColumns = @JoinColumn(name = "user_occupation_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @LazyCollection(LazyCollectionOption.TRUE)
    private List<User> inactiveUsers = new ArrayList<>();

    @JsonView({JsonViews.ViewMachineOccupation.class, JsonViews.SchemaMapperDefault.class})
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @MapperIgnore
    @ToString.Exclude
    @JoinTable(
            name = "UserOccupation_TimeRecords",
            joinColumns = @JoinColumn(name = "user_occupation_id"),
            inverseJoinColumns = @JoinColumn(name = "timerecord_id")
    )
    @LazyCollection(LazyCollectionOption.TRUE)
    private List<TimeRecord> userTimeRecords = new ArrayList<>();

    public UserOccupation(MachineOccupation machineOccupation) {
        this.machineOccupation = machineOccupation;
    }

    public void startTimeRecord(TimeRecordType timeRecordType, User user)
    {
        var timeRecord = new TimeRecord(null, timeRecordType);
        timeRecord.setStartDateTime(LocalDateTime.now());
        timeRecord.setResponseUser(user);
        this.userTimeRecords.add(timeRecord);

        setUserActive(user);
    }

    public void breakTimeRecord(TimeRecordType timeRecordType, SuspensionType suspensionType, User user)
    {
        //Continue Process
        if(suspensionType == null)
        {
            //Stop all running SuspensionTimeRecords on continue
            this.getAllOpenTimeRecords().filter(timeRecord -> timeRecord.getSuspensionType() != null).forEach(timeRecord -> timeRecord.setEndDateTime(LocalDateTime.now()));
        }
        else
        {
            var oldTimeRecord = this.getCurrentTimeRecordForUser(user);
            if(oldTimeRecord != null) {
                oldTimeRecord.setEndDateTime(LocalDateTime.now());
            }
        }


        var timeRecord = new TimeRecord(suspensionType, timeRecordType);
        timeRecord.setStartDateTime(LocalDateTime.now());
        timeRecord.setResponseUser(user);
        this.userTimeRecords.add(timeRecord);

        setUserInactive(user);
    }

    public void pauseTimeRecord(SuspensionType breakSuspensionType, User user) {
        breakTimeRecord(null, breakSuspensionType, user);
        setUserInactive(user);
    }

    public void continueTimeRecord(TimeRecordType manufacturingTimeRecordType, User user) {
        breakTimeRecord(manufacturingTimeRecordType, null, user);
        setUserActive(user);
    }

    public void stopTimeRecord(User user) {
        var timeRecord = this.getCurrentTimeRecordForUser(user);
        if(timeRecord != null) {
            timeRecord.setEndDateTime(LocalDateTime.now());
        }

        setUserInactive(user);
    }

    @JsonIgnore
    private Stream<TimeRecord> getTimeRecordsForUser(User user)
    {
        return userTimeRecords.stream().filter(tr -> tr.getResponseUser().equals(user));
    }

    @JsonIgnore
    private Stream<TimeRecord> getAllOpenTimeRecords()
    {
        return userTimeRecords.stream().filter(timeRecord -> timeRecord.getEndDateTime() == null);
    }

    public void setUserActive(User user)
    {
        this.inactiveUsers.remove(user);
        if(!this.activeUsers.contains(user))
        {
            this.activeUsers.add(user);
        }
    }

    public void setUserInactive(User user)
    {
        this.activeUsers.remove(user);
        if(!this.inactiveUsers.contains(user))
        {
            this.inactiveUsers.add(user);
        }
    }

    @JsonIgnore
    private TimeRecord getCurrentTimeRecordForUser(User user) {
        return getTimeRecordsForUser(user).filter(timeRecord -> timeRecord.getEndDateTime() == null).findFirst().orElse(null);
    }

    public void stopTimeRecords() {
        var timeRecords = this.getAllOpenTimeRecords();
        timeRecords.forEach(timeRecord -> timeRecord.setEndDateTime(LocalDateTime.now()));
    }

    public void startTimeRecordForActiveUsers(TimeRecordType manufacturingTimeRecordType) {
        this.activeUsers.forEach(user -> this.startTimeRecord(manufacturingTimeRecordType, user));
    }

    public void startOrContinueTimeRecord(User user, TimeRecordType manufacturingTimeRecordType) {
        var oldTimeRecord = this.getCurrentTimeRecordForUser(user);
        if(oldTimeRecord != null) {
            oldTimeRecord.setEndDateTime(LocalDateTime.now());
        }

        var timeRecord = new TimeRecord(null, manufacturingTimeRecordType);
        timeRecord.setStartDateTime(LocalDateTime.now());
        timeRecord.setResponseUser(user);
        this.userTimeRecords.add(timeRecord);
        setUserActive(user);
    }
}
