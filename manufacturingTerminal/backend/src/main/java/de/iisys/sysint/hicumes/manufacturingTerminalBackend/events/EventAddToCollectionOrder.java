package de.iisys.sysint.hicumes.manufacturingTerminalBackend.events;

import de.iisys.sysint.hicumes.manufacturingTerminalBackend.entities.CamundaMachineOccupation;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.EventEntity;
import lombok.Data;
import lombok.Getter;

import java.util.List;
@Data
public class EventAddToCollectionOrder extends EventEntity {

    @Getter
    CamundaMachineOccupation collectionOrder;
    @Getter
    CamundaMachineOccupation subMachineOccupation;


    public EventAddToCollectionOrder(CamundaMachineOccupation collectionOrder, CamundaMachineOccupation subMachineOccupation) {
        this.collectionOrder = collectionOrder;
        this.subMachineOccupation = subMachineOccupation;
    }

    public String toString() {
        return getClass().getSimpleName() + " with content: " + collectionOrder.getExternalId() + " and " + subMachineOccupation.getExternalId();
    }
}
