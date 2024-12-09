package de.iisys.sysint.hicumes.core.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.iisys.sysint.hicumes.core.entities.adapters.DurationAdapter;
import de.iisys.sysint.hicumes.core.entities.adapters.JPADurationAdapter;
import de.iisys.sysint.hicumes.core.entities.jsonViews.JsonViews;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@Data
@ToString(callSuper = true)
@XmlRootElement(name = "overheadCost")
@XmlAccessorType(XmlAccessType.FIELD)
@Table(indexes = @Index(columnList = "externalId"))
public class OverheadCost extends EntitySuperClass{

    @OneToOne(cascade = CascadeType.ALL)
    @JsonView({JsonViews.ViewMachineOccupation.class, JsonViews.SchemaMapperDefault.class})
    User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonView({JsonViews.ViewMachineOccupation.class, JsonViews.SchemaMapperDefault.class})
    TimeRecord timeRecord;

    @XmlJavaTypeAdapter(DurationAdapter.class)
    @Convert(converter = JPADurationAdapter.class)
    private Duration timeDuration;

    private String note;

    @ManyToOne
    @JsonView({JsonViews.ViewMachineOccupation.class, JsonViews.SchemaMapperDefault.class})
    @ToString.Exclude
    private CD_OverheadCostCenter overheadCostCenter;

    public OverheadCost() {
    }

    public OverheadCost(User user, CD_OverheadCostCenter costCenter, String noteString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMddHHmmss");
        var datetimeNow = LocalDateTime.now().format(formatter);

        this.user = user;
        this.overheadCostCenter = costCenter;
        this.note = noteString;
        this.externalId = costCenter.getExternalId() + "_" + user.getUserName() + "_" + datetimeNow;
        this.startTimeRecord();
    }

    @JsonIgnore
    public void startTimeRecord()
    {
        timeRecord = new TimeRecord();
        timeRecord.setStartDateTime(LocalDateTime.now());
        timeRecord.setResponseUser(user);
    }

    @JsonIgnore
    public void stopTimeRecord()
    {
        if(timeRecord != null) {
            timeRecord.setEndDateTime(LocalDateTime.now());
            timeRecord.setResponseUser(user);
            calculateDurations(timeRecord);
        }
    }

    private void calculateDurations(TimeRecord timeRecord)
    {
        timeDuration = Duration.between(timeRecord.getStartDateTime(), timeRecord.getEndDateTime());
    }

    @JsonIgnore
    public JsonNode toCamundaStartProcess(String userName) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMddHHmmss");


        var datetimeNow = LocalDateTime.now().format(formatter);
        ObjectNode eventContent = mapper.createObjectNode()
                .put("key", "Process_GMK")
                .put("businessKey", "gmk_" + datetimeNow)
                .put("userName", userName);
        var variables = eventContent.putObject("variables");
        try {
            ObjectWriter writer = mapper.writerWithView(JsonViews.ViewMachineOccupation.class);
            var jsonString = writer.writeValueAsString(this);
            var json = (ObjectNode) mapper.readTree(jsonString);
            variables.putObject("overheadCost").setAll(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        variables.put("businessKey", "gmk_" + datetimeNow);
        variables.put("key", "Process_GMK");
        variables.put("userName", userName);
        return eventContent;
    }

    @JsonIgnore
    public boolean isOverheadCostFinished() {
        return this.timeRecord != null && this.timeRecord.getEndDateTime() != null && this.timeDuration != null;
    }
}
