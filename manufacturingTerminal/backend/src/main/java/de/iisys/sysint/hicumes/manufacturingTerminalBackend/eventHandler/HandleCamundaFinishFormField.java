package de.iisys.sysint.hicumes.manufacturingTerminalBackend.eventHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.eventbus.Subscribe;
import de.iisys.sysint.hicumes.core.entities.*;
import de.iisys.sysint.hicumes.core.events.Events;
import de.iisys.sysint.hicumes.core.utils.database.PersistenceManager;
import de.iisys.sysint.hicumes.core.utils.exceptions.UtilsBaseException;
import de.iisys.sysint.hicumes.core.utils.exceptions.database.DatabasePersistException;
import de.iisys.sysint.hicumes.core.utils.exceptions.database.DatabaseQueryException;
import de.iisys.sysint.hicumes.core.utils.hazelcast.EventGenerator;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.entities.CamundaMachineOccupation;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.EventResult;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.ISubscribeEvent;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventCamundaFinishFormField;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventNewEntity;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventNewMachineOccupation;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.exceptions.TerminalMappingException;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.services.SubProductionStepMappingService;
import de.iisys.sysint.hicumes.mappingEngine.exceptions.mappingException.MappingException;
import de.iisys.sysint.hicumes.mappingEngine.models.mapping.MappingOutput;

import javax.ejb.EJB;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.Collectors;

public class HandleCamundaFinishFormField implements ISubscribeEvent {

    @EJB
    SubProductionStepMappingService subProductionStepMappingService;

    @Subscribe
    public void handle(EventCamundaFinishFormField eventCamundaFinishFormField) throws UtilsBaseException, TerminalMappingException, MappingException, JsonProcessingException {

        var id = eventCamundaFinishFormField.getCamundaProductionOrderId();
        var machineOccupation = persistService.getCamundaMachineOccupationById(id);

        var subMachineOccupations = machineOccupation.getMachineOccupation().getSubMachineOccupations();
        if(subMachineOccupations.size() > 0)
        {
            //Avoid ConcurrentModificationException when iterating over subMachineOccupations
            var subMachineOccupationIds = subMachineOccupations.stream().map(MachineOccupation::getId).collect(Collectors.toList());
            subMachineOccupationIds.forEach(subMachineOccupationId ->
            {
                CamundaMachineOccupation subCamundaMachineOccupation = null;
                try {
                    subCamundaMachineOccupation = persistService.getCamundaMachineOccupationByMachineOccupationId(subMachineOccupationId);
                    MappingOutput mappingOutput = null;
                    if(subCamundaMachineOccupation.getCurrentSubProductionStep().getCamundaProcessVariables() != null) {
                        mappingOutput = subProductionStepMappingService.map(eventCamundaFinishFormField.getFormFields(), mapper.readTree(subCamundaMachineOccupation.getCurrentSubProductionStep().getCamundaProcessVariables()), subCamundaMachineOccupation.getCurrentSubProductionStep().getTaskDefinitionKey());
                    }
                    else
                    {
                        mappingOutput = subProductionStepMappingService.map(eventCamundaFinishFormField.getFormFields(), subCamundaMachineOccupation.getCurrentSubProductionStep().getTaskDefinitionKey());
                    }
                    User user = null;
                    try {
                        user = persistService.getPersistenceManager().getByField(User.class, eventCamundaFinishFormField.getUserName(), "userName");
                    } catch (DatabaseQueryException e) {
                        user = new User(eventCamundaFinishFormField.getUserName());
                        persistService.getPersistenceManager().persist(user);
                    }

                    JsonNode camundaEvent = subCamundaMachineOccupation.finishSubProductionStep(eventCamundaFinishFormField.getTaskId(), eventCamundaFinishFormField.getFormFields(), eventCamundaFinishFormField.getSuspensionType(), user, mappingOutput, false);

    ///Erst hier ist der subProduction Step gespeichert
                    if (camundaEvent != null) {
                        persistService.getPersistenceManager().merge(subCamundaMachineOccupation.getCurrentSubProductionStep());
                        persistService.getPersistenceManager().merge(subCamundaMachineOccupation);

                        //Iterate through other classes that were created in formfield mapping and persist them in db
                        for (var key : PersistenceOrder.getPersistenceOrder()) {
                            if (mappingOutput.getByKey(key) instanceof ArrayList) {
                                ArrayList<EntitySuperClass> value = (ArrayList<EntitySuperClass>) mappingOutput.getByKey(key);
                                for (var singleObject : value) {
                                    if (singleObject.getClass() != SubProductionStep.class) {
                                        //System.out.println(singleObject);
                                        events.emit(new EventNewEntity(singleObject, true));
                                    }
                                }
                            }
                        }

                        //eventCamundaFinishFormField.executeCallback(new EventResult());
                        //var event = new EventGenerator().generateEvent(Events.CamundaReceiveTopic.FINISH_WITH_FORMFIELDS, camundaEvent);
                        //hazelServer.sendEvent(event);
                    }
                } catch (DatabaseQueryException | DatabasePersistException e) {
                    e.printStackTrace();
                } catch (TerminalMappingException e) {
                    e.printStackTrace();
                } catch (MappingException e) {
                    e.printStackTrace();
                } catch (JsonMappingException e) {
                    e.printStackTrace();
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            });
        }
        MappingOutput mappingOutput = null;
        if(machineOccupation.getCurrentSubProductionStep().getCamundaProcessVariables() != null) {
            mappingOutput = subProductionStepMappingService.map(eventCamundaFinishFormField.getFormFields(),  mapper.readTree(machineOccupation.getCurrentSubProductionStep().getCamundaProcessVariables()), machineOccupation.getCurrentSubProductionStep().getTaskDefinitionKey());
        }
        else
        {
            mappingOutput = subProductionStepMappingService.map(eventCamundaFinishFormField.getFormFields(),  machineOccupation.getCurrentSubProductionStep().getTaskDefinitionKey());
        }

        User user = null;
        try {
            user = persistService.getPersistenceManager().getByField(User.class, eventCamundaFinishFormField.getUserName(), "userName");
        } catch (DatabaseQueryException e) {
            user = new User(eventCamundaFinishFormField.getUserName());
            persistService.getPersistenceManager().persist(user);
        }

        JsonNode camundaEvent = machineOccupation.finishSubProductionStep(eventCamundaFinishFormField.getTaskId(), eventCamundaFinishFormField.getFormFields(), eventCamundaFinishFormField.getSuspensionType(), user, mappingOutput);

///Erst hier ist der subProduction Step gespeichert
        if (camundaEvent != null) {

            if(machineOccupation.getMachineOccupation().getUserOccupation() != null)
            {
                machineOccupation.getMachineOccupation().getUserOccupation().stopTimeRecords();
            }
            persistService.getPersistenceManager().merge(machineOccupation.getCurrentSubProductionStep());
            persistService.getPersistenceManager().merge(machineOccupation);

            //Iterate through other classes that were created in formfield mapping and persist them in db
            for (var key : PersistenceOrder.getPersistenceOrder()) {
                if (mappingOutput.getByKey(key) instanceof ArrayList) {
                    ArrayList<EntitySuperClass> value = (ArrayList<EntitySuperClass>) mappingOutput.getByKey(key);
                    for (var singleObject : value) {
                        if (singleObject.getClass() != SubProductionStep.class) {
                            //System.out.println(singleObject);
                            events.emit(new EventNewEntity(singleObject, true));
                        }
                    }
                }
            }

            eventCamundaFinishFormField.executeCallback(new EventResult());
            var event = new EventGenerator().generateEvent(Events.CamundaReceiveTopic.FINISH_WITH_FORMFIELDS, camundaEvent);
            hazelServer.sendEvent(event);
        }
    }
}
