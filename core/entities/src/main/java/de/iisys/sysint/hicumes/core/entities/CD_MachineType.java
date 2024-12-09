package de.iisys.sysint.hicumes.core.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
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
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@ToString(callSuper = true)
@XmlRootElement(name = "cD_MachineType")
@XmlAccessorType(XmlAccessType.FIELD)
@Cacheable
@Table(indexes = @Index(columnList = "externalId"))
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CD_MachineType extends EntitySuperClass {

    private String name;

    @JsonView(JsonViews.SchemaMapperDefault.class)
    @OneToMany(mappedBy = "machineType", cascade = CascadeType.ALL)
    private List<CD_Machine> machines = new ArrayList<>();

    @JsonView(JsonViews.SchemaMapperDefault.class)
    @OneToMany(mappedBy = "machineType", cascade = CascadeType.ALL)
    private List<CD_ProductionStep> productionSteps = new ArrayList<>();

    @JsonView(JsonViews.SchemaMapperDefault.class)
    @OneToMany(mappedBy = "machineType", cascade = CascadeType.ALL)
    private List<CD_ToolSettingParameter> toolSettingParameters = new ArrayList<>();

    @JsonView({JsonViews.ViewMachineOccupation.class, JsonViews.SchemaMapperDefault.class})
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "CD_MachineType_CD_Department",
            joinColumns = @JoinColumn(name = "machineType"),
            inverseJoinColumns = @JoinColumn(name = "department"))
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<CD_Department> departments = new ArrayList<>();

    public CD_MachineType(String name) {
        this.name = name;
    }

    @JsonIgnore
    public void machinesAdd(CD_Machine input) {
        input.setMachineType(this);
        machines.add(input);
    }

    @JsonIgnore
    public void productionStepsAdd(CD_ProductionStep input) {
        input.setMachineType(this);
        productionSteps.add(input);
    }

    @JsonIgnore
    public void toolSettingParametersAdd(CD_ToolSettingParameter input) {
        input.setMachineType(this);
        toolSettingParameters.add(input);
    }
}
