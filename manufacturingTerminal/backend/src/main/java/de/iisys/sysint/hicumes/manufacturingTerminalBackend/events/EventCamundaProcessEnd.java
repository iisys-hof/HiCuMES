package de.iisys.sysint.hicumes.manufacturingTerminalBackend.events;

import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.EventEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EventCamundaProcessEnd extends EventEntity {
    private String businessKey;
    private String processInstanceId;
    private boolean aborted = false;

    public EventCamundaProcessEnd(String businessKey, String id) {
        this.businessKey = businessKey;
        this.processInstanceId = id;
    }

    public String toString() {
        return getClass().getSimpleName() + " with businessKey: " + businessKey + " and processInstanceId: " + processInstanceId + " aborted?: " + aborted;
    }
}
