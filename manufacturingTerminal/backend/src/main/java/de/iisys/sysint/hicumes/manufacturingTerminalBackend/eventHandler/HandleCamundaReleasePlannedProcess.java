package de.iisys.sysint.hicumes.manufacturingTerminalBackend.eventHandler;

import com.google.common.collect.Iterables;
import com.google.common.eventbus.Subscribe;
import de.iisys.sysint.hicumes.core.entities.CD_ProductionStep;
import de.iisys.sysint.hicumes.core.entities.CD_Tool;
import de.iisys.sysint.hicumes.core.entities.MachineOccupation;
import de.iisys.sysint.hicumes.core.entities.enums.EMachineOccupationStatus;
import de.iisys.sysint.hicumes.core.utils.exceptions.UtilsBaseException;
import de.iisys.sysint.hicumes.core.utils.exceptions.database.DatabaseQueryException;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.entities.CamundaMachineOccupation;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBackgroundProcessingDone;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.ISubscribeEvent;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventCamundaProcessEnd;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventCamundaReleasePlannedProcess;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.manager.SSESessionManager;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.utils.MachineOccupationStatusChanger;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HandleCamundaReleasePlannedProcess implements ISubscribeEvent {

    @Subscribe
    public void handle(EventCamundaReleasePlannedProcess eventCamundaReleasePlannedProcess) throws UtilsBaseException {

        var businessKey = eventCamundaReleasePlannedProcess.getBusinessKey();
        var currentCamundaMachineOccupation = persistService.getCamundaMachineOccupationByBusinessKey(businessKey);
        var releasedMachineOccupations = new ArrayList<CamundaMachineOccupation>();

        if(currentCamundaMachineOccupation.getMachineOccupation().getSubMachineOccupations() != null && !currentCamundaMachineOccupation.getMachineOccupation().getSubMachineOccupations().isEmpty())
        {
            //Sammelauftrag
            for (MachineOccupation machineOccupation: currentCamundaMachineOccupation.getMachineOccupation().getSubMachineOccupations()) {
                releasedMachineOccupations.addAll(MachineOccupationStatusChanger.releaseNextPlannedProcess(persistService, machineOccupation, currentCamundaMachineOccupation.getActiveProductionStep().getProductionStepInfo(), eventCamundaReleasePlannedProcess.getToolExtId()));
            }
        }
        else {
            releasedMachineOccupations.addAll(MachineOccupationStatusChanger.releaseNextPlannedProcess(persistService, currentCamundaMachineOccupation.getMachineOccupation(), currentCamundaMachineOccupation.getActiveProductionStep().getProductionStepInfo(), eventCamundaReleasePlannedProcess.getToolExtId()));
        }

        //If the tool should be changed and the current MO has a different tool, we change it
        if(eventCamundaReleasePlannedProcess.getToolExtId() != null)
        {
            if(!currentCamundaMachineOccupation.getMachineOccupation().getAvailableTools().isEmpty() && !currentCamundaMachineOccupation.getMachineOccupation().getTool().getExternalId().equals(eventCamundaReleasePlannedProcess.getToolExtId()))
            {
                var tool = persistService.getPersistenceManager().getByExternalIdString(CD_Tool.class, eventCamundaReleasePlannedProcess.getToolExtId());
                currentCamundaMachineOccupation.getMachineOccupation().setTool(tool);
                persistService.getPersistenceManager().merge(currentCamundaMachineOccupation.getMachineOccupation());
            }
        }

        for (CamundaMachineOccupation machineOccupation: releasedMachineOccupations) {
            SSESessionManager sessionManager = SSESessionManager.getInstance();
            sessionManager.broadcast(new EventBackgroundProcessingDone(eventCamundaReleasePlannedProcess, machineOccupation));
        }
    }

//    private void releasePlannedProcess(EventCamundaReleasePlannedProcess eventCamundaReleasePlannedProcess, MachineOccupation machineOccupation) throws DatabaseQueryException {
//        var allMachineOccupations = persistService.getMachineOccupationByProductionOrder(machineOccupation.getProductionOrder().getExternalId(), true).collect(Collectors.toList());
//        var indexMachineOccupation = Iterables.indexOf(allMachineOccupations, i -> i.getId() == machineOccupation.getId()
//        );
//
//        var nextMachineOccupations = getNextMachineOccupation(allMachineOccupations, machineOccupation);
//
//        for(MachineOccupation nextMachineOccupation: nextMachineOccupations){
//            var nextCamundaMachineOccupation = persistService.getCamundaMachineOccupationByMachineOccupationId(nextMachineOccupation.getId());
//            if(nextCamundaMachineOccupation.getMachineOccupation().getStatus().equals(EMachineOccupationStatus.PLANNED)) {
//                nextCamundaMachineOccupation.getMachineOccupation().setStatus(EMachineOccupationStatus.READY_TO_START);//Set machine for next step, if current step has the same machineType
//            }
//            if(machineOccupation.getProductionSteps().get(0).getMachineType().getExternalId()
//                    .equals(nextCamundaMachineOccupation.getMachineOccupation().getProductionSteps().get(0).getMachineType().getExternalId()))
//            {
//                nextCamundaMachineOccupation.getMachineOccupation().setMachine(machineOccupation.getMachine());
//            }
//            persistService.getPersistenceManager().merge(nextCamundaMachineOccupation);
//            SSESessionManager sessionManager = SSESessionManager.getInstance();
//            sessionManager.broadcast(new EventBackgroundProcessingDone(eventCamundaReleasePlannedProcess, nextCamundaMachineOccupation));
//        }
//
//        /*if(indexMachineOccupation + 1 <= allMachineOccupations.size())
//        {
//            var nextCamundaMachineOccupation = persistService.getCamundaMachineOccupationByMachineOccupationId(allMachineOccupations.get(indexMachineOccupation + 1).getId());
//            //System.out.println(nextMachineOccupation);
//            nextCamundaMachineOccupation.getMachineOccupation().setStatus(EMachineOccupationStatus.READY_TO_START);
//            //Set machine for next step, if current step has the same machineType
//            if(machineOccupation.getProductionSteps().get(0).getMachineType().getExternalId()
//                    .equals(nextCamundaMachineOccupation.getMachineOccupation().getProductionSteps().get(0).getMachineType().getExternalId()))
//            {
//                nextCamundaMachineOccupation.getMachineOccupation().setMachine(machineOccupation.getMachine());
//            }
//            persistService.getPersistenceManager().merge(nextCamundaMachineOccupation);
//            SSESessionManager sessionManager = SSESessionManager.getInstance();
//            sessionManager.broadcast(new EventBackgroundProcessingDone(eventCamundaReleasePlannedProcess, nextCamundaMachineOccupation));
//        }*/
//    }
//
//    private List<MachineOccupation> getNextMachineOccupation(List<MachineOccupation> allMachineOccupations, MachineOccupation currentMachineOccupation)
//    {
//        List<MachineOccupation> nextMachineOccupations = new ArrayList<>();
//        var indexCurrentMachineOccupation = Iterables.indexOf(allMachineOccupations, i -> i.getId() == currentMachineOccupation.getId());
//
//        if(indexCurrentMachineOccupation + 1 <= allMachineOccupations.size()) {
//            var nextMachineOccupation = allMachineOccupations.get(indexCurrentMachineOccupation + 1);
//            nextMachineOccupations.add(allMachineOccupations.get(indexCurrentMachineOccupation + 1));
//            for(CD_ProductionStep productionStep : nextMachineOccupation.getProductionSteps())
//            {
//                if(productionStep.getProductionStepType() != null && productionStep.getProductionStepType().startsWith("alt"))
//                {
//                    for(MachineOccupation machineOccupation: allMachineOccupations.subList(indexCurrentMachineOccupation +1, allMachineOccupations.size()))
//                    {
//                        for(CD_ProductionStep altProductionStep : machineOccupation.getProductionSteps())
//                        {
//                            if(productionStep.getProductionStepType().equals(altProductionStep.getProductionStepType()))
//                            {
//                                nextMachineOccupations.add(machineOccupation);
//                            }
//                        }
//                    }
//                }
//                if(productionStep.getProductionStepType() != null && productionStep.getProductionStepType().equals("opt"))
//                {
//                    nextMachineOccupations.addAll(this.getNextMachineOccupation(allMachineOccupations.subList(indexCurrentMachineOccupation +1, allMachineOccupations.size()), nextMachineOccupation));
//                }
//            }
//        }
//        return nextMachineOccupations;
//    }
}
