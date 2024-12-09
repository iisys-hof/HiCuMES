package de.iisys.sysint.hicumes.manufacturingTerminalBackend.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.EventEntity;
import lombok.Data;
import lombok.Generated;

@Generated
@Data
public class EventCamundaPersistFormField extends EventEntity {
    private long parentCamundaMOId;
    private long subMachineOccupationId;
    private ObjectNode formFields;
    private String userName;

    public EventCamundaPersistFormField(long parentCamundaMOId, long subMachineOccupationId, ObjectNode formFields, String userName) {
        this.parentCamundaMOId = parentCamundaMOId;
        this.subMachineOccupationId = subMachineOccupationId;
        this.formFields = formFields;
        if(formFields == null)
        {
            this.formFields = new ObjectMapper().createObjectNode();
        }
        this.userName = userName;
    }


    public String toString() {
        return getClass().getSimpleName() + " with Parent CMO ID: " + parentCamundaMOId + " and sub MachineOccupation: " + subMachineOccupationId + " and FormField results " + formFields + " from user " + userName ;
    }
}
