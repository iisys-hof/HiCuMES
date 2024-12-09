package de.iisys.sysint.hicumes.mappingEngine.models.mapping;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class MappingDependencyTree {
    private Map<String, MappingDependencyTreeElement> tree;
    public String toJson() throws JsonProcessingException {
        var objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        String result = objectMapper.writeValueAsString(tree);
        return result;
    }
    public MappingDependencyTree() {
    }
}

