package de.iisys.sysint.hicumes.core.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import de.iisys.sysint.hicumes.core.entities.adapters.LocalDateTimeAdapter;
import de.iisys.sysint.hicumes.core.entities.jsonViews.JsonViews;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
@XmlRootElement(name="EntitySuperClass")
@XmlAccessorType(XmlAccessType.FIELD)
@Table(indexes = @Index(columnList = "externalId"))
public class EntitySuperClass implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    protected long id;

    @Column(unique = true)
    protected String externalId;;

    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    @CreationTimestamp
    @JsonIgnore
    @ToString.Exclude
    private LocalDateTime createDateTime;

    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    @UpdateTimestamp
    @JsonIgnore
    @ToString.Exclude
    private LocalDateTime updateDateTime;

    @JsonView({JsonViews.ViewMachineOccupation.class, JsonViews.SchemaMapperDefault.class})
    protected String versionNr;

}
