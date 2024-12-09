package de.iisys.sysint.hicumes.manufacturingTerminalBackend.eventHandler;

import com.google.common.eventbus.Subscribe;
import de.iisys.sysint.hicumes.core.events.Events;
import de.iisys.sysint.hicumes.core.utils.exceptions.UtilsBaseException;
import de.iisys.sysint.hicumes.core.utils.hazelcast.EventGenerator;
import de.iisys.sysint.hicumes.core.utils.hazelcast.HazelServer;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.ISubscribeEvent;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventRequestActiveTask;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.exceptions.bussines.BusinessException;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.manager.PersistService;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.startup.StartupController;

import javax.inject.Inject;

public class HandleRequestActiveTask implements ISubscribeEvent {

    @Subscribe
    public void handle(EventRequestActiveTask eventRequestActiveTask) throws UtilsBaseException, BusinessException {
        var businessKey = eventRequestActiveTask.getBusinessKey();
        var userName = eventRequestActiveTask.getUserName();
        var machineOccupation = persistService.getCamundaMachineOccupationByBusinessKey(businessKey);
        var event = new EventGenerator().generateEvent(Events.CamundaReceiveTopic.REQUEST_ACTIVE_TASK,  machineOccupation.toRequestActiveTask(userName, null));
        hazelServer.sendEvent(event);
    }
}
