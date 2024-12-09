package de.iisys.sysint.hicumes.manufacturingTerminalBackend.events;

import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.EventEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EventRequestActiveTask extends EventEntity {
    private String businessKey;
    private String userName;
//    private String processInstanceId;

    public String toString() {
        return getClass().getSimpleName() + " with businessKey: " + businessKey + " from user " + userName/*+ " and processInstanceId: " + processInstanceId*/;
    }
}
