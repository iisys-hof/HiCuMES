package de.iisys.sysint.hicumes.manufacturingTerminalBackend.eventHandler;

import com.google.common.eventbus.Subscribe;
import de.iisys.sysint.hicumes.core.entities.SuspensionType;
import de.iisys.sysint.hicumes.core.entities.TimeRecordType;
import de.iisys.sysint.hicumes.core.entities.User;
import de.iisys.sysint.hicumes.core.entities.enums.EMachineOccupationStatus;
import de.iisys.sysint.hicumes.core.utils.exceptions.UtilsBaseException;
import de.iisys.sysint.hicumes.core.utils.exceptions.database.DatabaseQueryException;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBackgroundProcessingDone;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.ISubscribeEvent;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventProcessPaused;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.exceptions.bussines.BusinessException;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.manager.SSESessionManager;

public class HandleProcessPaused implements ISubscribeEvent {

    @Subscribe
    public void handle(EventProcessPaused eventProcessPaused) throws UtilsBaseException, BusinessException {


        var businessKey = eventProcessPaused.getBusinessKey();
        var userName = eventProcessPaused.getUserName();
        var machineOccupation = persistService.getCamundaMachineOccupationByBusinessKey(businessKey);
        User user = null;
        try
        {
            user = persistService.getPersistenceManager().getByField(User.class, userName, "userName");
        }
        catch (DatabaseQueryException e)
        {
            user = new User(userName);
            persistService.getPersistenceManager().persist(user);
        }

        SuspensionType breakSuspensionType = null;
        try
        {
            breakSuspensionType = persistService.getPersistenceManager().getByField(SuspensionType.class, eventProcessPaused.getSuspensionType(), "name");
        }
        catch (DatabaseQueryException e)
        {
            breakSuspensionType = new SuspensionType(eventProcessPaused.getSuspensionType(), "Generated break type for " + eventProcessPaused.getSuspensionType());
            persistService.getPersistenceManager().persist(breakSuspensionType);
        }

        var userOccupation = machineOccupation.getMachineOccupation().getUserOccupation();
        if(userOccupation != null && (userOccupation.getActiveUsers().contains(user) && userOccupation.getActiveUsers().size() > 1))
        {
            userOccupation.pauseTimeRecord(breakSuspensionType, user);
        }
        else if(userOccupation != null && (userOccupation.getActiveUsers().contains(user) && userOccupation.getActiveUsers().size() == 1))
        {
            userOccupation.pauseTimeRecord(breakSuspensionType, user);
            machineOccupation.getCurrentSubProductionStep().getSubProductionStep().pauseTimeRecord(null, breakSuspensionType, user);
            machineOccupation.getMachineOccupation().setStatus(EMachineOccupationStatus.PAUSED);
        }
        else
        {
            //Only pause the subProductionStep, if there is no other active user
            machineOccupation.getCurrentSubProductionStep().getSubProductionStep().pauseTimeRecord(null, breakSuspensionType, user);
            machineOccupation.getMachineOccupation().setStatus(EMachineOccupationStatus.PAUSED);
        }

        //machineOccupation.setCamundaProcessInstanceId(null);
        persistService.getPersistenceManager().merge(machineOccupation);
        //var event = new EventGenerator().generateEvent(Events.CamundaReceiveTopic.REQUEST_ACTIVE_TASK,  machineOccupation.toRequestActiveTask());
        //hazelServer.sendEvent(event);
        SSESessionManager sessionManager = SSESessionManager.getInstance();
        //sessionManager.sendEvent(Long.toString(machineOccupation.getId()), eventResponseActiveTask);
        sessionManager.broadcast(new EventBackgroundProcessingDone(eventProcessPaused,machineOccupation));
    }

}
