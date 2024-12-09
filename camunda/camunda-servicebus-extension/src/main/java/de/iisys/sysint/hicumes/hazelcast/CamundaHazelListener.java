package de.iisys.sysint.hicumes.hazelcast;

import com.fasterxml.jackson.databind.JsonNode;
import com.hazelcast.topic.Message;
import com.hazelcast.topic.MessageListener;
import de.iisys.sysint.hicumes.EventController;
import de.iisys.sysint.hicumes.core.events.Events;
import de.iisys.sysint.hicumes.core.utils.hazelcast.EventDestructor;
import de.iisys.sysint.hicumes.core.utils.logger.Logger;

public class CamundaHazelListener implements MessageListener<JsonNode> {
    private final Logger logger = new Logger(this.getClass().getName());

    EventController eventController;

    public CamundaHazelListener(EventController eventController) {
        this.eventController = eventController;
    }

    @Override
    public void onMessage(Message<JsonNode> message) {
        JsonNode json = message.getMessageObject();
        logger.logMessage("RECEIVED DATA: ", ">", json.toPrettyString());

        var event = new EventDestructor(json);

        Events.CamundaReceiveTopic eventType = event.getCamundaReceiveEvent();
        JsonNode contentNode = event.getEventPayload();
        switch (eventType) {
            case UNDEPLOY_ALL_PROCESSES:
                eventController.eventUndeployAllBpmnProcesses(contentNode);
                break;
            case DEPLOY_PROCESSES:
                eventController.eventDeployBpmnProcess(contentNode);
                break;
            case REQUEST_ALL_DEPLOYED_PROCESSES:
                eventController.eventRequestAllDeployedProcesse(contentNode);
                break;
            case START_PROCESS:
                eventController.evenStartProcess(contentNode);
                break;
            case END_PROCESS:
                eventController.eventManualEndProcess(contentNode);
                break;
            case REQUEST_RUNNING_PROCESSES:
                eventController.eventRequestRunningProcesses(contentNode);
                break;
            case FINISH_WITH_FORMFIELDS:
                eventController.eventFinishWithFormFields(contentNode);
                break;
            case REQUEST_ACTIVE_TASK:
                eventController.eventRequestActiveTask(contentNode);
                break;
            case REQUEST_ALL_TASKS:
                eventController.eventRequestAllTasks(contentNode);
                break;
            case REQUEST_ALL_FORMS:
                eventController.eventRequestAllForms(contentNode);
                break;
            default:
                logger.logMessage("Get an unhandled event from Camunda:", "-", json.toPrettyString());
        }
    }
}

