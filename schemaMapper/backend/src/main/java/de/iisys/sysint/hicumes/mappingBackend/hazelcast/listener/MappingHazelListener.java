package de.iisys.sysint.hicumes.mappingBackend.hazelcast.listener;

import com.fasterxml.jackson.databind.JsonNode;
import com.hazelcast.topic.Message;
import com.hazelcast.topic.MessageListener;
import de.iisys.sysint.hicumes.core.events.Events;
import de.iisys.sysint.hicumes.core.utils.hazelcast.EventDestructor;
import de.iisys.sysint.hicumes.core.utils.hazelcast.EventGenerator;
import de.iisys.sysint.hicumes.core.utils.logger.Logger;
import de.iisys.sysint.hicumes.mappingBackend.events.EventController;
import de.iisys.sysint.hicumes.mappingBackend.startup.StartupController;
import lombok.SneakyThrows;

import javax.ejb.EJB;
import javax.inject.Inject;

public class MappingHazelListener implements MessageListener<JsonNode> {

    private final Logger logger = new Logger(this.getClass().getName());
    @EJB
    private EventController eventController;

    @SneakyThrows
    @Override
    public void onMessage(Message<JsonNode> message) {

        JsonNode json = message.getMessageObject();
        //logger.logMessage("RECEIVED MAPPING DATA: ", ">", json.toPrettyString());

        var event = new EventDestructor(json);

        Events.MappingReceiveTopic eventType = event.getMappingReceivedEvent();
        JsonNode contentNode = event.getEventPayload();
        switch (eventType) {
            case REQUEST_CAMUNDA_MAPPINGS:
                eventController.sendCamundaMappings();
                break;
            case RUN_SYNC:
                eventController.runMapping(contentNode);

                //var eventResponse = new EventGenerator().generateEvent(Events.MappingReceiveTopic.RUN_SYNC_DONE,  json);
                //StartupController.hazelServer.sendEvent(eventResponse);
                break;
/*            case RUN_SYNC_DONE:
            case NEW_MULTIPLE_DONE:
                //eventController.runMappingDone(contentNode);
                StartupController.hazelServer.handleResponse(json);
                break;*/
        }

    }
}
