package de.iisys.sysint.hicumes.mappingBackend.models.frontendFieldExtension;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.List;


@Entity
@Data
@AllArgsConstructor
public class ClassExtension {

    public ClassExtension(String name, String id, List<MemberExtension> members) {
        this.name = name;
        this.id = id;
        this.members = members;
    }

    @Id
    @GeneratedValue
    private long fieldId;

    private String name;
    private String id;

    public ClassExtension() {
    }

    @OneToMany(cascade = CascadeType.ALL)
    private List<MemberExtension> members;
}
