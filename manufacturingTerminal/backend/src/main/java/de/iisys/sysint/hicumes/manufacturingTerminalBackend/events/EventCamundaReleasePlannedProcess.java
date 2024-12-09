package de.iisys.sysint.hicumes.manufacturingTerminalBackend.events;

import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.EventEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class EventCamundaReleasePlannedProcess extends EventEntity  {
    private String businessKey;
    private String processInstanceId;
    private String toolExtId = null;

    public EventCamundaReleasePlannedProcess(String businessKey, String processInstanceId) {
        this.businessKey = businessKey;
        this.processInstanceId = processInstanceId;
    }

    public String toString() {
        return getClass().getSimpleName() + " with businessKey: " + businessKey + " and processInstanceId: " + processInstanceId;
    }
}
