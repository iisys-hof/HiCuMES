package de.iisys.sysint.hicumes.mappingBackend.hazelcast.listener;

import com.fasterxml.jackson.databind.JsonNode;
import com.hazelcast.topic.Message;
import com.hazelcast.topic.MessageListener;
import de.iisys.sysint.hicumes.core.events.Events;
import de.iisys.sysint.hicumes.core.utils.hazelcast.EventDestructor;
import de.iisys.sysint.hicumes.core.utils.logger.Logger;
import de.iisys.sysint.hicumes.mappingBackend.events.EventController;
import lombok.SneakyThrows;

import javax.ejb.EJB;
import javax.inject.Inject;

public class CamundaHazelListener implements MessageListener<JsonNode> {

    private final Logger logger = new Logger(this.getClass().getName());
    @EJB
    private EventController eventController;

    @SneakyThrows
    @Override
    public void onMessage(Message<JsonNode> message) {

        JsonNode json = message.getMessageObject();
        logger.logMessage("RECEIVED CAMUNDA DATA: ", ">", json.toPrettyString());

        var event = new EventDestructor(json);

        Events.CamundaSendTopic eventType = event.getCamundaSendEvent();
        JsonNode contentNode = event.getEventPayload();
        switch (eventType) {
            case RESPONSE_ALL_FORMS:
                eventController.updateAllForms(contentNode);
                break;
        }

    }
}

