package de.iisys.sysint.hicumes.manufacturingTerminalBackend.eventHandler;

import com.google.common.eventbus.Subscribe;
import de.iisys.sysint.hicumes.core.entities.MachineOccupation;
import de.iisys.sysint.hicumes.core.entities.ProductionNumbers;
import de.iisys.sysint.hicumes.core.entities.enums.EMachineOccupationStatus;
import de.iisys.sysint.hicumes.core.events.Events;
import de.iisys.sysint.hicumes.core.utils.exceptions.UtilsBaseException;
import de.iisys.sysint.hicumes.core.utils.exceptions.database.DatabasePersistException;
import de.iisys.sysint.hicumes.core.utils.exceptions.database.DatabaseQueryException;
import de.iisys.sysint.hicumes.core.utils.hazelcast.EventGenerator;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBackgroundProcessingDone;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.EventResult;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.ISubscribeEvent;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventCamundaStartProcess;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventChangeMachineOccupationStatus;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.exceptions.bussines.BusinessException;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.manager.SSESessionManager;

import java.util.stream.Collectors;

public class HandleChangeMachineOccupationStatus implements ISubscribeEvent {

    @Subscribe
    public void handle(EventChangeMachineOccupationStatus eventChangeMachineOccupationStatus) throws UtilsBaseException, BusinessException {

        var camundaMachineOccupationId = eventChangeMachineOccupationStatus.getCamundaMachineOccupationId();
        var status = eventChangeMachineOccupationStatus.getStatus();
        var camundaMachineOccupation = persistService.getCamundaMachineOccupationById(camundaMachineOccupationId);
        var userName = eventChangeMachineOccupationStatus.getUserName();

        camundaMachineOccupation.getMachineOccupation().setStatus(status);
        //Es gibt bereits einen Camunda Prozess, der noch beendet werden muss, weil der Auftrag beendet werden soll
        if(camundaMachineOccupation.getCamundaProcessInstanceId() != null && shouldEndProcessInstance(status))
        {
            var event = new EventGenerator().generateEvent(Events.CamundaReceiveTopic.END_PROCESS, camundaMachineOccupation.toCamundaManualEndProcess());
            hazelServer.sendEvent(event);
        }
        //Wird der Status auf READY TO START gesetzt wird und ein Camunda Prozess vorhanden war (ProcessId != null),
        // sollte zur Sicherheit der alte Prozess nochmals beendet werden und dann das Feld geleert werden, um den Prozess erneut in Camunda zu bearbeiten
        else if(camundaMachineOccupation.getCamundaProcessInstanceId() != null && status.equals(EMachineOccupationStatus.READY_TO_START))
        {
            var event = new EventGenerator().generateEvent(Events.CamundaReceiveTopic.END_PROCESS, camundaMachineOccupation.toCamundaManualEndProcess());
            hazelServer.sendEvent(event);
            camundaMachineOccupation.setCamundaProcessInstanceId(null);
        }
        persistService.getPersistenceManager().merge(camundaMachineOccupation);

        SSESessionManager sessionManager = SSESessionManager.getInstance();
        sessionManager.broadcast(new EventBackgroundProcessingDone(eventChangeMachineOccupationStatus, camundaMachineOccupation));
    }


    private boolean shouldEndProcessInstance(EMachineOccupationStatus status)
    {
        if( status.equals(EMachineOccupationStatus.FINISHED) ||
            status.equals(EMachineOccupationStatus.ABORTED) ||
            status.equals(EMachineOccupationStatus.ARCHIVED) ||
            status.equals(EMachineOccupationStatus.SKIPPED))
        {
            return true;
        }
        return false;
    }
}