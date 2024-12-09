package de.iisys.sysint.hicumes.business.handler;

import com.fasterxml.jackson.databind.JsonNode;
import de.iisys.sysint.hicumes.business.handler.mapper.FormFieldMapper;
import de.iisys.sysint.hicumes.core.events.Events;
import de.iisys.sysint.hicumes.core.utils.hazelcast.EventGenerator;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.task.Task;

public class RequestAllTasksHandler extends DefaultHandler implements HazelcastHandler {
    public RequestAllTasksHandler(ProcessEngine processEngine) {
        super(processEngine);
    }

    @Override
    public JsonNode handleEvent(JsonNode contentNode) {

        logger.logMessage("requestAllTasks");

        var businessKey = contentNode.get("businessKey").asText();

        var taskList = processEngine.getTaskService().createTaskQuery().processDefinitionId("Process_Schneiden").list();
        var taskListArrayNode = mapper.createArrayNode();
        for (Task task : taskList) {
            var taskFormData = processEngine.getFormService().getTaskFormData(task.getId());
            var taskFormFields = taskFormData.getFormFields();
            var taskFormFieldsArrayNode = new FormFieldMapper().encodeFields(taskFormFields);
            var activeTask = mapper.createObjectNode();
            activeTask.put("taskId", task.getId());
            activeTask.put("processInstanceId", task.getProcessInstanceId());
            activeTask.put("processDefinitionId", task.getProcessDefinitionId());
            activeTask.put("businessKey", businessKey);
            activeTask.put("name", task.getName());
            activeTask.putArray("formFields").addAll(taskFormFieldsArrayNode);
            taskListArrayNode.add(activeTask);
        }
        System.out.println(taskListArrayNode.toPrettyString());
        var newEvent = new EventGenerator().generateEvent(Events.CamundaSendTopic.RESPONSE_ALL_TASKS, taskListArrayNode);
        return newEvent;
    }
}
