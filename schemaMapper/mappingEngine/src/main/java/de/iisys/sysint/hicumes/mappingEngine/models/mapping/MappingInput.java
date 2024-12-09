package de.iisys.sysint.hicumes.mappingEngine.models.mapping;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MappingInput {
    private JsonNode jsonNode;

    public MappingInput() {
    }
}
