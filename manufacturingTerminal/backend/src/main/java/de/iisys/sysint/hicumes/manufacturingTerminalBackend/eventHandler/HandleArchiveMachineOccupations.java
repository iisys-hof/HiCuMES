package de.iisys.sysint.hicumes.manufacturingTerminalBackend.eventHandler;

import com.google.common.eventbus.Subscribe;
import de.iisys.sysint.hicumes.core.entities.TimeRecordType;
import de.iisys.sysint.hicumes.core.entities.User;
import de.iisys.sysint.hicumes.core.entities.enums.EMachineOccupationStatus;
import de.iisys.sysint.hicumes.core.utils.exceptions.UtilsBaseException;
import de.iisys.sysint.hicumes.core.utils.exceptions.database.DatabaseQueryException;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventArchiveMachineOccupations;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBackgroundProcessingDone;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.ISubscribeEvent;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventProcessContinued;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.exceptions.bussines.BusinessException;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.manager.SSESessionManager;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.stream.Collectors;

public class HandleArchiveMachineOccupations implements ISubscribeEvent {

    @Subscribe
    public void handle(EventArchiveMachineOccupations eventArchiveMachineOccupations) throws UtilsBaseException, BusinessException {

        var finishedXDaysAgo = eventArchiveMachineOccupations.getFinishedXDaysAgo();

        var beforeDate = LocalDateTime.now().minusDays(finishedXDaysAgo);
        var result = persistService.getMachineOccupationsFinishedBeforeDate(beforeDate).collect(Collectors.toList());
        for (var machineOccupation: result ) {
            machineOccupation.setStatus(EMachineOccupationStatus.ARCHIVED);
            persistService.getPersistenceManager().merge(machineOccupation);
        }
    }


}
