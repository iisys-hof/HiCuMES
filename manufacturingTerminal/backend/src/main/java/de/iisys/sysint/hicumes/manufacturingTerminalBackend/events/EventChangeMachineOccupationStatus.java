package de.iisys.sysint.hicumes.manufacturingTerminalBackend.events;

import de.iisys.sysint.hicumes.core.entities.enums.EMachineOccupationStatus;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.EventEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class EventChangeMachineOccupationStatus extends EventEntity  {
    private int camundaMachineOccupationId;
    private EMachineOccupationStatus status;
    private String userName;

    public EventChangeMachineOccupationStatus(int camundaMachineOccupationId, String status, String userName) {
        this.camundaMachineOccupationId = camundaMachineOccupationId;
        this.status = EMachineOccupationStatus.valueOf(status);
        this.userName = userName;
    }

    public String toString() {
        return getClass().getSimpleName() + " with camundaMachineOccupationId: " + camundaMachineOccupationId + " and status " + status + " from user " + userName;
    }
}
