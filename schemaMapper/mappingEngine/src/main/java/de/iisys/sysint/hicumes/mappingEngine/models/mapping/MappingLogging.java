package de.iisys.sysint.hicumes.mappingEngine.models.mapping;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class MappingLogging {
    private List<String> log = new ArrayList<String>();
    private List<String> error = new ArrayList<String>();

    public MappingLogging() {
    }
}
