package de.iisys.sysint.hicumes.manufacturingTerminalBackend.hazelcast;

import com.fasterxml.jackson.databind.JsonNode;
//import com.hazelcast.core.Message;
//import com.hazelcast.core.MessageListener;
import com.hazelcast.topic.Message;
import com.hazelcast.topic.MessageListener;
import de.iisys.sysint.hicumes.core.entities.jsonViews.JsonViews;
import de.iisys.sysint.hicumes.core.events.Events;
import de.iisys.sysint.hicumes.core.utils.exceptions.UtilsBaseException;
import de.iisys.sysint.hicumes.core.utils.hazelcast.EventDestructor;
import de.iisys.sysint.hicumes.core.utils.json.JsonTransformer;
import de.iisys.sysint.hicumes.core.utils.logger.Logger;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.*;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.IStaticDependencies;
import lombok.SneakyThrows;

public class RunMappingHazelListener implements MessageListener<JsonNode>, IStaticDependencies
{

    private final Logger logger = Logger.getInstance(this.getClass().getName(), this.getClass().getSimpleName());

    @SneakyThrows
    @Override
    public void onMessage(Message<JsonNode> message)
    {
        JsonNode json = message.getMessageObject();
        logger.logMessage("RECEIVED CAMUNDA DATA: ", ">", json.toPrettyString());

        var event = new EventDestructor(json);

        Events.RunMappingTopic eventType = event.getRunMappingEvent();
        JsonNode contentNode = event.getEventPayload();
        switch (eventType)
        {
            case RUN_MAPPING:
                var mappingName = contentNode.get("mappingName").toString().replace("\"", "");
                var useSavedData = contentNode.get("useSavedData").asBoolean();
                var isSimulate = contentNode.get("isSimulate").asBoolean();
                var writerConfig = contentNode.get("writerConfig");
                var writerParserConfig = contentNode.get("writerParserConfig");
                var readerConfig = contentNode.get("readerConfig");
                var readerParserConfig = contentNode.get("readerParserConfig");

                var eventRunMapping = new EventRunMapping(mappingName, useSavedData, isSimulate, writerConfig, writerParserConfig, readerConfig, readerParserConfig);
                events.emit(eventRunMapping);
                break;

        }
    }
}
