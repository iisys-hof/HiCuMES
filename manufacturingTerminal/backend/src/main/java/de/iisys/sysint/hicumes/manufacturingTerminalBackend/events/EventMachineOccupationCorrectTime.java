package de.iisys.sysint.hicumes.manufacturingTerminalBackend.events;

import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.EventEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EventMachineOccupationCorrectTime extends EventEntity {
    private String businessKey;
    private String userName;
    private Long durationSeconds;

    public String toString() {
        return getClass().getSimpleName() + " with businessKey: " + businessKey + " from user " + userName + " with " + durationSeconds;
    }
}
