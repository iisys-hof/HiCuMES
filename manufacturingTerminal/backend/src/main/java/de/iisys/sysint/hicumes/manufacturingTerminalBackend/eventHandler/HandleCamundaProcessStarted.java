package de.iisys.sysint.hicumes.manufacturingTerminalBackend.eventHandler;

import com.google.common.collect.Iterables;
import com.google.common.eventbus.Subscribe;
import de.iisys.sysint.hicumes.core.entities.CD_ProductionStep;
import de.iisys.sysint.hicumes.core.entities.MachineOccupation;
import de.iisys.sysint.hicumes.core.entities.enums.EMachineOccupationStatus;
import de.iisys.sysint.hicumes.core.entities.enums.EProductionStepType;
import de.iisys.sysint.hicumes.core.events.Events;
import de.iisys.sysint.hicumes.core.utils.exceptions.UtilsBaseException;
import de.iisys.sysint.hicumes.core.utils.exceptions.database.DatabaseQueryException;
import de.iisys.sysint.hicumes.core.utils.hazelcast.EventGenerator;
import de.iisys.sysint.hicumes.core.utils.logger.Logger;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.entities.CamundaMachineOccupation;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBackgroundProcessingDone;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.EventResult;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.ISubscribeEvent;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventCamundaProcessStarted;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventCamundaReleasePlannedProcess;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.exceptions.bussines.BusinessException;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.manager.SSESessionManager;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.utils.MachineOccupationStatusChanger;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HandleCamundaProcessStarted implements ISubscribeEvent {

    private final Logger logger = new Logger(this.getClass().getName());
    @Subscribe
    public void handle(EventCamundaProcessStarted eventCamundaProcessStarted) throws UtilsBaseException {

        SSESessionManager sessionManager = SSESessionManager.getInstance();
        var businessKey = eventCamundaProcessStarted.getBusinessKey();
        var processInstanceId = eventCamundaProcessStarted.getProcessInstanceId();
        var userName = eventCamundaProcessStarted.getUserName();

        if(businessKey == null || businessKey.equals("null"))
        {
            return;
        }

        try {
            var camundaMachineOccupation = persistService.getCamundaMachineOccupationByBusinessKey(businessKey);
            //System.out.println("camundaMachineOccupation");
            //System.out.println(camundaMachineOccupation);
            //System.out.println(camundaMachineOccupation.getMachineOccupation().getStatus());

            try {
                camundaMachineOccupation.startMachineOccupation(processInstanceId);
            }
            catch (BusinessException e)
            {
                logger.logMessage("MachineOccupation with businessKey " + businessKey + " is not in state READY_TO_START, sending sse broadcast");
                sessionManager.broadcast(new EventBackgroundProcessingDone(eventCamundaProcessStarted, camundaMachineOccupation));
                return;
            }

            //System.out.println("nach dem Start");
            //System.out.println(camundaMachineOccupation.getMachineOccupation().getStatus());

            persistService.getPersistenceManager().merge(camundaMachineOccupation);
            //System.out.println("nach dem merge");
            //System.out.println(camundaMachineOccupation.getMachineOccupation().getStatus());
            camundaMachineOccupation = persistService.getCamundaMachineOccupationByBusinessKey(businessKey);
            //System.out.println("nach dem neuldaden");
            //System.out.println(camundaMachineOccupation.getMachineOccupation().getStatus());

            //Wenn der aktuell AG eine ProductionStepInfo hat, muss geprüft werden, ob es noch andere AGs gibt, die deaktiviert werden müssen
            if (camundaMachineOccupation.getActiveProductionStep().getProductionStepInfo() != null && camundaMachineOccupation.getActiveProductionStep().getProductionStepInfo().getStepType().equals(EProductionStepType.ALTERNATIVE)) {
                var alternativeMachineOccupations = new ArrayList<CamundaMachineOccupation>();
                if (camundaMachineOccupation.getMachineOccupation().getSubMachineOccupations() != null && camundaMachineOccupation.getMachineOccupation().getSubMachineOccupations().size() > 0) {
                    //Sammelauftrag
                    for (MachineOccupation machineOccupation : camundaMachineOccupation.getMachineOccupation().getSubMachineOccupations()) {
                        alternativeMachineOccupations.addAll(MachineOccupationStatusChanger.stopOtherAlternativeProcess(persistService, machineOccupation, camundaMachineOccupation.getActiveProductionStep().getProductionStepInfo()));
                    }
                } else {
                    alternativeMachineOccupations.addAll(MachineOccupationStatusChanger.stopOtherAlternativeProcess(persistService, camundaMachineOccupation.getMachineOccupation(), camundaMachineOccupation.getActiveProductionStep().getProductionStepInfo()));
                }

                for (CamundaMachineOccupation machineOccupation : alternativeMachineOccupations) {
                    sessionManager.broadcast(new EventBackgroundProcessingDone(eventCamundaProcessStarted, machineOccupation));
                }
            }

            var event = new EventGenerator().generateEvent(Events.CamundaReceiveTopic.REQUEST_ACTIVE_TASK, camundaMachineOccupation.toRequestActiveTask(userName, null));
            hazelServer.sendEvent(event);
        }
        catch (DatabaseQueryException e)
        {
            logger.logMessage("Entry with businessKey " + businessKey + " does not exist in table CamundaMachineOccupation");
        }
    }

}
