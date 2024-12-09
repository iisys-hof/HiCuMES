package de.iisys.sysint.hicumes.manufacturingTerminalBackend.eventHandler;

import com.google.common.eventbus.Subscribe;
import de.iisys.sysint.hicumes.core.entities.MachineOccupation;
import de.iisys.sysint.hicumes.core.entities.enums.EMachineOccupationStatus;
import de.iisys.sysint.hicumes.core.utils.exceptions.database.DatabasePersistException;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.entities.CamundaMachineOccupation;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventAddToCollectionOrder;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBackgroundProcessingDone;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.IEmitEvent;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.ISubscribeEvent;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventCreateCollectionOrder;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventNewMachineOccupation;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.manager.SSESessionManager;

import javax.inject.Inject;
import java.util.Random;
import java.util.stream.Collectors;

public class HandleAddToCollectionOrder implements ISubscribeEvent {

    @Inject
    private IEmitEvent events;
    @Subscribe
    public void handle(EventAddToCollectionOrder eventAddToCollectionOrder) throws DatabasePersistException {

        var collectionOrder = eventAddToCollectionOrder.getCollectionOrder();
        var subMachineOccupation = eventAddToCollectionOrder.getSubMachineOccupation();

        collectionOrder.getMachineOccupation().getSubMachineOccupations().add(subMachineOccupation.getMachineOccupation());

        SSESessionManager sessionManager = SSESessionManager.getInstance();

        subMachineOccupation.setSubMachineOccupation(true);
        persistService.getPersistenceManager().persist(subMachineOccupation);

        sessionManager.broadcast(new EventBackgroundProcessingDone(eventAddToCollectionOrder, collectionOrder));

        persistService.getPersistenceManager().persist(collectionOrder);

    }

}
