package de.iisys.sysint.hicumes.manufacturingTerminalBackend.events;

import de.iisys.sysint.hicumes.manufacturingTerminalBackend.entities.CamundaMachineOccupation;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.EventEntity;
import lombok.Getter;

import java.util.List;

@Getter
public class EventCreateSplitOrder extends EventEntity {

    CamundaMachineOccupation camundaMachineOccupation;
    List<Double> splits;

    public EventCreateSplitOrder(CamundaMachineOccupation camundaMachineOccupation, List<Double> splits) {
        this.camundaMachineOccupation = camundaMachineOccupation;
        this.splits = splits;
    }

    public String toString() {
        return getClass().getSimpleName() + " with content: " + camundaMachineOccupation + " and " + splits;
    }
}
