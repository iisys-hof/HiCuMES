package de.iisys.sysint.hicumes.core.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import de.iisys.sysint.hicumes.core.entities.adapters.JPADurationAdapter;
import de.iisys.sysint.hicumes.core.entities.adapters.LocalDateTimeAdapter;
import de.iisys.sysint.hicumes.core.entities.annotations.MapperIgnore;
import de.iisys.sysint.hicumes.core.entities.enums.EMachineOccupationStatus;
import de.iisys.sysint.hicumes.core.entities.jsonViews.JsonViews;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Entity
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@XmlRootElement(name = "machineOccupation")
@XmlAccessorType(XmlAccessType.FIELD)
@Cacheable
@Table(indexes = @Index(columnList = "externalId"))
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)

@NamedEntityGraph(
        name = "Graph.MachineOccupation.ProductionSteps",
        attributeNodes = {
                @NamedAttributeNode(value = "productionSteps")
        }
)

public class MachineOccupation extends EntitySuperClass {

    @JsonView(JsonViews.ViewMachineOccupationParent.class)
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @MapperIgnore
    @ToString.Exclude
    @LazyCollection(LazyCollectionOption.TRUE)
    private List<MachineOccupation> subMachineOccupations = new ArrayList<>();

    @JsonView(JsonViews.ViewMachineOccupationChild.class)
    @ManyToOne(cascade = CascadeType.ALL)
    @MapperIgnore
    @ToString.Exclude
    private MachineOccupation parentMachineOccupation;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    @MapperIgnore
    @ToString.Exclude
    private UserOccupation userOccupation;// = new UserOccupation(this);

    @JsonView({JsonViews.ViewMachineOccupation.class, JsonViews.SchemaMapperDefault.class})
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "MachineOccupation_CD_ProductionStep",
            joinColumns = @JoinColumn(name = "machineOccupation"),
            inverseJoinColumns = @JoinColumn(name = "productionStep"))
    @LazyCollection(LazyCollectionOption.TRUE)
    private List<CD_ProductionStep> productionSteps = new ArrayList<>();

    @ManyToOne
    @JsonView({JsonViews.ViewMachineOccupation.class, JsonViews.SchemaMapperDefault.class})
    @ToString.Exclude
    private CD_Machine machine;

    @ManyToOne
    @JsonView({JsonViews.ViewMachineOccupation.class, JsonViews.SchemaMapperDefault.class})
    @ToString.Exclude
    private CD_Tool tool;

    @JsonView({JsonViews.ViewMachineOccupation.class, JsonViews.SchemaMapperDefault.class})
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "MachineOccupation_CD_Tool",
            joinColumns = @JoinColumn(name = "machineOccupation"),
            inverseJoinColumns = @JoinColumn(name = "tool"))
    @LazyCollection(LazyCollectionOption.TRUE)
    @ToString.Exclude
    private List<CD_Tool> availableTools = new ArrayList<>();

    @ManyToOne
    @JsonView({JsonViews.ViewMachineOccupation.class, JsonViews.SchemaMapperDefault.class})
    @ToString.Exclude
    private ProductionOrder productionOrder;

    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime plannedStartDateTime;
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime plannedEndDateTime;
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime actualStartDateTime;
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime actualEndDateTime;

    private String camundaProcessName;
    private String name;

    @Enumerated(EnumType.STRING)
    private EMachineOccupationStatus status = EMachineOccupationStatus.READY_TO_START;

    @JsonView({JsonViews.ViewMachineOccupation.class, JsonViews.SchemaMapperDefault.class})
    @OneToMany(mappedBy = "machineOccupation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @LazyCollection(LazyCollectionOption.TRUE)
    private List<SubProductionStep> subProductionSteps = new ArrayList<>();

    @JsonView({JsonViews.ViewMachineOccupation.class, JsonViews.SchemaMapperDefault.class})
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "MachineOccupation_ActiveToolSetting",
            joinColumns = @JoinColumn(name = "machineOccupation"),
            inverseJoinColumns = @JoinColumn(name = "toolSetting"))
    @LazyCollection(LazyCollectionOption.TRUE)
    private List<ToolSetting> activeToolSettings = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JsonView({JsonViews.ViewMachineOccupation.class, JsonViews.SchemaMapperDefault.class})
    private ProductionNumbers totalProductionNumbers = new ProductionNumbers();

    @ElementCollection
    @CollectionTable(name = "Time_Durations",
            joinColumns = {@JoinColumn(name = "machineOccupation", referencedColumnName = "id")})
    @MapKeyColumn(name = "timeType")
    @Column(name = "duration")
    @Convert(converter = JPADurationAdapter.class, attributeName = "value")
    @LazyCollection(LazyCollectionOption.FALSE)
    private Map<String, Duration> timeDurations = new HashMap<>();

    @ManyToOne
    @JsonView({JsonViews.ViewMachineOccupation.class, JsonViews.SchemaMapperDefault.class})
    @ToString.Exclude
    private CD_Department department;

    public MachineOccupation(List<CD_ProductionStep> productionSteps, CD_Tool tool, ProductionOrder productionOrder, String camundaProcessName) {
        //CDProductionStep.machineOccupationsAdd(this);
        this.productionSteps.addAll(productionSteps);

        if(tool != null) {
            tool.machineOccupationsAdd(this);
        }
        this.tool = tool;

        if(productionOrder != null) {
            productionOrder.machineOccupationsAdd(this);
        }
        this.productionOrder = productionOrder;

        this.camundaProcessName = camundaProcessName;
    }

    @JsonIgnore
    public void subProductionStepsAdd(SubProductionStep input) {
        input.setMachineOccupation(this);
        subProductionSteps.add(input);
    }

    @JsonIgnore
    public void setActiveToolSettings(List<ToolSetting> input)
    {
        this.activeToolSettings = input;
    }

    @JsonIgnore
    public void updateMachine(CD_Machine input) {
        input.machineOccupationsAdd(this);
        this.machine = input;
    }

    @JsonIgnore
    public void activeToolSettingsAdd(ToolSetting toolSetting) {
        this.activeToolSettings.add(toolSetting);
    }

    @JsonIgnore
    public boolean isCollection() {
        return this.subMachineOccupations.size() > 0;
    }

    public void overwriteToolSetting(ToolSetting toolSetting) {
        this.activeToolSettings = this.getActiveToolSettings().stream().map(toolSetting1 -> {
            if(toolSetting1.getExternalId().equals(toolSetting.getExternalId()))
            {
                toolSetting1 = toolSetting;
            }
            return toolSetting1;
        }).collect(Collectors.toList());
    }

    @JsonIgnore
    public boolean isCurrentSubProductionStepFinished()
    {
        return getCurrentSubProductionStep() != null && getCurrentSubProductionStep().isTimeRecordFinished();
    }

    @JsonIgnore
    public SubProductionStep getCurrentSubProductionStep() {
        if(subProductionSteps.size() > 0)
        {
            return subProductionSteps.get(subProductionSteps.size() - 1);
        }
        else
        {
            return null;
        }
    }
}
