package de.iisys.sysint.hicumes.manufacturingTerminalBackend.hazelcast;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.hazelcast.topic.Message;
import com.hazelcast.topic.MessageListener;
import de.iisys.sysint.hicumes.core.events.Events;
import de.iisys.sysint.hicumes.core.utils.hazelcast.EventDestructor;
import de.iisys.sysint.hicumes.core.utils.logger.Logger;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.*;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.IStaticDependencies;
import lombok.SneakyThrows;
import org.apache.commons.text.StringEscapeUtils;

import java.util.HashMap;


public class CamundaHazelListener implements MessageListener<JsonNode>, IStaticDependencies
{

    private final Logger logger = Logger.getInstance(this.getClass().getName(), this.getClass().getSimpleName());

    @SneakyThrows
    @Override
    public void onMessage(Message<JsonNode> message)
    {
        JsonNode json = message.getMessageObject();
        logger.logMessage("RECEIVED CAMUNDA DATA: ", ">", json.toPrettyString());

        var event = new EventDestructor(json);

        Events.CamundaSendTopic eventType = event.getCamundaSendEvent();
        JsonNode contentNode = event.getEventPayload();
        switch (eventType)
        {
            case RESPONSE_START_PROCESS:
            {
                var taskStarted = new EventCamundaProcessStarted(contentNode.get("businessKey").asText(), contentNode.get("processInstanceId").asText(), contentNode.get("userName").asText());
                events.emit(taskStarted);
                break;
            }
            case RESPONSE_ACTIVE_TASK:
                contentNode = contentNode.get(0);
                var eventUpdateCamundaTask = new EventResponseActiveTask(
                        contentNode.get("businessKey").asText(),
                        contentNode.get("processInstanceId").asText(),
                        contentNode.get("activeProcess").asText(),
                        contentNode.get("taskId").asText(),
                        contentNode.get("formKey").asText(),
                        contentNode.get("formFields").toString(),
                        contentNode.get("name").asText(),
                        contentNode.get("taskDefinitionKey").asText(),
                        contentNode.get("userName").asText(),
                        contentNode.get("processVariables").toString()
                );
                if(contentNode.get("suspensionType") != null)
                {
                    eventUpdateCamundaTask.setSuspensionType(contentNode.get("suspensionType").asText());
                }
                events.emit(eventUpdateCamundaTask);
                break;
            case RESPONSE_TASK_WITH_FORM_FIELDS:

                var eventResponseTaskWithFormFields = new EventResponseTaskWithFormFields(

                        contentNode.get("processInstanceId").asText(),
                        contentNode.get("businessKey").asText(),
                        contentNode.get("taskId").asText(),
                        contentNode.get("formFields").toString(),
                        contentNode.get("userName").asText()
                );
                if(contentNode.get("suspensionType") != null)
                {
                    eventResponseTaskWithFormFields.setSuspensionType(contentNode.get("suspensionType").asText());
                }
                events.emit(eventResponseTaskWithFormFields);
                break;

            case END_PROCESS:

                EventCamundaProcessEnd processEnd = null;
                if(contentNode.get("aborted") != null)
                {
                    processEnd = new EventCamundaProcessEnd(contentNode.get("businessKey").asText(), contentNode.get("id").asText(), contentNode.get("aborted").asBoolean());
                }
                else {
                    processEnd = new EventCamundaProcessEnd(contentNode.get("businessKey").asText(), contentNode.get("id").asText());
                }
                events.emit(processEnd);
                break;

            case RELEASE_PLANNED_PROCESS:

                EventCamundaReleasePlannedProcess eventReleasePlannedProcess = null;
                if(contentNode.get("toolExtId") != null)
                {
                    eventReleasePlannedProcess = new EventCamundaReleasePlannedProcess(contentNode.get("businessKey").asText(), contentNode.get("processInstanceId").asText(), contentNode.get("toolExtId").asText());
                }
                else
                {
                    eventReleasePlannedProcess = new EventCamundaReleasePlannedProcess(contentNode.get("businessKey").asText(), contentNode.get("processInstanceId").asText());
                }
                events.emit(eventReleasePlannedProcess);
                break;

            case AUTO_TIMER_FINISH:

                EventCamundaAutoTimerFinish eventCamundaAutoTimerFinish = null;
                if(contentNode.get("timerOffset") != null)
                {
                    eventCamundaAutoTimerFinish = new EventCamundaAutoTimerFinish(
                            contentNode.get("businessKey").asText(),
                            contentNode.get("processInstanceId").asText(),
                            contentNode.get("activeProcess").asText(),
                            contentNode.get("taskId").asText(),
                            contentNode.get("formKey").asText(),
                            contentNode.get("formFields").toString(),
                            contentNode.get("name").asText(),
                            contentNode.get("taskDefinitionKey").asText(),
                            contentNode.get("userName").asText(),
                            contentNode.get("timerOffset").asLong());
                }
                else
                {
                    eventCamundaAutoTimerFinish = new EventCamundaAutoTimerFinish(
                            contentNode.get("businessKey").asText(),
                            contentNode.get("processInstanceId").asText(),
                            contentNode.get("activeProcess").asText(),
                            contentNode.get("taskId").asText(),
                            contentNode.get("formKey").asText(),
                            contentNode.get("formFields").toString(),
                            contentNode.get("name").asText(),
                            contentNode.get("taskDefinitionKey").asText(),
                            contentNode.get("userName").asText());
                }
                events.emit(eventCamundaAutoTimerFinish);
                break;

            case SEND_BOOKINGS:

                var queryParameters = new HashMap<String, String>();
                if(contentNode.get("queryParameters") != null)
                {

                    JsonNode jsonQueryParams = mapper.readTree(contentNode.get("queryParameters").asText());
                    queryParameters = mapper.convertValue(jsonQueryParams, new TypeReference<HashMap<String, String>>() {});
                }
                var headerParameters = new HashMap<String, String>();
                if(contentNode.get("headerParameters") != null)
                {
                    JsonNode jsonHeaderParams = mapper.readTree(contentNode.get("headerParameters").asText());
                    headerParameters = mapper.convertValue(jsonHeaderParams, new TypeReference<HashMap<String, String>>() {});
                }
                var eventSendBookingREST = new EventSendBookingsREST(
                        queryParameters,
                        headerParameters,
                        contentNode.get("requestType").asText(),
                        contentNode.get("requestAddress").asText()
                    );

                events.emit(eventSendBookingREST);
                break;

        }
    }
}

