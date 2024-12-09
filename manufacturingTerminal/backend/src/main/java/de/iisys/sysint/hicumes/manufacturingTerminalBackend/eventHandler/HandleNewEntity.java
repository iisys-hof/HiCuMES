package de.iisys.sysint.hicumes.manufacturingTerminalBackend.eventHandler;

import com.google.common.eventbus.Subscribe;
import de.iisys.sysint.hicumes.core.entities.EntitySuperClass;
import de.iisys.sysint.hicumes.core.entities.MachineOccupation;
import de.iisys.sysint.hicumes.core.entities.enums.EMachineOccupationStatus;
import de.iisys.sysint.hicumes.core.events.Events;
import de.iisys.sysint.hicumes.core.utils.exceptions.database.DatabasePersistException;
import de.iisys.sysint.hicumes.core.utils.exceptions.database.DatabaseQueryException;
import de.iisys.sysint.hicumes.core.utils.hazelcast.EventGenerator;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.entities.CamundaMachineOccupation;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.ISubscribeEvent;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventNewEntity;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventNewMachineOccupation;

import javax.inject.Singleton;
import java.util.Random;

public class HandleNewEntity implements ISubscribeEvent {

    @Subscribe
    public void handle(EventNewEntity eventNewEntity) throws DatabasePersistException, DatabaseQueryException {
        var isNewPersistedEntity = persistEntityToDatabase(eventNewEntity);
        EntitySuperClass entity = eventNewEntity.getEntity();

        if (entity instanceof MachineOccupation && isNewPersistedEntity) {
              events.emit(new EventNewMachineOccupation((MachineOccupation) entity, eventNewEntity.isCamundaSubMachineOccupation(), true));
        }
        else if(entity instanceof MachineOccupation)
        {
            //If there is already a camunda MachineOccupation, we need to stop the camunda process if it is running
            if(((MachineOccupation) entity).getStatus().equals(EMachineOccupationStatus.ARCHIVED))
            {
                var camundaMachineOccupation = persistService.getCamundaMachineOccupationByMachineOccupationId(entity.getId());
                if(camundaMachineOccupation.getCamundaProcessInstanceId() != null)
                {
                    var processEndEvent = new EventGenerator().generateEvent(Events.CamundaReceiveTopic.END_PROCESS, camundaMachineOccupation.toCamundaManualEndProcess());
                    hazelServer.sendEvent(processEndEvent);
                    camundaMachineOccupation.setCamundaProcessInstanceId(null);
                    persistService.getPersistenceManager().merge(camundaMachineOccupation);
                }
            }
        }
    }

    private boolean persistEntityToDatabase(EventNewEntity eventNewEntity) throws DatabasePersistException {
        return persistService.getPersistenceManager().persistEntitySuperClass(eventNewEntity.getEntity(), eventNewEntity.isForceUpdate());
    }
}
