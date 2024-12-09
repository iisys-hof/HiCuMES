package de.iisys.sysint.hicumes.core.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import de.iisys.sysint.hicumes.core.entities.adapters.LocalDateTimeAdapter;
import de.iisys.sysint.hicumes.core.entities.jsonViews.JsonViews;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@XmlRootElement(name = "customerOrder")
@XmlAccessorType(XmlAccessType.FIELD)
@Cacheable
@Table(indexes = @Index(columnList = "externalId"))
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CustomerOrder extends EntitySuperClass {

    private String name;
    private String customerName;
    private int priority;

    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime deadline;

    @JsonView(JsonViews.ViewHideEverywhere.class)
    @OneToMany(mappedBy = "customerOrder", cascade = CascadeType.ALL)
    private List<ProductionOrder> productionOrders = new ArrayList<>();

    public CustomerOrder(String name, String customerName, int priority, LocalDateTime deadline) {
        this.name = name;
        this.customerName = customerName;
        this.priority = priority;
        this.deadline = deadline;
    }

    @JsonIgnore
    public void productionOrdersAdd(ProductionOrder input) {
        input.setCustomerOrder(this);
        productionOrders.add(input);
    }


}
