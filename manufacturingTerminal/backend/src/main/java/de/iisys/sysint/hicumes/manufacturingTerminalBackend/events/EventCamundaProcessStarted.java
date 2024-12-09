package de.iisys.sysint.hicumes.manufacturingTerminalBackend.events;

import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.EventEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;

@Data
@Generated
@AllArgsConstructor
public class EventCamundaProcessStarted extends EventEntity {
    private String businessKey;
    private String processInstanceId;
    private String userName;

    public String toString() {
        return getClass().getSimpleName() + " with businessKey: " + businessKey + " and processInstanceId: " + processInstanceId + " from user " + userName;
    }
}
