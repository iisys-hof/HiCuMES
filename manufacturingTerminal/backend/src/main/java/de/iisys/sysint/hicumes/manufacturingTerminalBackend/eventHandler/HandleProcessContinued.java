package de.iisys.sysint.hicumes.manufacturingTerminalBackend.eventHandler;

import com.google.common.eventbus.Subscribe;
import de.iisys.sysint.hicumes.core.entities.TimeRecordType;
import de.iisys.sysint.hicumes.core.entities.User;
import de.iisys.sysint.hicumes.core.entities.enums.EMachineOccupationStatus;
import de.iisys.sysint.hicumes.core.utils.exceptions.UtilsBaseException;
import de.iisys.sysint.hicumes.core.utils.exceptions.database.DatabaseQueryException;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBackgroundProcessingDone;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.ISubscribeEvent;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventProcessContinued;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.exceptions.bussines.BusinessException;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.manager.SSESessionManager;

public class HandleProcessContinued implements ISubscribeEvent {

    @Subscribe
    public void handle(EventProcessContinued eventProcessContinued) throws UtilsBaseException, BusinessException {


        var businessKey = eventProcessContinued.getBusinessKey();
        var userName = eventProcessContinued.getUserName();
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

        TimeRecordType manufacturingTimeRecordType = null;
        var timeRecordTypeName = "Manufacturing_" + eventProcessContinued.getFormKey();
        var timeRecordTypeDescription = "Default manufacturing type for " + eventProcessContinued.getFormKey();

        try
        {
            manufacturingTimeRecordType = persistService.getPersistenceManager().getByField(TimeRecordType.class, timeRecordTypeName, "name");
        }
        catch (DatabaseQueryException e)
        {
            manufacturingTimeRecordType = new TimeRecordType(timeRecordTypeName, timeRecordTypeDescription);
            persistService.getPersistenceManager().persist(manufacturingTimeRecordType);
        }

        machineOccupation.getCurrentSubProductionStep().getSubProductionStep().continueTimeRecord(manufacturingTimeRecordType, user);

        if(machineOccupation.getMachineOccupation().getUserOccupation() != null)
        {
            machineOccupation.getMachineOccupation().getUserOccupation().continueTimeRecord(manufacturingTimeRecordType, user);
        }

        machineOccupation.getMachineOccupation().setStatus(EMachineOccupationStatus.RUNNING);
        persistService.getPersistenceManager().merge(machineOccupation);

        //var event = new EventGenerator().generateEvent(Events.CamundaReceiveTopic.REQUEST_ACTIVE_TASK,  machineOccupation.toRequestActiveTask());
        //hazelServer.sendEvent(event);
        SSESessionManager sessionManager = SSESessionManager.getInstance();
        //sessionManager.sendEvent(Long.toString(machineOccupation.getId()), eventResponseActiveTask);
        sessionManager.broadcast(new EventBackgroundProcessingDone(eventProcessContinued,machineOccupation));
    }


}
