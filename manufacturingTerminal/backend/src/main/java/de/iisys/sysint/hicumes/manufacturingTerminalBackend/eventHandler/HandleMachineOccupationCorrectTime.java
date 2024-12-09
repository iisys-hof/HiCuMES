package de.iisys.sysint.hicumes.manufacturingTerminalBackend.eventHandler;

import com.google.common.eventbus.Subscribe;
import de.iisys.sysint.hicumes.core.entities.SuspensionType;
import de.iisys.sysint.hicumes.core.entities.User;
import de.iisys.sysint.hicumes.core.entities.enums.EMachineOccupationStatus;
import de.iisys.sysint.hicumes.core.utils.exceptions.UtilsBaseException;
import de.iisys.sysint.hicumes.core.utils.exceptions.database.DatabaseQueryException;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBackgroundProcessingDone;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.ISubscribeEvent;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventMachineOccupationCorrectTime;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventProcessPaused;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.exceptions.bussines.BusinessException;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.manager.SSESessionManager;

import java.time.Duration;

public class HandleMachineOccupationCorrectTime implements ISubscribeEvent {

    @Subscribe
    public void handle(EventMachineOccupationCorrectTime eventMachineOccupationCorrectTime) throws UtilsBaseException, BusinessException {


        var businessKey = eventMachineOccupationCorrectTime.getBusinessKey();
        var userName = eventMachineOccupationCorrectTime.getUserName();
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

        var currentDuration = Duration.ofSeconds(eventMachineOccupationCorrectTime.getDurationSeconds());
        var oldDurationCorrect = machineOccupation.getMachineOccupation().getTimeDurations().get("correct");
        if(oldDurationCorrect != null)
        {
            machineOccupation.getMachineOccupation().getTimeDurations().replace("correct", oldDurationCorrect.plus(currentDuration));
        }
        else
        {
            machineOccupation.getMachineOccupation().getTimeDurations().put("correct", currentDuration);
        }

        //machineOccupation.setCamundaProcessInstanceId(null);
        persistService.getPersistenceManager().merge(machineOccupation);
    }

}
