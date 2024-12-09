package de.iisys.sysint.hicumes.manufacturingTerminalBackend.events;

import de.iisys.sysint.hicumes.core.entities.EntitySuperClass;
import de.iisys.sysint.hicumes.core.entities.MachineOccupation;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.EventEntity;
import lombok.Getter;

public class EventNewMachineOccupation extends EventEntity  {
    public boolean isSubMachineOccupation = false;
    public boolean sendSSEBroadcast = true;
    @Getter
    MachineOccupation machineOccupation;

    public EventNewMachineOccupation(MachineOccupation machineOccupation) {
        this.machineOccupation = machineOccupation;
    }

    public EventNewMachineOccupation(MachineOccupation machineOccupation, boolean isSubMachineOccupation) {
        this.machineOccupation = machineOccupation;
        this.isSubMachineOccupation = isSubMachineOccupation;
    }

    public EventNewMachineOccupation(MachineOccupation machineOccupation, boolean isSubMachineOccupation, boolean sendSSEBroadcast) {
        this.machineOccupation = machineOccupation;
        this.isSubMachineOccupation = isSubMachineOccupation;
        this.sendSSEBroadcast = sendSSEBroadcast;
    }

    public String toString() {
        return getClass().getSimpleName() + " with content: " + machineOccupation.getClass().getSimpleName();
    }
}
