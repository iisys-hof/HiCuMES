package de.iisys.sysint.hicumes.manufacturingTerminalBackend.eventHandler;

import com.google.common.eventbus.Subscribe;
import de.iisys.sysint.hicumes.core.events.Events;
import de.iisys.sysint.hicumes.core.utils.exceptions.database.DatabaseQueryException;
import de.iisys.sysint.hicumes.core.utils.hazelcast.EventGenerator;
import de.iisys.sysint.hicumes.core.utils.logger.Logger;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.ISubscribeEvent;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventResponseTaskWithFormFields;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventUpdateCamundaMappings;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.services.CamundaMappingStateService;
import lombok.SneakyThrows;

import javax.ejb.EJB;
import javax.inject.Inject;

public class HandleUpdateCamundaMappings implements ISubscribeEvent {

    private final Logger logger = new Logger(this.getClass().getName());

    @EJB
    CamundaMappingStateService camundaMappingStateService;

    @SneakyThrows
    @Subscribe
    public void handle(EventUpdateCamundaMappings eventUpdateCamundaMappings)  {
        camundaMappingStateService.update(eventUpdateCamundaMappings.getMappingAndDataSources());

    }
}
