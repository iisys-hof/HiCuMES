package de.iisys.sysint.hicumes.business.handler;

import com.fasterxml.jackson.databind.JsonNode;
import de.iisys.sysint.hicumes.business.handler.mapper.FormFieldMapper;
import de.iisys.sysint.hicumes.core.events.Events;
import de.iisys.sysint.hicumes.core.utils.hazelcast.EventGenerator;
import org.camunda.bpm.engine.ProcessEngine;

public class GetCurrentTaskFormFieldsHandler extends DefaultHandler implements HazelcastHandler {


    public GetCurrentTaskFormFieldsHandler(ProcessEngine processEngine) {
        super(processEngine);
    }

    @Override
    public JsonNode handleEvent(JsonNode contentNode) {
        logger.logMessage("currentTaskFormFields");
        var taskId = contentNode.get("taskId").asText();
        var camundaFormKey = contentNode.get("camundaFormKey").asText();
        var processInstanceId = contentNode.get("processInstanceId").asText();
        var businessKey = contentNode.get("businessKey").asText();


        var taskFormData = processEngine.getFormService().getTaskFormData(taskId);
        var formFields = taskFormData.getFormFields();

        var fieldsArray = new FormFieldMapper().encodeFields(formFields);
        var taskResponse = mapper.createObjectNode();

        taskResponse.putArray("formFields").addAll(fieldsArray);
        taskResponse.put("camundaFormKey", camundaFormKey);
        taskResponse.put("processInstanceId", processInstanceId);
        taskResponse.put("taskId", taskId);
        taskResponse.put("businessKey", businessKey);

        if(contentNode.get("userName") != null) {
            var userName = contentNode.get("userName").asText();
            taskResponse.put("userName", userName);
        }

        if(contentNode.get("suspensionType") != null) {
            var suspensionType = contentNode.get("suspensionType").asText();
            taskResponse.put("suspensionType", suspensionType);
        }

        var newEvent = new EventGenerator().generateEvent(Events.CamundaSendTopic.RESPONSE_TASK_WITH_FORM_FIELDS, taskResponse);
        return newEvent;
    }
}
