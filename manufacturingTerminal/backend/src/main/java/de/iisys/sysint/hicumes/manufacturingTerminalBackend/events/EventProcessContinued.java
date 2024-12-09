package de.iisys.sysint.hicumes.manufacturingTerminalBackend.events;

import de.iisys.sysint.hicumes.manufacturingTerminalBackend.entities.CamundaMachineOccupation;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.EventEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EventProcessContinued extends EventEntity {
    private String businessKey;
    private String processInstanceId;
    private String userName;
    private String formKey;

    public String toString() {
        return getClass().getSimpleName() + " with businessKey: " + businessKey + " and processInstanceId: " + processInstanceId + " from user " + userName;
    }
}
