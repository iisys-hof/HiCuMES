package de.iisys.sysint.hicumes.manufacturingTerminalBackend.events;

import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.EventEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EventArchiveMachineOccupations extends EventEntity {
    private int finishedXDaysAgo;

    public String toString() {
        return getClass().getSimpleName() + " that finished x days ago: " + finishedXDaysAgo;
    }
}
