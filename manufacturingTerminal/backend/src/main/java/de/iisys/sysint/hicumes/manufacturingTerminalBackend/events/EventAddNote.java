package de.iisys.sysint.hicumes.manufacturingTerminalBackend.events;

import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.EventEntity;
import lombok.Data;

@Data
public class EventAddNote extends EventEntity {

    int machineOccupationId;
    String noteString;
    String userName;

    public EventAddNote(int machineOccupationId, String noteString, String userName) {
        this.machineOccupationId = machineOccupationId;
        this.noteString = noteString;
        this.userName = userName;
    }

    public String toString() {
        return getClass().getSimpleName() + " with content: " + machineOccupationId + " and " + noteString + " and " + userName;
    }

}
