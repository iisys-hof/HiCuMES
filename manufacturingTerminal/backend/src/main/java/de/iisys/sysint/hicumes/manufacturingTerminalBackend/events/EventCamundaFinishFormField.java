package de.iisys.sysint.hicumes.manufacturingTerminalBackend.events;

import com.fasterxml.jackson.databind.node.ObjectNode;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.EventEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;

@AllArgsConstructor
@Generated
@Data
public class EventCamundaFinishFormField extends EventEntity {
    private String taskId;
    private int camundaProductionOrderId;
    private ObjectNode formFields;
    private String userName;
    private String suspensionType;
    private boolean sendToCamunda = true;

    public EventCamundaFinishFormField(String taskId, int camundaProductionOrderId, ObjectNode formFields, String userName) {
        this.taskId = taskId;
        this.camundaProductionOrderId = camundaProductionOrderId;
        this.formFields = formFields;
        this.userName = userName;
    }

    public EventCamundaFinishFormField(String taskId, int camundaProductionOrderId, ObjectNode formFields, String userName, boolean sendToCamunda) {
        this.taskId = taskId;
        this.camundaProductionOrderId = camundaProductionOrderId;
        this.formFields = formFields;
        this.userName = userName;
        this.sendToCamunda = sendToCamunda;
    }

    public String toString() {
        return getClass().getSimpleName() + " with camundaProductionOrderId: " + camundaProductionOrderId + " and FormField results " + formFields.toString() + " and taskId " + taskId + " from user " + userName ;
    }
}
