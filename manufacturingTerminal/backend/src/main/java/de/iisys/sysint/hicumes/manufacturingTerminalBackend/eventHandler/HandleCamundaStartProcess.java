package de.iisys.sysint.hicumes.manufacturingTerminalBackend.eventHandler;

import com.google.common.eventbus.Subscribe;
import de.iisys.sysint.hicumes.core.entities.MachineOccupation;
import de.iisys.sysint.hicumes.core.entities.ProductionNumbers;
import de.iisys.sysint.hicumes.core.entities.UserOccupation;
import de.iisys.sysint.hicumes.core.events.Events;
import de.iisys.sysint.hicumes.core.utils.exceptions.UtilsBaseException;
import de.iisys.sysint.hicumes.core.utils.exceptions.database.DatabasePersistException;
import de.iisys.sysint.hicumes.core.utils.exceptions.database.DatabaseQueryException;
import de.iisys.sysint.hicumes.core.utils.hazelcast.EventGenerator;
import de.iisys.sysint.hicumes.core.utils.logger.Logger;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.entities.CamundaMachineOccupation;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBackgroundProcessingDone;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.EventResult;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.ISubscribeEvent;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventCamundaStartProcess;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.exceptions.bussines.BusinessException;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.manager.SSESessionManager;

import java.util.stream.Collectors;

public class HandleCamundaStartProcess implements ISubscribeEvent {

    private final Logger logger = new Logger(this.getClass().getName());
    @Subscribe
    public void handle(EventCamundaStartProcess eventCamundaStartProcess) throws UtilsBaseException, BusinessException {

        SSESessionManager sessionManager = SSESessionManager.getInstance();
        var camundaMachineOccupationId = eventCamundaStartProcess.getCamundaMachineOccupationId();
        var machineId = eventCamundaStartProcess.getMachineId();
        var machineOccupation = persistService.getCamundaMachineOccupationById(camundaMachineOccupationId);
        var machine = persistService.getMachinById(machineId);
        var userName = eventCamundaStartProcess.getUserName();

//        var toolSettings = machineOccupation.getMachineOccupation().getProductionSteps().stream().flatMap(p -> p.getToolSettingParameters().stream()).flatMap(t -> t.getToolSettings().stream())
//                .filter(toolSetting -> toolSetting.getMachine().getId() == machine.getId() && toolSetting.getTool().getId() == machineOccupation.getMachineOccupation().getTool().getId())
//                .collect(Collectors.toList());
        if (!machineOccupation.isAllowedToStartMachineOccupation()) {
            logger.logMessage("Tried to start a Process with an invalid state. State " + machineOccupation.getMachineOccupation().getStatus() + ". \n\tBusinessKey: " + machineOccupation.getBusinessKey());
            sessionManager.broadcast(new EventBackgroundProcessingDone(eventCamundaStartProcess, machineOccupation));
            return;
        }

        machineOccupation.getMachineOccupation().updateMachine(machine);
//        machineOccupation.getMachineOccupation().setActiveToolSettings(toolSettings);

        //Compatibility, if machineOccupation was already created with old hicumes version, there are no totalproductionnumbers defined
        if(machineOccupation.getMachineOccupation().getTotalProductionNumbers() == null)
        {
            machineOccupation.getMachineOccupation().setTotalProductionNumbers(new ProductionNumbers());
            var unitString = machineOccupation.getMachineOccupation().getProductionOrder().getMeasurement().getUnitString();
            machineOccupation.getMachineOccupation().getTotalProductionNumbers().getAcceptedMeasurement().setUnitString(unitString);
            machineOccupation.getMachineOccupation().getTotalProductionNumbers().getRejectedMeasurement().setUnitString(unitString);
            persistService.getPersistenceManager().persist(machineOccupation.getMachineOccupation().getTotalProductionNumbers());
            persistService.getPersistenceManager().merge(machineOccupation.getMachineOccupation());
        }

        if(machineOccupation.getMachineOccupation().getUserOccupation() == null)
        {
            var userOccupation = new UserOccupation(machineOccupation.getMachineOccupation());
            machineOccupation.getMachineOccupation().setUserOccupation(userOccupation);
            persistService.getPersistenceManager().persist(userOccupation);
            persistService.getPersistenceManager().merge(machineOccupation.getMachineOccupation());
        }

        persistService.getPersistenceManager().merge(machineOccupation);

        var subMachineOccupations = machineOccupation.getMachineOccupation().getSubMachineOccupations();
        if(subMachineOccupations.size() > 0)
        {
            //Avoid ConcurrentModificationException when iterating over subMachineOccupations
            var subMachineOccupationIds = subMachineOccupations.stream().map(MachineOccupation::getId).collect(Collectors.toList());
            subMachineOccupationIds.forEach(subMachineOccupationId ->
            {
                MachineOccupation subMachineOccupation = null;
                try {
                    subMachineOccupation = persistService.getMachineOccupationById(subMachineOccupationId);

                    subMachineOccupation.updateMachine(machine);
                    //machineOccupation.getMachineOccupation().setActiveToolSettings(toolSettings);

                    //Compatibility, if machineOccupation was already created with old hicumes version, there is no totalproductionnumbers defined
                    if (subMachineOccupation.getTotalProductionNumbers() == null) {
                        subMachineOccupation.setTotalProductionNumbers(new ProductionNumbers());
                        var unitString = subMachineOccupation.getProductionOrder().getMeasurement().getUnitString();
                        subMachineOccupation.getTotalProductionNumbers().getAcceptedMeasurement().setUnitString(unitString);
                        subMachineOccupation.getTotalProductionNumbers().getRejectedMeasurement().setUnitString(unitString);
                        persistService.getPersistenceManager().persist(subMachineOccupation.getTotalProductionNumbers());
                    }
                    if(subMachineOccupation.getUserOccupation() == null)
                    {
                        var userOccupation = new UserOccupation(subMachineOccupation);
                        subMachineOccupation.setUserOccupation(userOccupation);
                        persistService.getPersistenceManager().persist(userOccupation);
                    }
                    persistService.getPersistenceManager().merge(subMachineOccupation);
                } catch (DatabaseQueryException | DatabasePersistException e) {
                    e.printStackTrace();
                }
            });
        }

        eventCamundaStartProcess.executeCallback(new EventResult());
        var event = new EventGenerator().generateEvent(Events.CamundaReceiveTopic.START_PROCESS,  machineOccupation.toCamundaStartProcess(userName));
        hazelServer.sendEvent(event);
    }
}
