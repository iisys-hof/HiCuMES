package de.iisys.sysint.hicumes.manufacturingTerminalBackend.eventHandler;

import com.google.common.eventbus.Subscribe;
import de.iisys.sysint.hicumes.core.entities.MachineOccupation;
import de.iisys.sysint.hicumes.core.entities.Note;
import de.iisys.sysint.hicumes.core.utils.exceptions.UtilsBaseException;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBackgroundProcessingDone;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.ISubscribeEvent;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventAddNote;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.exceptions.bussines.BusinessException;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.manager.SSESessionManager;

import java.time.LocalDateTime;

public class HandleAddNote implements ISubscribeEvent {

    @Subscribe
    public void handle(EventAddNote eventAddNote) throws UtilsBaseException, BusinessException {

        var machineOccupationId = eventAddNote.getMachineOccupationId();
        var noteString = eventAddNote.getNoteString();
        var userName = eventAddNote.getUserName();

        var camundaMachineOccupation = persistService.getCamundaMachineOccupationByMachineOccupationId(machineOccupationId);
        var note = new Note(noteString, userName, LocalDateTime.now());
        camundaMachineOccupation.getMachineOccupation().getProductionOrder().getNotes().add(note);


        persistService.getPersistenceManager().merge(camundaMachineOccupation);

        //var event = new EventGenerator().generateEvent(Events.CamundaReceiveTopic.REQUEST_ACTIVE_TASK,  machineOccupation.toRequestActiveTask());
        //hazelServer.sendEvent(event);
        SSESessionManager sessionManager = SSESessionManager.getInstance();
        //sessionManager.sendEvent(Long.toString(machineOccupation.getId()), eventResponseActiveTask);

        if(camundaMachineOccupation.isSubMachineOccupation())
        {
            var parentCMO = persistService.getCamundaMachineOccupationByMachineOccupationId(camundaMachineOccupation.getMachineOccupation().getParentMachineOccupation().getId());
            sessionManager.broadcast(new EventBackgroundProcessingDone(eventAddNote,parentCMO));
        }
        else
        {
            sessionManager.broadcast(new EventBackgroundProcessingDone(eventAddNote, camundaMachineOccupation));
        }
    }

}
