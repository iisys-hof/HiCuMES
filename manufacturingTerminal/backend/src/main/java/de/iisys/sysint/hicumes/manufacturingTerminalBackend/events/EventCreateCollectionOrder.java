package de.iisys.sysint.hicumes.manufacturingTerminalBackend.events;

import de.iisys.sysint.hicumes.manufacturingTerminalBackend.entities.CamundaMachineOccupation;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.EventEntity;
import lombok.Getter;

import java.util.List;
import java.util.Map;

public class EventCreateCollectionOrder extends EventEntity {

    @Getter
    CamundaMachineOccupation collectionOrder;
    @Getter
    Map<CamundaMachineOccupation, Double> subMachineOccupationAmount;

    public EventCreateCollectionOrder(CamundaMachineOccupation collectionOrder, Map<CamundaMachineOccupation, Double> subMachineOccupationAmount) {
        this.collectionOrder = collectionOrder;
        this.subMachineOccupationAmount = subMachineOccupationAmount;
    }

    public String toString() {
        return getClass().getSimpleName() + " with content: " + collectionOrder + " " + subMachineOccupationAmount;
    }
}
