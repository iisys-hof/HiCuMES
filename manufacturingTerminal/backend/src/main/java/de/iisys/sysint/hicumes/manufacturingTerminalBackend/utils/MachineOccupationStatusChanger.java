package de.iisys.sysint.hicumes.manufacturingTerminalBackend.utils;

import com.google.common.collect.Iterables;
import de.iisys.sysint.hicumes.core.entities.CD_ProductionStep;
import de.iisys.sysint.hicumes.core.entities.MachineOccupation;
import de.iisys.sysint.hicumes.core.entities.ProductionStepInfo;
import de.iisys.sysint.hicumes.core.entities.enums.EMachineOccupationStatus;
import de.iisys.sysint.hicumes.core.entities.enums.EProductionStepType;
import de.iisys.sysint.hicumes.core.utils.exceptions.database.DatabaseQueryException;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.entities.CamundaMachineOccupation;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventCamundaReleasePlannedProcess;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.manager.PersistService;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.startup.StartupController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MachineOccupationStatusChanger {

    public static List<CamundaMachineOccupation> stopOtherAlternativeProcess(PersistService persistService, MachineOccupation machineOccupation, ProductionStepInfo currentProductionStepInfo) throws DatabaseQueryException {
        List<CamundaMachineOccupation> updatedCamundaMachineOccupations = new ArrayList<>();
        if(currentProductionStepInfo != null) {
            var allMachineOccupations = persistService.getMachineOccupationByProductionOrderWithGraph(machineOccupation.getProductionOrder().getExternalId(), true).collect(Collectors.toList());

            var alternativeMachineOccupations = getAlternativeMachineOccupations(allMachineOccupations, machineOccupation, currentProductionStepInfo, persistService);

            for (MachineOccupation alternativeMachineOccupation : alternativeMachineOccupations) {
                var alternativeCamundaMachineOccupation = persistService.getCamundaMachineOccupationByMachineOccupationId(alternativeMachineOccupation.getId());
                if (alternativeCamundaMachineOccupation.getMachineOccupation().getStatus().equals(EMachineOccupationStatus.READY_TO_START)) {
                    alternativeCamundaMachineOccupation.getMachineOccupation().setStatus(EMachineOccupationStatus.SKIPPED);//Set machine for next step, if current step has the same machineType
                }
                persistService.getPersistenceManager().merge(alternativeCamundaMachineOccupation);
                updatedCamundaMachineOccupations.add(alternativeCamundaMachineOccupation);
            }
        }
        return updatedCamundaMachineOccupations;
    }

    public static List<CamundaMachineOccupation> releaseNextPlannedProcess(PersistService persistService, MachineOccupation machineOccupation, ProductionStepInfo currentProductionStepInfo, String toolExternalId) throws DatabaseQueryException {
        List<CamundaMachineOccupation> updatedCamundaMachineOccupations = new ArrayList<>();
        var allMachineOccupations = persistService.getMachineOccupationByProductionOrderWithGraph(machineOccupation.getProductionOrder().getExternalId(), true).collect(Collectors.toList());

        var nextMachineOccupations = getNextMachineOccupations(allMachineOccupations, machineOccupation, currentProductionStepInfo, persistService);

        for(MachineOccupation nextMachineOccupation: nextMachineOccupations){
            var nextCamundaMachineOccupation = persistService.getCamundaMachineOccupationByMachineOccupationId(nextMachineOccupation.getId());
            //var nextProductionStep = nextMachineOccupation.getProductionSteps().get(0);
            var nextProductionStep = persistService.getProductionStepByMachineOccupationId(nextMachineOccupation.getId()).get(0);
            if(nextCamundaMachineOccupation.getMachineOccupation().getStatus().equals(EMachineOccupationStatus.PLANNED)) {
                nextCamundaMachineOccupation.getMachineOccupation().setStatus(EMachineOccupationStatus.READY_TO_START);//Set machine for next step, if current step has the same machineType
            }
            if(machineOccupation.getProductionSteps().get(0).getMachineType().getExternalId()
                    .equals(nextCamundaMachineOccupation.getMachineOccupation().getProductionSteps().get(0).getMachineType().getExternalId()))
            {
                nextCamundaMachineOccupation.getMachineOccupation().setMachine(machineOccupation.getMachine());
            }

            //Set tool, if externalId is set in method
            if(toolExternalId != null)
            {
                if((!nextCamundaMachineOccupation.getMachineOccupation().getAvailableTools().isEmpty() && nextCamundaMachineOccupation.getMachineOccupation().getTool() == null) || (!nextCamundaMachineOccupation.getMachineOccupation().getAvailableTools().isEmpty() && !nextCamundaMachineOccupation.getMachineOccupation().getTool().getExternalId().equals(toolExternalId)))
                {
                    nextCamundaMachineOccupation.getMachineOccupation().setTool(machineOccupation.getTool());
                }
            }

            persistService.getPersistenceManager().merge(nextCamundaMachineOccupation);

            updatedCamundaMachineOccupations.add(nextCamundaMachineOccupation);
        }

        return updatedCamundaMachineOccupations;
    }

    private static List<MachineOccupation> getAlternativeMachineOccupations(List<MachineOccupation> allMachineOccupations, MachineOccupation currentMachineOccupation, ProductionStepInfo currentProductionStepInfo, PersistService persistService) throws DatabaseQueryException {
        List<MachineOccupation> alternativeMachineOccupations = new ArrayList<>();
        var indexCurrentMachineOccupation = Iterables.indexOf(allMachineOccupations, i -> i.getId() == currentMachineOccupation.getId());

        for (MachineOccupation machineOccupation: allMachineOccupations) {
            if(machineOccupation.getId() != currentMachineOccupation.getId()) {
                //var productionStep = machineOccupation.getProductionSteps().get(0);
                var productionStep = persistService.getProductionStepByMachineOccupationId(machineOccupation.getId()).get(0);
                var productionStepInfo = productionStep.getProductionStepInfo();
                //Alle machineOccupations suchen, die den gleichen identifier haben
                if (productionStepInfo != null && productionStepInfo.getStepType().equals(EProductionStepType.ALTERNATIVE) && currentProductionStepInfo.getStepIdent().equals(productionStepInfo.getStepIdent())) {
                    alternativeMachineOccupations.add(machineOccupation);
                }
            }
        }
        return alternativeMachineOccupations;
    }


    private static List<MachineOccupation> getNextMachineOccupations(List<MachineOccupation> allMachineOccupations, MachineOccupation currentMachineOccupation, ProductionStepInfo currentProductionStepInfo, PersistService persistService) throws DatabaseQueryException {
        List<MachineOccupation> nextMachineOccupations = new ArrayList<>();
        var indexCurrentMachineOccupation = Iterables.indexOf(allMachineOccupations, i -> i.getId() == currentMachineOccupation.getId());

        if (indexCurrentMachineOccupation + 1 < allMachineOccupations.size()) {

            //Direkten Nachfolger zur Liste hinzufügen, da wir aktuell nicht in einem alternativen AG sind
            if (currentProductionStepInfo == null || currentProductionStepInfo.getStepType().equals(EProductionStepType.OPTIONAL))
            {
                var nextMachineOccupation = allMachineOccupations.get(indexCurrentMachineOccupation + 1);
                nextMachineOccupations.add(nextMachineOccupation);

                //Nur der erste ProductionStep der MachineOccupation muss überprüft werden
                //var nextProductionStep = nextMachineOccupation.getProductionSteps().get(0);
                var nextProductionStep = persistService.getProductionStepByMachineOccupationId(nextMachineOccupation.getId()).get(0);
                var nextProductionStepInfo = nextProductionStep.getProductionStepInfo();
                //Wenn nächster Schritt ein alternativer AG ist, müssen alle nachfolgenden AGs auf die gleichen identifier geprüft und hinzugefügt werden
                if (nextProductionStepInfo != null && EProductionStepType.ALTERNATIVE.equals(nextProductionStepInfo.getStepType())) {
                    //Alle nachfolgenden MachineOccupations anschauen
                    for (MachineOccupation furtherMachineOccupation : allMachineOccupations.subList(indexCurrentMachineOccupation + 1, allMachineOccupations.size())) {
                        //Über ags der nächsten MachineOccupation gehen
                        //var furtherProductionStep = furtherMachineOccupation.getProductionSteps().get(0);
                        var furtherProductionStep = persistService.getProductionStepByMachineOccupationId(furtherMachineOccupation.getId()).get(0);
                        //Wenn der identifier gleich dem der nächsten MachineOccupation ist, kann diese hinzugefügt werden
                        if (furtherProductionStep.getProductionStepInfo() != null && furtherProductionStep.getProductionStepInfo().getStepIdent().equals(nextProductionStepInfo.getStepIdent())) {
                            nextMachineOccupations.add(furtherMachineOccupation);
                        }
                        //Abbruch, wenn die nächste MachineOccupation keine Infos hinterlegt hat (wenn ein anderer AG kommt, der nicht alternativ/optional ist)
                        if(furtherProductionStep.getProductionStepInfo() == null)
                        {
                            break;
                        }
                    }
                }
                //Wenn der nächste Schritt optional ist, muss auch der darauffolgende freigegeben werden und über rekursion diese Funktion iteriert werden
                if (nextProductionStepInfo != null && EProductionStepType.OPTIONAL.equals(nextProductionStepInfo.getStepType())) {
                    nextMachineOccupations.addAll(getNextMachineOccupations(allMachineOccupations.subList(indexCurrentMachineOccupation + 1, allMachineOccupations.size()), nextMachineOccupation, null, persistService));
                }

                return nextMachineOccupations;
            }

            //Der aktuelle AG ist evtl. ein alternativer AG, wir müssen den nächsten AG freigeben, der in der gleichen Gruppe der AGs ist. Dieser muss nicht unbedingt als nächstes auftauchen
            else if (currentProductionStepInfo.getStepType().equals(EProductionStepType.ALTERNATIVE)) {
                //Über alle nachfolgenden MachineOccupations iterieren

                for (MachineOccupation furtherMachineOccupation : allMachineOccupations.subList(indexCurrentMachineOccupation + 1, allMachineOccupations.size())) {
                    //Den ersten ProdStep überprüfen
                    //var furtherProductionStep = furtherMachineOccupation.getProductionSteps().get(0);
                    var furtherProductionStep = persistService.getProductionStepByMachineOccupationId(furtherMachineOccupation.getId()).get(0);
                    var furtherProductionStepInfo = furtherProductionStep.getProductionStepInfo();
                    //Wenn der nächste AG in der gleichen Gruppe ist, wird dieser zur Liste hinzugefügt und die Schleife beendet
                    if (furtherProductionStepInfo == null
                            || (furtherProductionStepInfo.getStepType().equals(EProductionStepType.ALTERNATIVE) && furtherProductionStepInfo.getStepGroup().equals(currentProductionStepInfo.getStepGroup()) && !furtherProductionStepInfo.getStepIdent().equals(currentProductionStepInfo.getStepIdent()))) {
                        nextMachineOccupations.add(furtherMachineOccupation);
                        return nextMachineOccupations;
                    }
                    else if(furtherProductionStepInfo.getStepType().equals(EProductionStepType.OPTIONAL))
                    {
                        nextMachineOccupations.add(furtherMachineOccupation);
                    }

                    //Es gibt AGs mit der gleichen Gruppe und Identifier, die aber freigeschalten werden müssen (wenn diese nicht bereits übersprungen wurden)
                    if (furtherProductionStepInfo == null || furtherProductionStepInfo.getStepType().equals(EProductionStepType.ALTERNATIVE)
                            && furtherProductionStepInfo.getStepGroup().equals(currentProductionStepInfo.getStepGroup())
                            && !furtherMachineOccupation.getStatus().equals(EMachineOccupationStatus.SKIPPED))
                    {
                        nextMachineOccupations.add(furtherMachineOccupation);
                    }
                }
            }

            //Wenn der aktuelle AG ein paralleler AG ist, muss solange über die nachfolgendnen AGS iteriert werden, bis man bei einem ist, dessen Gruppe abweicht
            else if (currentProductionStepInfo.getStepType().equals(EProductionStepType.PARALLEL))
            {
                var indexFurtherMachineOccupation = 0;
                //Über alle nachfolgenden MachineOccupations iterieren
                for (MachineOccupation followingMachineOccupation : allMachineOccupations.subList(indexCurrentMachineOccupation + 1, allMachineOccupations.size())) {
                    //Den ersten ProdStep überprüfen
                    //var followingProductionStep = followingMachineOccupation.getProductionSteps().get(0);
                    var followingProductionStep = persistService.getProductionStepByMachineOccupationId(followingMachineOccupation.getId()).get(0);
                    var followingProductionStepInfo = followingProductionStep.getProductionStepInfo();

                    //Wenn der nächste AG in der gleichen Gruppe ist aber eine kleiner Schritt-NR hat, kann dieser ignoriert werden
                    if(followingProductionStepInfo != null && followingProductionStepInfo.getStepGroup().equals(currentProductionStepInfo.getStepGroup()) &&
                            followingProductionStepInfo.getStepNr() <= currentProductionStepInfo.getStepNr())
                    {
                        indexFurtherMachineOccupation ++;
                        continue;
                    }

                    //Wenn der nächste AG in einer anderen Gruppe ist oder gar keine Info hat, kann dieser freigeschalten werden
                    if (followingProductionStepInfo == null || !followingProductionStepInfo.getStepGroup().equals(currentProductionStepInfo.getStepGroup()))
                    {
                        //Nächsten Schritt hinzufügen
                        nextMachineOccupations.add(followingMachineOccupation);
                        //Wenn der nächste AG productionStepInfos hat, müssen evtl. noch mehr freigegeben werden
                        if(followingProductionStepInfo != null)
                        {
                            //Wenn nächster Schritt ein alternativer AG ist, müssen alle nachfolgenden AGs auf die gleichen identifier geprüft und hinzugefügt werden
                            if (EProductionStepType.ALTERNATIVE.equals(followingProductionStepInfo.getStepType())) {
                                //Alle nachfolgenden MachineOccupations anschauen
                                for (MachineOccupation furtherMachineOccupation : allMachineOccupations.subList(indexCurrentMachineOccupation + indexFurtherMachineOccupation + 1, allMachineOccupations.size())) {
                                    //Über ags der nächsten MachineOccupation gehen
                                    //var furtherProductionStep = furtherMachineOccupation.getProductionSteps().get(0);
                                    var furtherProductionStep = persistService.getProductionStepByMachineOccupationId(furtherMachineOccupation.getId()).get(0);
                                    //Wenn der identifier gleich dem der nächsten MachineOccupation ist, kann diese hinzugefügt werden
                                    if (furtherProductionStep.getProductionStepInfo() != null && furtherProductionStep.getProductionStepInfo().getStepIdent().equals(followingProductionStepInfo.getStepIdent())) {
                                        //only add if ungliech
                                        if(!nextMachineOccupations.contains(furtherMachineOccupation))
                                        {
                                            nextMachineOccupations.add(furtherMachineOccupation);
                                        }
                                    }
                                    //Abbruch, wenn die nächste MachineOccupation keine Infos hinterlegt hat (wenn ein anderer AG kommt, der nicht alternativ/optional ist)
                                    if(furtherProductionStep.getProductionStepInfo() == null)
                                    {
                                        break;
                                    }
                                }
                            }
                            else {
                                nextMachineOccupations.addAll(getNextMachineOccupations(allMachineOccupations.subList(indexCurrentMachineOccupation + indexFurtherMachineOccupation, allMachineOccupations.size()), followingMachineOccupation, followingProductionStepInfo, persistService));
                            }

                        }
                        return nextMachineOccupations;
                    }
                    //Wenn der nächste AG in der gleichen Gruppe ist, aber einen anderen Identifier hat, wird er freigegeben
                    else if (followingProductionStepInfo.getStepGroup().equals(currentProductionStepInfo.getStepGroup()) &&
                            !followingProductionStepInfo.getStepIdent().equals(currentProductionStepInfo.getStepIdent()))
                    {
                        nextMachineOccupations.add(followingMachineOccupation);
                        return nextMachineOccupations;
                    }
                }
            }

        }
        return nextMachineOccupations;
    }
}
