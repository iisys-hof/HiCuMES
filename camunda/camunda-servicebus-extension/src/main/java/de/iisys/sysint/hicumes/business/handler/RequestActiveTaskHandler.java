package de.iisys.sysint.hicumes.business.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import de.iisys.sysint.hicumes.business.handler.mapper.FormFieldMapper;
import de.iisys.sysint.hicumes.core.events.Events;
import de.iisys.sysint.hicumes.core.utils.hazelcast.EventGenerator;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.impl.persistence.entity.ExecutionEntity;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.SubProcess;
import org.camunda.bpm.model.xml.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RequestActiveTaskHandler extends DefaultHandler implements HazelcastHandler {
    public RequestActiveTaskHandler(ProcessEngine processEngine) {
        super(processEngine);
    }

    @Override
    public JsonNode handleEvent(JsonNode contentNode) {
        logger.logMessage("requestActiveTask");
        var businessKey = contentNode.get("businessKey").asText();
        String processInstanceId = contentNode.get("processInstanceId").asText();

        //Update process variables
        if(contentNode.get("processInstanceId") != null) {
            var variables = mapper.convertValue(contentNode.get("variables"), Map.class);
            var executions = processEngine.getRuntimeService().createExecutionQuery().processInstanceId(processInstanceId).list();
            executions.forEach(execution -> {
                processEngine.getRuntimeService().setVariables(execution.getId(), variables);
            });
        }

        var executions = processEngine.getRuntimeService().createExecutionQuery().processInstanceId(processInstanceId).list();
        var processVars = new HashMap<>();
        executions.forEach(execution -> {
            processVars.putAll(processEngine.getRuntimeService().getVariablesLocal(execution.getId()));
        });
        processVars.remove("machineOccupation");
        var processVarsNode = mapper.valueToTree(processVars);
        //var activeTasksList = processEngine.getTaskService().createTaskQuery().active().processInstanceBusinessKey(businessKey).initializeFormKeys().list();
        var activeTasksList = processEngine.getTaskService().createTaskQuery().active().processInstanceId(processInstanceId).initializeFormKeys().list();
        System.out .println(processVars);
        var activeTasksListArrayNode = mapper.createArrayNode();
        for (Task task : activeTasksList) {
            var taskFormData = processEngine.getFormService().getTaskFormData(task.getId());
            var taskFormFields = taskFormData.getFormFields();
            var taskFormFieldsArrayNode = new FormFieldMapper().encodeFields(taskFormFields);
            var activeTask = mapper.createObjectNode();
            BpmnModelInstance modelInstance = processEngine.getRepositoryService().getBpmnModelInstance(task.getProcessDefinitionId());
            var subProcessId = modelInstance.getModelElementById(task.getTaskDefinitionKey()).getParentElement().getAttributeValue("id");


            activeTask.put("taskId", task.getId());
            activeTask.put("processInstanceId", task.getProcessInstanceId());
            activeTask.put("processDefinitionId", task.getProcessDefinitionId());
            activeTask.put("activeProcess", subProcessId);
            activeTask.put("businessKey", businessKey);
            activeTask.put("name", task.getName());
            activeTask.put("formKey", task.getFormKey());
            activeTask.putArray("formFields").addAll(taskFormFieldsArrayNode);
            activeTask.set("processVariables", processVarsNode);

            activeTask.put("taskDefinitionKey", task.getTaskDefinitionKey());
            activeTask.put("tenantId", task.getTenantId());
            if(contentNode.get("userName") != null)
            {
                String userName =contentNode.get("userName").asText();
                activeTask.put("userName", userName);
            }

            if(contentNode.get("suspensionType") != null) {
                var suspensionType = contentNode.get("suspensionType").asText();
                activeTask.put("suspensionType", suspensionType);
            }

            activeTasksListArrayNode.add(activeTask);
        }
        var newEvent = new EventGenerator().generateEvent(Events.CamundaSendTopic.RESPONSE_ACTIVE_TASK, activeTasksListArrayNode);
        return newEvent;
    }
}
