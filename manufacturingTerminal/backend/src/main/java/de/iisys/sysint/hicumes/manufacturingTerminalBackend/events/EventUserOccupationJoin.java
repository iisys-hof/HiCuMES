package de.iisys.sysint.hicumes.manufacturingTerminalBackend.events;

import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.EventEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EventUserOccupationJoin extends EventEntity {

    private String userName;
    private int cmoId;

    public String toString() {
        return getClass().getSimpleName() + " with cmoId: " + cmoId + " from user " + userName;
    }
}
