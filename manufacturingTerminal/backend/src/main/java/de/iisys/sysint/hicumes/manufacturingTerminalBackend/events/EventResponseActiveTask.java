package de.iisys.sysint.hicumes.manufacturingTerminalBackend.events;

import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.EventEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class EventResponseActiveTask extends EventEntity  {
    private String businessKey;
    private String processInstanceId;
    private String activeProcess;
    private String taskId;
    private String formKey;
    private String formField;
    private String name;
    private String taskDefinitionKey;
    private String userName;
    private String suspensionType;
    private String processVariables;

    public EventResponseActiveTask(String businessKey, String processInstanceId, String activeProcess, String taskId, String formKey, String formField, String name, String taskDefinitionKey, String userName, String processVariables) {
        this.businessKey = businessKey;
        this.processInstanceId = processInstanceId;
        this.activeProcess = activeProcess;
        this.taskId = taskId;
        this.formKey = formKey;
        this.formField = formField;
        this.name = name;
        this.taskDefinitionKey = taskDefinitionKey;
        this.userName = userName;
        this.processVariables = processVariables;
    }

    public String toString() {
        return getClass().getSimpleName() + " with businessKey: " + businessKey + " and processInstanceId: " + processInstanceId + " and activeProcess: " + activeProcess + " and taskId: " + taskId + " and taskDefinitionKey: " + taskDefinitionKey + " from user " + userName;
    }
}
