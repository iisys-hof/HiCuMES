package de.iisys.sysint.hicumes.manufacturingTerminalBackend.events;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.EventEntity;
import lombok.Data;
import lombok.Generated;

@Generated
@Data
public class EventRunMapping extends EventEntity {
    String mappingName;
    boolean useSavedData;
    boolean isSimulate;
    JsonNode writerConfig;
    JsonNode writerParserConfig;
    JsonNode readerConfig;
    JsonNode readerParserConfig;

    public EventRunMapping(String mappingName, boolean useSavedData, boolean isSimulate, JsonNode writerConfig, JsonNode writerParserConfig, JsonNode readerConfig, JsonNode readerParserConfig) {
        this.mappingName = mappingName;
        this.useSavedData = useSavedData;
        this.isSimulate = isSimulate;
        this.writerConfig = writerConfig;
        this.writerParserConfig = writerParserConfig;
        this.readerConfig = readerConfig;
        this.readerParserConfig = readerParserConfig;
    }

    public String toString() {
        return getClass().getSimpleName();
    }
}
