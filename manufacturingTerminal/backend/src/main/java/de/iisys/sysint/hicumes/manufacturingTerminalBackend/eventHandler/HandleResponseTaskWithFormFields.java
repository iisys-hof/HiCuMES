package de.iisys.sysint.hicumes.manufacturingTerminalBackend.eventHandler;

import com.google.common.eventbus.Subscribe;
import de.iisys.sysint.hicumes.core.events.Events;
import de.iisys.sysint.hicumes.core.utils.exceptions.database.DatabaseQueryException;
import de.iisys.sysint.hicumes.core.utils.exceptions.json.JsonParsingUtilsException;
import de.iisys.sysint.hicumes.core.utils.hazelcast.EventGenerator;
import de.iisys.sysint.hicumes.core.utils.logger.Logger;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.ISubscribeEvent;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventResponseTaskWithFormFields;

public class HandleResponseTaskWithFormFields implements ISubscribeEvent {
    private final Logger logger = new Logger(this.getClass().getName());

    @Subscribe
    public void handle(EventResponseTaskWithFormFields eventResponseTaskWithFormFields) throws DatabaseQueryException, JsonParsingUtilsException {
        var businessKey = eventResponseTaskWithFormFields.getBusinessKey();
        var machineOccupation = persistService.getCamundaMachineOccupationByBusinessKey(businessKey);
        var userName = eventResponseTaskWithFormFields.getUserName();
        var suspensionType = eventResponseTaskWithFormFields.getSuspensionType();

        //Only request active task, when processInstanceId is set (i.e. camunda task is running)
        if(machineOccupation.getCamundaProcessInstanceId() != null) {
            var event = new EventGenerator().generateEvent(Events.CamundaReceiveTopic.REQUEST_ACTIVE_TASK, machineOccupation.toRequestActiveTask(userName, suspensionType));
            hazelServer.sendEvent(event);
        }
    }
}
