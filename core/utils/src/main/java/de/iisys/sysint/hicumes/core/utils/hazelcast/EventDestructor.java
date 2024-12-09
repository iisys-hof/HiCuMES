package de.iisys.sysint.hicumes.core.utils.hazelcast;

import com.fasterxml.jackson.databind.JsonNode;
import de.iisys.sysint.hicumes.core.events.Events;

public class EventDestructor
{
    private final String eventType = "eventType";
    private final String eventContent = "eventContent";
    private JsonNode jsonNode;

    public EventDestructor(JsonNode jsonNode) {
        this.jsonNode = jsonNode;
    }

    public Events.CamundaReceiveTopic getCamundaReceiveEvent() {
        String type = jsonNode.get(eventType).textValue();

        return Events.CamundaReceiveTopic.valueOf(type);
    }

    public Events.CamundaSendTopic getCamundaSendEvent() {
        String type = jsonNode.get(eventType).textValue();

        return Events.CamundaSendTopic.valueOf(type);
    }

    public Events.MappingSendTopic getMappingSendEvent() {
        String type = jsonNode.get(eventType).textValue();

        return Events.MappingSendTopic.valueOf(type);
    }

    public Events.CoreDataTopic getCoreDataEvent() {
        String type = jsonNode.get(eventType).textValue();

        return Events.CoreDataTopic.valueOf(type);
    }

    public Events.RunMappingTopic getRunMappingEvent() {
        String type = jsonNode.get(eventType).textValue();

        return Events.RunMappingTopic.valueOf(type);
    }

    public Events.MappingReceiveTopic getMappingReceivedEvent() {
        String type = jsonNode.get(eventType).textValue();

        return Events.MappingReceiveTopic.valueOf(type);
    }
    public JsonNode getEventPayload() {
        return jsonNode.get(eventContent);
    }


}
