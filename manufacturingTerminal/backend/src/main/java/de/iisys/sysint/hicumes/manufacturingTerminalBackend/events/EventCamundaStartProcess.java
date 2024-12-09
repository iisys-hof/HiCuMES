package de.iisys.sysint.hicumes.manufacturingTerminalBackend.events;

import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.EventEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class EventCamundaStartProcess extends EventEntity  {
    private int camundaMachineOccupationId;
    private int machineId;
    private String userName;

    public String toString() {
        return getClass().getSimpleName() + " with camundaMachineOccupationId: " + camundaMachineOccupationId + " on machine: " + machineId + " from user " + userName;
    }
}
