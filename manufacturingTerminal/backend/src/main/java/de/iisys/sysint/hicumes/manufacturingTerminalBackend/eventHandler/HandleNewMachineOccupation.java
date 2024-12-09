package de.iisys.sysint.hicumes.manufacturingTerminalBackend.eventHandler;

import com.google.common.eventbus.Subscribe;
import de.iisys.sysint.hicumes.core.entities.EntitySuperClass;
import de.iisys.sysint.hicumes.core.entities.MachineOccupation;
import de.iisys.sysint.hicumes.core.utils.exceptions.database.DatabasePersistException;
import de.iisys.sysint.hicumes.core.utils.exceptions.database.DatabaseQueryException;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.entities.CamundaMachineOccupation;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBackgroundProcessingDone;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.IEmitEvent;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.ISubscribeEvent;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventNewEntity;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventNewMachineOccupation;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.manager.SSESessionManager;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ejb.Singleton;
import java.util.Random;

public class HandleNewMachineOccupation implements ISubscribeEvent {

    @Inject
    private IEmitEvent events;
    @Subscribe
    public void handle(EventNewMachineOccupation eventNewMachineOccupation) throws DatabasePersistException, DatabaseQueryException {
        var machineOccupation = eventNewMachineOccupation.getMachineOccupation();
        // machineOccupation = persistService.getPersistenceManager().getById(MachineOccupation.class,machineOccupation.getId());
        var businessKey = machineOccupation.getCamundaProcessName() + "#" + machineOccupation.getExternalId() + "#" + new Random().nextInt();
        var camundaMachineOccupation = new CamundaMachineOccupation(machineOccupation, businessKey);
        if(eventNewMachineOccupation.isSubMachineOccupation)
        {
            camundaMachineOccupation.setSubMachineOccupation(true);
        }
        persistService.getPersistenceManager().persist(camundaMachineOccupation);

        if(eventNewMachineOccupation.sendSSEBroadcast)
        {
            SSESessionManager sessionManager = SSESessionManager.getInstance();
            sessionManager.broadcast(new EventBackgroundProcessingDone(eventNewMachineOccupation, camundaMachineOccupation));
        }
    }
}
