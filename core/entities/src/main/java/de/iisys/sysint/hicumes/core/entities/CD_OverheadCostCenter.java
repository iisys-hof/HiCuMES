package de.iisys.sysint.hicumes.core.entities;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Data
@ToString(callSuper = true)
@XmlRootElement(name = "cD_OverheadCostCenter")
@XmlAccessorType(XmlAccessType.FIELD)
@Table(indexes = @Index(columnList = "externalId"))
public class CD_OverheadCostCenter extends EntitySuperClass{

    private String name;

    public CD_OverheadCostCenter() {
    }
}
