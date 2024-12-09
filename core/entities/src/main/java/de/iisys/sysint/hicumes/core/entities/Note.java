package de.iisys.sysint.hicumes.core.entities;

import de.iisys.sysint.hicumes.core.entities.adapters.LocalDateTimeAdapter;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@XmlRootElement(name = "note")
@XmlAccessorType(XmlAccessType.FIELD)
@Table(indexes = @Index(columnList = "externalId"))
public class Note extends EntitySuperClass {

    private String noteString;
    private String userName;

    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime creationDateTime;

    public Note(String noteString, String userName, LocalDateTime creationDateTime) {
        this.noteString = noteString;
        this.userName = userName;
        this.creationDateTime = creationDateTime;
    }
}
