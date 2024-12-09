package de.iisys.sysint.hicumes.mappingBackend.models.frontendFieldExtension;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
public class AllClassExtension {
    @Id
    protected long id;

    @OneToMany(cascade = CascadeType.ALL)
    private List<ClassExtension> classes;

    public AllClassExtension(List<ClassExtension> classes) {
        this.classes = classes;
    }

    public AllClassExtension() {
    }
}
