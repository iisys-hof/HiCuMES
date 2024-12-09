package de.iisys.sysint.hicumes.manufacturingTerminalBackend.eventHandler;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.eventbus.Subscribe;
import de.iisys.sysint.hicumes.core.entities.EntitySuperClass;
import de.iisys.sysint.hicumes.core.entities.PersistenceOrder;
import de.iisys.sysint.hicumes.core.entities.SubProductionStep;
import de.iisys.sysint.hicumes.core.entities.User;
import de.iisys.sysint.hicumes.core.events.Events;
import de.iisys.sysint.hicumes.core.utils.exceptions.UtilsBaseException;
import de.iisys.sysint.hicumes.core.utils.exceptions.database.DatabaseQueryException;
import de.iisys.sysint.hicumes.core.utils.hazelcast.EventGenerator;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBackgroundProcessingDone;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.EventResult;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.ISubscribeEvent;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventCamundaFinishFormField;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventCamundaPersistFormField;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventNewEntity;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.exceptions.TerminalMappingException;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.manager.SSESessionManager;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.services.SubProductionStepMappingService;
import de.iisys.sysint.hicumes.mappingEngine.exceptions.mappingException.MappingException;

import javax.ejb.EJB;
import java.util.ArrayList;

public class HandleCamundaPersistFormField implements ISubscribeEvent {

    @EJB
    SubProductionStepMappingService subProductionStepMappingService;

    SSESessionManager sessionManager = SSESessionManager.getInstance();

    @Subscribe
    public void handle(EventCamundaPersistFormField eventCamundaPersistFormField) throws UtilsBaseException, TerminalMappingException, MappingException {

        var parentId = eventCamundaPersistFormField.getParentCamundaMOId();
        var subMachineOccupationId = eventCamundaPersistFormField.getSubMachineOccupationId();

        var parentMachineOccupation = persistService.getCamundaMachineOccupationById(parentId);
        var subMachineOccupation = persistService.getCamundaMachineOccupationByMachineOccupationId(subMachineOccupationId);

        var mappingOutput =  subProductionStepMappingService.map(eventCamundaPersistFormField.getFormFields(), parentMachineOccupation.getCurrentSubProductionStep().getTaskDefinitionKey());
        User user = null;
        try
        {
            user = persistService.getPersistenceManager().getByField(User.class, eventCamundaPersistFormField.getUserName(), "userName");
        }
        catch (DatabaseQueryException e)
        {
            user = new User(eventCamundaPersistFormField.getUserName());
            persistService.getPersistenceManager().persist(user);
        }

        JsonNode camundaEvent = subMachineOccupation.persistSubProductionStep(parentMachineOccupation, eventCamundaPersistFormField.getFormFields(), user, mappingOutput);
        if(!subMachineOccupation.getMachineOccupation().getSubProductionSteps().contains(subMachineOccupation.getCurrentSubProductionStep().getSubProductionStep()))
        {
            subMachineOccupation.getMachineOccupation().subProductionStepsAdd(subMachineOccupation.getCurrentSubProductionStep().getSubProductionStep());
        }
        ///Erst hier ist der subProduction Step gespeichert
        if(camundaEvent != null) {
            persistService.getPersistenceManager().merge(subMachineOccupation);
             //Iterate through other classes that were created in formfield mapping and persist them in db
            for (var key: PersistenceOrder.getPersistenceOrder()) {
                if( mappingOutput.getByKey(key) instanceof ArrayList) {
                    ArrayList<EntitySuperClass> value = (ArrayList<EntitySuperClass>) mappingOutput.getByKey(key);
                        for (var singleObject : value) {
                            if(singleObject.getClass() != SubProductionStep.class) {
                            //System.out.println(singleObject);
                            events.emit(new EventNewEntity(singleObject));
                        }
                    }
                }
            }
            var test = persistService.getCamundaMachineOccupationById(parentMachineOccupation.getId());
            persistService.getPersistenceManager().getEntityManager().refresh(test);
            sessionManager.broadcast(new EventBackgroundProcessingDone(eventCamundaPersistFormField, test));
        }


    }
}
