package de.iisys.sysint.hicumes.core.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import de.iisys.sysint.hicumes.core.entities.adapters.JPADurationAdapter;
import de.iisys.sysint.hicumes.core.entities.enums.ESubProductionStepType;
import de.iisys.sysint.hicumes.core.entities.enums.ESubmitType;
import de.iisys.sysint.hicumes.core.entities.jsonViews.JsonViews;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@XmlRootElement(name = "subProductionStep")
@XmlAccessorType(XmlAccessType.FIELD)
@Table(indexes = @Index(columnList = "externalId"))
public class SubProductionStep extends EntitySuperClass {

    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ToString.Exclude
    private MachineOccupation machineOccupation;

    private ESubProductionStepType type;
    private ESubmitType submitType;
    //private LocalDateTime startDateTime;
    //private LocalDateTime endDateTime;
    private String note;

    @JsonView({JsonViews.SchemaMapperDefault.class, JsonViews.ViewMachineOccupation.class})
    @OneToMany(mappedBy = "subProductionStep", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<AuxiliaryMaterials> auxiliaryMaterials = new ArrayList<>();

    @JsonView({JsonViews.SchemaMapperDefault.class, JsonViews.ViewMachineOccupation.class})
    @OneToMany(mappedBy = "subProductionStep", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<ProductionNumbers> productionNumbers = new ArrayList<>();

    @JsonView({JsonViews.SchemaMapperDefault.class, JsonViews.ViewMachineOccupation.class})
    @OneToMany(mappedBy = "subProductionStep", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<QualityManagement> qualityManagements = new ArrayList<>();

    @JsonView({JsonViews.SchemaMapperDefault.class, JsonViews.ViewMachineOccupation.class})
    @OneToMany(mappedBy = "subProductionStep", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<SetUp> setUps = new ArrayList<>();

    @JsonView({JsonViews.SchemaMapperDefault.class, JsonViews.ViewMachineOccupation.class})
    @OneToMany(mappedBy = "subProductionStep", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<TimeRecord> timeRecords = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "Time_DurationsStep",
            joinColumns = {@JoinColumn(name = "subProductionStep", referencedColumnName = "id")})
    @MapKeyColumn(name = "timeType")
    @Column(name = "duration")
    @Convert(converter = JPADurationAdapter.class, attributeName = "value")
    @LazyCollection(LazyCollectionOption.FALSE)
    private Map<String, Duration> timeDurations = new HashMap<>();

    @ElementCollection
    @CollectionTable(name = "KeyValueMapSubProductionStep",
            joinColumns = {@JoinColumn(name = "subProductionStep", referencedColumnName = "id")})
    @MapKeyColumn(name = "keyString")
    @Column(name = "valueString")
    @LazyCollection(LazyCollectionOption.FALSE)
    private Map<String, String> keyValueMap = new HashMap<>();

    public SubProductionStep(ESubProductionStepType type, MachineOccupation machineOccupation) {
        this.type = type;
        machineOccupation.subProductionStepsAdd(this);
        this.machineOccupation = machineOccupation;
    }

    @JsonIgnore
    public void auxiliaryMaterialsAdd(AuxiliaryMaterials input) {
        input.setSubProductionStep(this);
        auxiliaryMaterials.add(input);
    }

    @JsonIgnore
    public void productionNumbersAdd(ProductionNumbers input) {
        input.setSubProductionStep(this);
        productionNumbers.add(input);
    }

    @JsonIgnore
    public void qualityManagementsAdd(QualityManagement input) {
        input.setSubProductionStep(this);
        qualityManagements.add(input);
    }

    @JsonIgnore
    public void setUpsAdd(SetUp input) {
        input.setSubProductionStep(this);
        setUps.add(input);
    }

    @JsonIgnore
    public void timeRecordsAdd(TimeRecord input) {
        input.setSubProductionStep(this);
        timeRecords.add(input);
    }

    @JsonIgnore
    public boolean isTimeRecordFinished()
    {
        return this.getCurrentTimeRecord() != null && this.getCurrentTimeRecord().getEndDateTime() != null;
    }

    @JsonIgnore
    public void startTimeRecord(TimeRecordType timeRecordType, User user)
    {
        var timeRecord = new TimeRecord(this, null, timeRecordType);
        timeRecord.setStartDateTime(LocalDateTime.now());
        timeRecord.setResponseUser(user);
    }

    @JsonIgnore
    private void breakTimeRecord(TimeRecordType timeRecordType, SuspensionType suspensionType, User user)
    {
        var oldTimeRecord = this.getCurrentTimeRecord();
        if(oldTimeRecord != null) {
            oldTimeRecord.setEndDateTime(LocalDateTime.now());
            calculateDurations(oldTimeRecord);
        }

        var timeRecord = new TimeRecord(this, suspensionType, timeRecordType);
        timeRecord.setStartDateTime(LocalDateTime.now());
        timeRecord.setResponseUser(user);
    }

    @JsonIgnore
    public void pauseTimeRecord(TimeRecordType timeRecordType, SuspensionType suspensionType, User user)
    {
        breakTimeRecord(timeRecordType, suspensionType, user);
    }

    @JsonIgnore
    public void continueTimeRecord(TimeRecordType timeRecordType, User user)
    {
        breakTimeRecord(timeRecordType, null, user);
    }



    @JsonIgnore
    public void stopTimeRecord(User user)
    {
        this.stopTimeRecord(user, null);
    }


    @JsonIgnore
    public void stopTimeRecord(User user, Duration customDuration)
    {
        var timeRecord = this.getCurrentTimeRecord();
        if(timeRecord != null && timeRecord.getEndDateTime() == null) {
            timeRecord.setEndDateTime(LocalDateTime.now());
            if(user != null)
            {
                timeRecord.setResponseUser(user);
            }
            else
            {
                this.setSubmitType(ESubmitType.OTHER);
            }

            calculateDurations(timeRecord, customDuration);
        }
    }

    @JsonIgnore
    public Duration stopTimeRecord(User user, long timeOffsetSeconds)
    {
        var timeRecord = this.getCurrentTimeRecord();
        Duration remainder = Duration.ZERO;
        if(timeRecord != null && timeRecord.getEndDateTime() == null) {
            //If the enddate with the timeOffset (can be negative) is less than the startdate, we make sure, there is at least one minute of time and return the remainder
            if(Duration.between(timeRecord.getStartDateTime(),LocalDateTime.now().plusSeconds(timeOffsetSeconds)).isNegative()) {
                remainder = Duration.between(timeRecord.getStartDateTime(),LocalDateTime.now().plusSeconds(timeOffsetSeconds+60)).negated();
                timeRecord.setEndDateTime(timeRecord.getStartDateTime().plusSeconds(60));
            }
            else {
                timeRecord.setEndDateTime(LocalDateTime.now().plusSeconds(timeOffsetSeconds));
            }

            if (user != null) {
                timeRecord.setResponseUser(user);
            } else {
                this.setSubmitType(ESubmitType.OTHER);
            }
            calculateDurations(timeRecord);
        }
        return remainder;
    }

    private void calculateDurations(TimeRecord timeRecord) {
        this.calculateDurations(timeRecord, null);
    }

    private void calculateDurations(TimeRecord timeRecord, Duration customDuration)
    {
        Duration currentDuration = null;
        if(customDuration != null)
        {
            currentDuration = customDuration;
        }
        else {
            currentDuration = Duration.between(timeRecord.getStartDateTime(), timeRecord.getEndDateTime());
        }
        var timeRecordType = "";
        if(timeRecord.getTimeRecordType() != null)
        {
            timeRecordType = timeRecord.getTimeRecordType().getName();
            //Also calculate time for subProdStep
            var oldDurationStep = this.getTimeDurations().get("work");
            if(oldDurationStep != null)
            {
                this.getTimeDurations().replace("work", oldDurationStep.plus(currentDuration));
            }
            else
            {
                this.getTimeDurations().put("work", currentDuration);
            }
            var oldDurationSum = this.machineOccupation.getTimeDurations().get("work");
            if(oldDurationSum != null)
            {
                this.machineOccupation.getTimeDurations().replace("work", oldDurationSum.plus(currentDuration));
            }
            else
            {
                this.machineOccupation.getTimeDurations().put("work", currentDuration);
            }
        }
        else if(timeRecord.getSuspensionType() != null)
        {
            timeRecordType = timeRecord.getSuspensionType().getName();
            //Also calculate time for subProdStep
            var oldDurationStep = this.getTimeDurations().get("break");
            if(oldDurationStep != null)
            {
                this.getTimeDurations().replace("break", oldDurationStep.plus(currentDuration));
            }
            else
            {
                this.getTimeDurations().put("break", currentDuration);
            }
            var oldDurationSum = this.machineOccupation.getTimeDurations().get("break");
            if(oldDurationSum != null)
            {
                this.machineOccupation.getTimeDurations().replace("break", oldDurationSum.plus(currentDuration));
            }
            else
            {
                this.machineOccupation.getTimeDurations().put("break", currentDuration);
            }
        }

        var oldDuration = this.machineOccupation.getTimeDurations().get(timeRecordType);
        if(oldDuration != null)
        {
            this.machineOccupation.getTimeDurations().replace(timeRecordType, oldDuration.plus(currentDuration));
        }
        else
        {
            this.machineOccupation.getTimeDurations().put(timeRecordType, currentDuration);
        }

    }

    @JsonIgnore
    private TimeRecord getCurrentTimeRecord() {
        if(timeRecords.size() >= 1) {
            return timeRecords.get(timeRecords.size() - 1);
        }

        return null;
    }

    public void updateFromMappingOutput(SubProductionStep subProductionStep) {
        this.updateFromMappingOutput(subProductionStep, true);
    }

    public void updateFromMappingOutput(SubProductionStep subProductionStep, boolean overWriteWithEmpty) {
        this.type = subProductionStep.getType();
        //Only update field, if new value is not empty or overWriteWithEmpty is set
        if (overWriteWithEmpty || subProductionStep.getNote() != null && (!subProductionStep.getNote().isBlank() && !subProductionStep.getNote().isEmpty()))
        {
            this.note = subProductionStep.getNote();
        }
        //}
        //this.startDateTime = subProductionStep.getStartDateTime();
        //this.endDateTime = subProductionStep.getEndDateTime();
        this.auxiliaryMaterials = subProductionStep.getAuxiliaryMaterials();
        this.auxiliaryMaterials.forEach(auxiliaryMaterial -> auxiliaryMaterial.setSubProductionStep(this));
        this.productionNumbers = subProductionStep.getProductionNumbers();
        this.productionNumbers.forEach(productionNumber -> productionNumber.setSubProductionStep(this));
        this.qualityManagements = subProductionStep.getQualityManagements();
        this.qualityManagements.forEach(qualityManagement -> qualityManagement.setSubProductionStep(this));
        this.setUps = subProductionStep.getSetUps();
        this.setUps.forEach(setUp -> setUp.setSubProductionStep(this));

        //Only update fields in subProductionStep, that are already empty or not in the set
        for (Map.Entry<String, String> newEntry : subProductionStep.getKeyValueMap().entrySet()) {
            String key = newEntry.getKey();
            String newValue = newEntry.getValue();

            if ((!newValue.isBlank() && !newValue.isEmpty()) || overWriteWithEmpty)
                this.keyValueMap.put(key, newValue);
            }
    }

    public void updateMachineOccupation(MachineOccupation machineOccupation) {
        machineOccupation.subProductionStepsAdd(this);
        this.machineOccupation = machineOccupation;
    }

}
