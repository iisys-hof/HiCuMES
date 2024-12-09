package de.iisys.sysint.hicumes.manufacturingTerminalBackend.eventHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.eventbus.Subscribe;
import de.iisys.sysint.hicumes.core.entities.MachineOccupation;
import de.iisys.sysint.hicumes.core.utils.exceptions.UtilsBaseException;
import de.iisys.sysint.hicumes.core.utils.exceptions.database.DatabaseQueryException;
import de.iisys.sysint.hicumes.core.utils.hazelcast.HazelServer;
import de.iisys.sysint.hicumes.core.utils.logger.Logger;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBackgroundProcessingDone;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.EventQueue;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.ISubscribeEvent;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventCamundaProcessEnd;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.manager.PersistService;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.manager.SSESessionManager;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.startup.StartupController;

import javax.inject.Inject;
import java.util.stream.Collectors;

public class HandleCamundaProcessEnd implements ISubscribeEvent {

    private final Logger logger = new Logger(this.getClass().getName());
    @Subscribe
    public void handle(EventCamundaProcessEnd eventCamundaProcessEnd) throws UtilsBaseException {

        var businessKey = eventCamundaProcessEnd.getBusinessKey();

        if(businessKey == null || businessKey.equals("null"))
        {
            return;
        }

        try
        {
            var machineOccupation = persistService.getCamundaMachineOccupationByBusinessKey(businessKey);

            var subMachineOccupations = machineOccupation.getMachineOccupation().getSubMachineOccupations();
            /*if(subMachineOccupations.size() > 0)
            {
                //Avoid ConcurrentModificationException when iterating over subMachineOccupations
                var subMachineOccupationIds = subMachineOccupations.stream().map(MachineOccupation::getId).collect(Collectors.toList());
                subMachineOccupationIds.forEach(subMachineOccupationId ->
                {
                    try {
                        var subCamundaMachineOccupation = persistService.getCamundaMachineOccupationByMachineOccupationId(subMachineOccupationId);

                        subCamundaMachineOccupation.endProcess();
                        persistService.getPersistenceManager().merge(subCamundaMachineOccupation);

                        SSESessionManager sessionManager = SSESessionManager.getInstance();
                        //sessionManager.sendEvent(Long.toString(machineOccupation.getId()), eventResponseActiveTask);
                        sessionManager.broadcast(new EventBackgroundProcessingDone(eventCamundaProcessEnd, subCamundaMachineOccupation));
                    } catch (DatabaseQueryException e) {
                        e.printStackTrace();
                    }
                });
            }*/

            machineOccupation.endProcess(eventCamundaProcessEnd.isAborted());
            persistService.getPersistenceManager().merge(machineOccupation);

            SSESessionManager sessionManager = SSESessionManager.getInstance();
            //sessionManager.sendEvent(Long.toString(machineOccupation.getId()), eventResponseActiveTask);
            sessionManager.broadcast(new EventBackgroundProcessingDone(eventCamundaProcessEnd, machineOccupation));
        }
        catch (DatabaseQueryException e)
        {
            logger.logMessage("Entry with businessKey " + businessKey + " does not exist in table CamundaMachineOccupation");
        }
    }
}
