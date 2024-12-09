package de.iisys.sysint.hicumes.mappingBackend.models.frontendFieldExtension;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FlatMemberExtension {
    private String className;
    private String classId;
    private String name;
    private String type;
    private boolean isCustomerField;
}
