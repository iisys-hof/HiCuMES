package de.iisys.sysint.hicumes.core.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import de.iisys.sysint.hicumes.core.entities.adapters.LocalDateTimeAdapter;
import de.iisys.sysint.hicumes.core.entities.annotations.MapperIgnore;
import de.iisys.sysint.hicumes.core.entities.enums.EProductionStatus;
import de.iisys.sysint.hicumes.core.entities.jsonViews.JsonViews;
import de.iisys.sysint.hicumes.core.entities.unit.Measurement;
import de.iisys.sysint.hicumes.core.entities.unit.Unit;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@XmlRootElement(name = "productionOrder")
@XmlAccessorType(XmlAccessType.FIELD)
@Cacheable
@Table(indexes = @Index(columnList = "externalId"))
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProductionOrder extends EntitySuperClass {

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @MapperIgnore
    @ToString.Exclude
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<ProductionOrder> subProductionOrders = new ArrayList<>();

    @JsonView({JsonViews.SchemaMapperDefault.class})
    @ManyToOne(cascade = CascadeType.ALL)
    @ToString.Exclude
    private ProductionOrder parentProductionOrder;

    @Embedded
    private Measurement measurement = new Measurement();

    @ManyToOne
    @JsonView({JsonViews.ViewMachineOccupation.class, JsonViews.SchemaMapperDefault.class})
    @ToString.Exclude
    private CustomerOrder customerOrder;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JsonView({JsonViews.ViewMachineOccupation.class, JsonViews.SchemaMapperDefault.class})
    @ToString.Exclude
    private CD_Product product;

    private String name;

    private int priority;
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime deadline;
    @Enumerated(EnumType.STRING)
    private EProductionStatus status = EProductionStatus.PLANNED;
    @JsonView(JsonViews.SchemaMapperDefault.class)
    @OneToMany(mappedBy = "productionOrder", cascade = CascadeType.ALL)
    private List<MachineOccupation> machineOccupations = new ArrayList<>();

    @JsonView({JsonViews.ViewMachineOccupation.class, JsonViews.SchemaMapperDefault.class})
    @OneToMany(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Note> notes = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "KeyValueMapProductionOrder",
            joinColumns = {@JoinColumn(name = "productionOrder", referencedColumnName = "id")})
    @MapKeyColumn(name = "keyString")
    @Column(name = "valueString")
    @LazyCollection(LazyCollectionOption.FALSE)
    private Map<String, String> keyValueMap = new HashMap<>();

    public ProductionOrder(Measurement measurement, CustomerOrder customerOrder, CD_Product product, int priority, LocalDateTime deadline, EProductionStatus status) {
        this.measurement = measurement;
        if(customerOrder != null) {
            customerOrder.productionOrdersAdd(this);
        }
        this.customerOrder = customerOrder;
        if(product != null) {
            product.productionOrdersAdd(this);
        }
        this.product = product;
        this.priority = priority;
        this.deadline = deadline;
        this.status = status;
    }

    @JsonIgnore
    public void machineOccupationsAdd(MachineOccupation input) {
        input.setProductionOrder(this);
        machineOccupations.add(input);
    }

}
