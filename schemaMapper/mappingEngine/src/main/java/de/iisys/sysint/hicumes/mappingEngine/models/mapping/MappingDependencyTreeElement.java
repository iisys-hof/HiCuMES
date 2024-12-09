package de.iisys.sysint.hicumes.mappingEngine.models.mapping;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
public class MappingDependencyTreeElement {
    private String id;

    private Map<String,MappingDependencyTreeElement> arrays = new HashMap<>();
    private Map<String,String> fields = new HashMap<>();
    private Map<String, Boolean> isCustomerField = new HashMap<>();
    private Map<String,MappingDependencyTreeElement> objects = new HashMap<>();
    private Map<String,MappingDependencyTreeElement> objectsReference = new HashMap<>();

    public MappingDependencyTreeElement(String id) {
        this.id = id;
    }
    public MappingDependencyTreeElement() {
    }
}
