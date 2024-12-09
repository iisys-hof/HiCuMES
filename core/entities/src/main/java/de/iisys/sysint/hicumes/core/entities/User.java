package de.iisys.sysint.hicumes.core.entities;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
@Table(indexes = @Index(columnList = "externalId"))
public class User extends EntitySuperClass {

    @Column(unique = true)
    private String userName;

    private String fullUserName;

    public User(String userName)
    {
        this.userName = userName;
    }

    public User(String userName, String fullUserName)
    {
        this.userName = userName;
        this.fullUserName = fullUserName;
    }
}
