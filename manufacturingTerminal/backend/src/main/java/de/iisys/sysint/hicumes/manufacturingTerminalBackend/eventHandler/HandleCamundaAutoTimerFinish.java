package de.iisys.sysint.hicumes.manufacturingTerminalBackend.eventHandler;

import com.google.common.eventbus.Subscribe;
import de.iisys.sysint.hicumes.core.entities.CD_ProductionStep;
import de.iisys.sysint.hicumes.core.entities.SubProductionStep;
import de.iisys.sysint.hicumes.core.entities.TimeRecordType;
import de.iisys.sysint.hicumes.core.entities.User;
import de.iisys.sysint.hicumes.core.utils.exceptions.UtilsBaseException;
import de.iisys.sysint.hicumes.core.utils.exceptions.database.DatabaseQueryException;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.entities.CamundaSubProductionStep;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBackgroundProcessingDone;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.ISubscribeEvent;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventCamundaAutoTimerFinish;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.manager.SSESessionManager;

import java.time.LocalDateTime;
import java.util.List;

public class HandleCamundaAutoTimerFinish implements ISubscribeEvent {

    @Subscribe
    public void handle(EventCamundaAutoTimerFinish eventCamundaAutoTimerFinish) throws UtilsBaseException {

        var businessKey = eventCamundaAutoTimerFinish.getBusinessKey();
        var userName = eventCamundaAutoTimerFinish.getUserName();
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

        var camundaSubProductionStep = new CamundaSubProductionStep(machineOccupation, eventCamundaAutoTimerFinish.getTaskId(), eventCamundaAutoTimerFinish.getName(),eventCamundaAutoTimerFinish.getFormKey(),eventCamundaAutoTimerFinish.getFormField(), eventCamundaAutoTimerFinish.getProcessVariables(), eventCamundaAutoTimerFinish.getTaskDefinitionKey());
        var subProductionStep = new SubProductionStep();

        if(eventCamundaAutoTimerFinish.getSuspensionType() == null) {
            TimeRecordType manufacturingTimeRecordType = null;
            var timeRecordTypeName = "Manufacturing_" + eventCamundaAutoTimerFinish.getFormKey();
            var timeRecordTypeDescription = "Default manufacturing type for " + eventCamundaAutoTimerFinish.getFormKey();

            try {
                manufacturingTimeRecordType = persistService.getPersistenceManager().getByField(TimeRecordType.class, timeRecordTypeName, "name");
            } catch (DatabaseQueryException e) {
                manufacturingTimeRecordType = new TimeRecordType(timeRecordTypeName, timeRecordTypeDescription);
                persistService.getPersistenceManager().persist(manufacturingTimeRecordType);
            }

            subProductionStep.startTimeRecord(manufacturingTimeRecordType, user);
        }

        machineOccupation.getMachineOccupation().setActualStartDateTime(LocalDateTime.now());
        camundaSubProductionStep.setSubProductionStep(subProductionStep);
        machineOccupation.getMachineOccupation().subProductionStepsAdd(subProductionStep);
        camundaSubProductionStep.stopTimerOnEnd(eventCamundaAutoTimerFinish.getTimeOffsetSeconds());

        if(eventCamundaAutoTimerFinish.getActiveProcess() != null && !eventCamundaAutoTimerFinish.getActiveProcess().equals(""))
        {
            var activeProductionSteps = persistService.getProductionStepByCamundaProcessName(eventCamundaAutoTimerFinish.getActiveProcess());
            for (CD_ProductionStep productionStep : activeProductionSteps) {
                if(containsProductionStep(machineOccupation.getMachineOccupation().getProductionSteps(), productionStep.getExternalId()))
                {
                    machineOccupation.setActiveProductionStep(productionStep);
                    break;
                }
            }
        }

        if (!machineOccupation.getCamundaSubProductionSteps().contains(camundaSubProductionStep)) {
            machineOccupation.startSubProductionStep(camundaSubProductionStep);
            subProductionStep.setMachineOccupation(machineOccupation.getMachineOccupation());
            subProductionStep.setExternalId(machineOccupation.getCurrentSubProductionStep().getTaskDefinitionKey() + "_" + machineOccupation.getCurrentSubProductionStep().getTaskId() + "_" + machineOccupation.getMachineOccupation().getExternalId());
        }

        persistService.getPersistenceManager().merge(machineOccupation);

        SSESessionManager sessionManager = SSESessionManager.getInstance();
        sessionManager.broadcast(new EventBackgroundProcessingDone(eventCamundaAutoTimerFinish, machineOccupation));
    }


    private boolean containsProductionStep(final List<CD_ProductionStep> list, final String externalId){
        return list.stream().anyMatch(o -> externalId.equals(o.getExternalId()));
    }
}
