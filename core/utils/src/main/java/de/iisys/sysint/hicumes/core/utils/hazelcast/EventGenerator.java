package de.iisys.sysint.hicumes.core.utils.hazelcast;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.iisys.sysint.hicumes.core.events.Events;
import de.iisys.sysint.hicumes.core.events.IEvent;

import java.util.List;

public class EventGenerator
{
    ObjectMapper mapper = new ObjectMapper();

    public JsonNode generateEvent(IEvent eventType, IEvent responseType, JsonNode eventContent) {


        ObjectNode node = (ObjectNode) generateEvent(eventType, eventContent);

        if(responseType instanceof Events.CamundaReceiveTopic)
        {
            node.put("responseTopic", Events.CamundaReceiveTopic.getTopic());
        }
        else if(responseType instanceof Events.MappingReceiveTopic)
        {
            node.put("responseTopic", Events.MappingReceiveTopic.getTopic());
        }
        else if(responseType instanceof Events.MappingSendTopic)
        {
            node.put("responseTopic", Events.MappingSendTopic.getTopic());
        }
        else if(responseType instanceof Events.CamundaSendTopic)
        {
            node.put("responseTopic", Events.CamundaSendTopic.getTopic());
        }
        else if(responseType instanceof Events.CoreDataTopic)
        {
            node.put("responseTopic", Events.CoreDataTopic.getTopic());
        }
        else if(responseType instanceof Events.RunMappingTopic)
        {
            node.put("responseTopic", Events.RunMappingTopic.getTopic());
        }
        node.put("responseType", responseType.toString());
        return node;
    }

    public JsonNode generateEvent(IEvent eventType, JsonNode eventContent) {
        ObjectNode node = mapper.createObjectNode();
        if(eventType instanceof Events.CamundaReceiveTopic)
        {
            node.put("eventTopic", Events.CamundaReceiveTopic.getTopic());
        }
        else if(eventType instanceof Events.MappingReceiveTopic)
        {
            node.put("eventTopic", Events.MappingReceiveTopic.getTopic());
        }
        else if(eventType instanceof Events.MappingSendTopic)
        {
            node.put("eventTopic", Events.MappingSendTopic.getTopic());
        }
        else if(eventType instanceof Events.CamundaSendTopic)
        {
            node.put("eventTopic", Events.CamundaSendTopic.getTopic());
        }
        else if(eventType instanceof Events.CoreDataTopic)
        {
            node.put("eventTopic", Events.CoreDataTopic.getTopic());
        }
        else if(eventType instanceof Events.RunMappingTopic)
        {
            node.put("eventTopic", Events.RunMappingTopic.getTopic());
        }
        node.put("eventType", eventType.toString());
        node.set("eventContent", eventContent);
        return node;
    }

    public JsonNode generateEvent(IEvent eventType) {
        JsonNode node = this.generateEvent(eventType, mapper.createObjectNode());
        return node;
    }

    public JsonNode generateEvent(IEvent eventType, List list) {

        var listNode = mapper.valueToTree(list);
        JsonNode node = this.generateEvent(eventType, listNode);
        return node;
    }
}
