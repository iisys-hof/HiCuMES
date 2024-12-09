package de.iisys.sysint.hicumes.manufacturingTerminalBackend.eventHandler;

import com.google.common.eventbus.Subscribe;
import de.iisys.sysint.hicumes.core.entities.TimeRecordType;
import de.iisys.sysint.hicumes.core.entities.User;
import de.iisys.sysint.hicumes.core.entities.UserOccupation;
import de.iisys.sysint.hicumes.core.utils.exceptions.UtilsBaseException;
import de.iisys.sysint.hicumes.core.utils.exceptions.database.DatabaseQueryException;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBackgroundProcessingDone;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.ISubscribeEvent;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventUserOccupationJoin;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.exceptions.bussines.BusinessException;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.manager.SSESessionManager;

public class HandleUserOccupationJoin implements ISubscribeEvent {

    @Subscribe
    public void handle(EventUserOccupationJoin eventUserOccupationJoin) throws UtilsBaseException, BusinessException {


        var cmoId = eventUserOccupationJoin.getCmoId();
        var userName = eventUserOccupationJoin.getUserName();

        UserOccupation userOccupation = null;
        try
        {
            var machineOccupation = persistService.getCamundaMachineOccupationById(cmoId);
            userOccupation = persistService.getUserOccupationByCamundaMachineOccupationId(cmoId);

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
            var timeRecordTypeName = "Manufacturing_" + machineOccupation.getCurrentSubProductionStep().getFormKey();
            var timeRecordTypeDescription = "Default manufacturing type for " + machineOccupation.getCurrentSubProductionStep().getFormKey();

            try {
                manufacturingTimeRecordType = persistService.getPersistenceManager().getByField(TimeRecordType.class, timeRecordTypeName, "name");
            } catch (DatabaseQueryException e) {
                manufacturingTimeRecordType = new TimeRecordType(timeRecordTypeName, timeRecordTypeDescription);
                persistService.getPersistenceManager().persist(manufacturingTimeRecordType);
            }

            userOccupation.startOrContinueTimeRecord(user, manufacturingTimeRecordType);
            persistService.getPersistenceManager().merge(userOccupation);

            //var event = new EventGenerator().generateEvent(Events.CamundaReceiveTopic.REQUEST_ACTIVE_TASK,  machineOccupation.toRequestActiveTask());
            //hazelServer.sendEvent(event);
            SSESessionManager sessionManager = SSESessionManager.getInstance();
            //sessionManager.sendEvent(Long.toString(machineOccupation.getId()), eventResponseActiveTask);
            sessionManager.broadcast(new EventBackgroundProcessingDone(eventUserOccupationJoin, machineOccupation));
        }
        catch (DatabaseQueryException e)
        {
            e.printStackTrace();
        }

    }


}
