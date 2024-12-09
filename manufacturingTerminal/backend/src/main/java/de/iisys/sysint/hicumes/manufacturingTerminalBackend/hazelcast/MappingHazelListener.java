package de.iisys.sysint.hicumes.manufacturingTerminalBackend.hazelcast;

import com.fasterxml.jackson.databind.JsonNode;
import com.hazelcast.topic.Message;
import com.hazelcast.topic.MessageListener;
import de.iisys.sysint.hicumes.core.entities.jsonViews.JsonViews;
import de.iisys.sysint.hicumes.core.events.Events;
import de.iisys.sysint.hicumes.core.utils.hazelcast.EventDestructor;
import de.iisys.sysint.hicumes.core.utils.json.JsonTransformer;
import de.iisys.sysint.hicumes.core.utils.logger.Logger;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.IStaticDependencies;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventCamundaProcessStarted;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventUpdateCamundaMappings;
import de.iisys.sysint.hicumes.mappingEngine.models.MappingAndDataSource;
import lombok.SneakyThrows;

public class MappingHazelListener implements MessageListener<JsonNode>, IStaticDependencies {
    private final Logger logger = Logger.getInstance(this.getClass().getName(), this.getClass().getSimpleName());

    private final JsonTransformer jsonTransformer = new JsonTransformer(JsonViews.ViewMachineOccupation.class);

    @SneakyThrows
    @Override
    public void onMessage(Message<JsonNode> message) {
        JsonNode json = message.getMessageObject();
        logger.logMessage("RECEIVED CAMUNDA MAPPINGS: ", ">", json.toPrettyString());
        var event = new EventDestructor(json);

        Events.MappingSendTopic eventType = event.getMappingSendEvent();
        JsonNode contentNode = event.getEventPayload();

            switch (eventType) {
                case CAMUNDA_MAPPINGS:
                    var updateCamundaMappings = new EventUpdateCamundaMappings(jsonTransformer.readValue(contentNode, MappingAndDataSource[].class));
                    events.emit(updateCamundaMappings);
                    break;
            }

    }
}
