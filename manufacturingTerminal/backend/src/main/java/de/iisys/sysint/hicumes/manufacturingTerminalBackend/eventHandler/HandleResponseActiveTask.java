package de.iisys.sysint.hicumes.manufacturingTerminalBackend.eventHandler;

import com.google.common.eventbus.Subscribe;
import de.iisys.sysint.hicumes.core.entities.*;
import de.iisys.sysint.hicumes.core.entities.enums.EMachineOccupationStatus;
import de.iisys.sysint.hicumes.core.utils.exceptions.database.DatabasePersistException;
import de.iisys.sysint.hicumes.core.utils.exceptions.database.DatabaseQueryException;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.entities.CamundaSubProductionStep;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBackgroundProcessingDone;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.EventQueue;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.IEmitEvent;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.ISubscribeEvent;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventResponseActiveTask;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.manager.PersistService;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.manager.SSESessionManager;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ejb.Singleton;
import java.util.List;

public class HandleResponseActiveTask implements ISubscribeEvent {

    @EJB
    PersistService persistService;

    @Subscribe
    public void handle(EventResponseActiveTask eventResponseActiveTask) throws DatabaseQueryException, DatabasePersistException {

        var businessKey = eventResponseActiveTask.getBusinessKey();
        var userName = eventResponseActiveTask.getUserName();
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

        var camundaSubProductionStep = new CamundaSubProductionStep(machineOccupation, eventResponseActiveTask.getTaskId(),eventResponseActiveTask.getName(),eventResponseActiveTask.getFormKey(),eventResponseActiveTask.getFormField(), eventResponseActiveTask.getProcessVariables(), eventResponseActiveTask.getTaskDefinitionKey());
        var subProductionStep = new SubProductionStep();

        if(eventResponseActiveTask.getSuspensionType() == null) {
            TimeRecordType manufacturingTimeRecordType = null;
            var timeRecordTypeName = "Manufacturing_" + eventResponseActiveTask.getFormKey();
            var timeRecordTypeDescription = "Default manufacturing type for " + eventResponseActiveTask.getFormKey();

            try {
                manufacturingTimeRecordType = persistService.getPersistenceManager().getByField(TimeRecordType.class, timeRecordTypeName, "name");
            } catch (DatabaseQueryException e) {
                manufacturingTimeRecordType = new TimeRecordType(timeRecordTypeName, timeRecordTypeDescription);
                persistService.getPersistenceManager().persist(manufacturingTimeRecordType);
            }

            subProductionStep.startTimeRecord(manufacturingTimeRecordType, user);

            if(machineOccupation.getMachineOccupation().getUserOccupation() != null)
            {
                machineOccupation.getMachineOccupation().getUserOccupation().setUserActive(user);
                machineOccupation.getMachineOccupation().getUserOccupation().startTimeRecordForActiveUsers(manufacturingTimeRecordType);
            }
        }
        else
        {
            SuspensionType suspensionType = null;
            var suspensionTypeName = eventResponseActiveTask.getSuspensionType();
            var suspensionTypeDescription = "Generated break type for " + eventResponseActiveTask.getSuspensionType();

            try {
                suspensionType = persistService.getPersistenceManager().getByField(SuspensionType.class, suspensionTypeName, "name");
            } catch (DatabaseQueryException e) {
                suspensionType = new SuspensionType(suspensionTypeName, suspensionTypeDescription);
                persistService.getPersistenceManager().persist(suspensionType);
            }

            var userOccupation = machineOccupation.getMachineOccupation().getUserOccupation();
            if(userOccupation != null && (userOccupation.getActiveUsers().contains(user) && userOccupation.getActiveUsers().size() > 1))
            {
                userOccupation.pauseTimeRecord(suspensionType, user);
                TimeRecordType manufacturingTimeRecordType = null;
                var timeRecordTypeName = "Manufacturing_" + eventResponseActiveTask.getFormKey();
                var timeRecordTypeDescription = "Default manufacturing type for " + eventResponseActiveTask.getFormKey();

                try {
                    manufacturingTimeRecordType = persistService.getPersistenceManager().getByField(TimeRecordType.class, timeRecordTypeName, "name");
                } catch (DatabaseQueryException e) {
                    manufacturingTimeRecordType = new TimeRecordType(timeRecordTypeName, timeRecordTypeDescription);
                    persistService.getPersistenceManager().persist(manufacturingTimeRecordType);
                }

                subProductionStep.startTimeRecord(manufacturingTimeRecordType, user);
                machineOccupation.getMachineOccupation().getUserOccupation().startTimeRecordForActiveUsers(manufacturingTimeRecordType);
            }
            else if(userOccupation != null && (userOccupation.getActiveUsers().contains(user) && userOccupation.getActiveUsers().size() == 1))
            {
                userOccupation.pauseTimeRecord(suspensionType, user);
                subProductionStep.pauseTimeRecord(null, suspensionType, user);
                machineOccupation.getMachineOccupation().setStatus(EMachineOccupationStatus.PAUSED);
            }
            else
            {
                //Only pause the subProductionStep, if there is no other active user!
                subProductionStep.pauseTimeRecord(null, suspensionType, user);
                machineOccupation.getMachineOccupation().setStatus(EMachineOccupationStatus.PAUSED);

            }

        }

        camundaSubProductionStep.setSubProductionStep(subProductionStep);
        machineOccupation.getMachineOccupation().subProductionStepsAdd(subProductionStep);

        if(eventResponseActiveTask.getActiveProcess() != null && !eventResponseActiveTask.getActiveProcess().equals(""))
        {
            var activeProductionSteps = persistService.getProductionStepByCamundaProcessName(eventResponseActiveTask.getActiveProcess());
            for (CD_ProductionStep productionStep : activeProductionSteps) {
                if(containsProductionStep(machineOccupation.getMachineOccupation().getProductionSteps(), productionStep.getExternalId()))
                {
                    machineOccupation.setActiveProductionStep(productionStep);
                    break;
                }
            }
        }

        if (!machineOccupation.getCamundaSubProductionSteps().contains(camundaSubProductionStep)) {
            //persistService.getPersistenceManager().persist(camundaSubProductionStep);
            machineOccupation.startSubProductionStep(camundaSubProductionStep);
            //machineOccupation.getCurrentSubProductionStep().getSubProductionStep().startTimeRecord(new TimeRecordType("Manufacturing", "Default Manufacturing type"));

            subProductionStep.setMachineOccupation(machineOccupation.getMachineOccupation());
            subProductionStep.setExternalId(machineOccupation.getCurrentSubProductionStep().getTaskDefinitionKey() + "_" + machineOccupation.getCurrentSubProductionStep().getTaskId() + "_" + machineOccupation.getMachineOccupation().getExternalId());

            persistService.getPersistenceManager().merge(machineOccupation);
        }

        SSESessionManager sessionManager = SSESessionManager.getInstance();
        //sessionManager.sendEvent(Long.toString(machineOccupation.getId()), eventResponseActiveTask);
        sessionManager.broadcast(new EventBackgroundProcessingDone(eventResponseActiveTask,machineOccupation));

    }

    private boolean containsProductionStep(final List<CD_ProductionStep> list, final String externalId){
        return list.stream().anyMatch(o -> externalId.equals(o.getExternalId()));
    }
}
