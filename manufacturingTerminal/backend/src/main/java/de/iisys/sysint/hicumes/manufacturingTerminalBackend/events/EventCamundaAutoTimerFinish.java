package de.iisys.sysint.hicumes.manufacturingTerminalBackend.events;

import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.EventEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class EventCamundaAutoTimerFinish extends EventEntity  {
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
    private Long timeOffsetSeconds = 0L;

    public EventCamundaAutoTimerFinish(String businessKey, String processInstanceId, String activeProcess, String taskId, String formKey, String formField, String name, String taskDefinitionKey, String userName, Long timeOffsetSeconds) {
        this.businessKey = businessKey;
        this.processInstanceId = processInstanceId;
        this.activeProcess = activeProcess;
        this.taskId = taskId;
        this.formKey = formKey;
        this.formField = formField;
        this.name = name;
        this.taskDefinitionKey = taskDefinitionKey;
        this.userName = userName;
        this.timeOffsetSeconds = timeOffsetSeconds;
    }

    public EventCamundaAutoTimerFinish(String businessKey, String processInstanceId, String activeProcess, String taskId, String formKey, String formField, String name, String taskDefinitionKey, String userName) {
        this.businessKey = businessKey;
        this.processInstanceId = processInstanceId;
        this.activeProcess = activeProcess;
        this.taskId = taskId;
        this.formKey = formKey;
        this.formField = formField;
        this.name = name;
        this.taskDefinitionKey = taskDefinitionKey;
        this.userName = userName;
    }

    public String toString() {
        return getClass().getSimpleName() + " with businessKey: " + businessKey + " and processInstanceId: " + processInstanceId;
    }
}
