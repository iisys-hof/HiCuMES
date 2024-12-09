package de.iisys.sysint.hicumes.manufacturingTerminalBackend.events;

import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.EventEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class EventResponseTaskWithFormFields extends EventEntity  {
    private String processInstanceId;
    private String businessKey;
    private String taskId;
    private String formField;
    private String userName;
    private String suspensionType;

    public EventResponseTaskWithFormFields(String processInstanceId, String businessKey, String taskId, String formField, String userName) {
        this.processInstanceId = processInstanceId;
        this.businessKey = businessKey;
        this.taskId = taskId;
        this.formField = formField;
        this.userName = userName;
    }

    public String toString() {
        return getClass().getSimpleName() + " and processInstanceId: " + processInstanceId + " and taskId: " + taskId + " and businessKey:" + businessKey + " from user " + userName;
    }
}
