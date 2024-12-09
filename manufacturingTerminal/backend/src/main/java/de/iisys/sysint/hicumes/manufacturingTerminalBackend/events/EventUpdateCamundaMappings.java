package de.iisys.sysint.hicumes.manufacturingTerminalBackend.events;

import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.EventEntity;
import de.iisys.sysint.hicumes.mappingEngine.models.MappingAndDataSource;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class EventUpdateCamundaMappings extends EventEntity {
    MappingAndDataSource[] mappingAndDataSources;

    public String toString() {
        return getClass().getSimpleName() + " and contains mappings: " + mappingAndDataSources.length ;
    }
}
