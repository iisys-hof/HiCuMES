package de.iisys.sysint.hicumes.manufacturingTerminalBackend.hazelcast;

import com.fasterxml.jackson.databind.JsonNode;
//import com.hazelcast.core.Message;
//import com.hazelcast.core.MessageListener;
import de.iisys.sysint.hicumes.core.entities.EntitySuperClass;
import de.iisys.sysint.hicumes.core.entities.MachineOccupation;
import com.hazelcast.topic.Message;
import com.hazelcast.topic.MessageListener;
import de.iisys.sysint.hicumes.core.entities.enums.EMachineOccupationStatus;
import de.iisys.sysint.hicumes.core.entities.jsonViews.JsonViews;
import de.iisys.sysint.hicumes.core.events.Events;
import de.iisys.sysint.hicumes.core.utils.exceptions.UtilsBaseException;
import de.iisys.sysint.hicumes.core.utils.exceptions.database.DatabasePersistException;
import de.iisys.sysint.hicumes.core.utils.hazelcast.EventDestructor;
import de.iisys.sysint.hicumes.core.utils.hazelcast.EventGenerator;
import de.iisys.sysint.hicumes.core.utils.json.JsonTransformer;
import de.iisys.sysint.hicumes.core.utils.logger.Logger;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventArchiveMachineOccupations;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.IEmitEvent;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.IStaticDependencies;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventCamundaProcessStarted;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventNewEntity;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventNewMachineOccupation;

import javax.inject.Inject;

public class CoreDataHazelListener implements MessageListener<JsonNode>, IStaticDependencies
{

    static private final String eventClass = "eventClass";
    static private final String classContent = "classContent";
    static private final String forceUpdateField = "forceUpdate";
    private final Logger logger = Logger.getInstance(this.getClass().getName(), this.getClass().getSimpleName());

    private final JsonTransformer jsonTransformer = new JsonTransformer(JsonViews.ViewMachineOccupation.class);

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void onMessage(Message<JsonNode> message) {
        JsonNode json = message.getMessageObject();
        //logger.logMessage("RECEIVED CORE DATA: ", ">", json.toPrettyString());
        var event = new EventDestructor(json);

        Events.CoreDataTopic eventType = event.getCoreDataEvent();
        JsonNode contentNode = event.getEventPayload();

        switch (eventType) {
            case NEW: {
                String className = contentNode.get(eventClass).textValue();
                String content = contentNode.get(classContent).toString();
                boolean forceUpdate = false;
                if (contentNode.get(forceUpdateField) != null) {
                    forceUpdate = contentNode.get(forceUpdateField).asBoolean();
                }
                try {
                    var generatedClass = jsonTransformer.readValueFromEntitySuperClass(content, className);
                    //System.out.println(events);
                    //System.out.println("events");
                    //events.emit(new EventNewEntity(generatedClass, forceUpdate));
                    var eventNewEntity = new EventNewEntity(generatedClass, forceUpdate);
                    var isNewPersistedEntity = persistEntityToDatabase(eventNewEntity);
                    EntitySuperClass entity = eventNewEntity.getEntity();

                    if (entity instanceof MachineOccupation && isNewPersistedEntity) {
                        events.emit(new EventNewMachineOccupation((MachineOccupation) entity, eventNewEntity.isCamundaSubMachineOccupation(), false));
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
                } catch (UtilsBaseException e) {
                    e.printStackTrace();
                }
                break;
            }

            case NEW_MULTIPLE: {
                    var arrayNode = contentNode.get("contents");
                    if (arrayNode.isArray()) {
                        for (JsonNode node : arrayNode) {
                            String className = node.get(eventClass).textValue();
                            String content = node.get(classContent).toString();
                            boolean forceUpdate = false;
                            if (node.get(forceUpdateField) != null) {
                                forceUpdate = node.get(forceUpdateField).asBoolean();
                            }
                            try {
                                var generatedClass = jsonTransformer.readValueFromEntitySuperClass(content, className);
                                //System.out.println(events);
                                //System.out.println("events");
                                //events.emit(new EventNewEntity(generatedClass, forceUpdate));
                                var eventNewEntity = new EventNewEntity(generatedClass, forceUpdate);
                                var isNewPersistedEntity = persistEntityToDatabase(eventNewEntity);
                                EntitySuperClass entity = eventNewEntity.getEntity();

                                if (entity instanceof MachineOccupation && isNewPersistedEntity) {
                                    events.emit(new EventNewMachineOccupation((MachineOccupation) entity, eventNewEntity.isCamundaSubMachineOccupation(), false));
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

                            } catch (UtilsBaseException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    //var eventResponse = new EventGenerator().generateEvent(Events.MappingReceiveTopic.NEW_MULTIPLE_DONE,  json);
                    //hazelServer.sendEvent(eventResponse);
                break;
            }

            case ARCHIVE_FINISHED: {
                var archiveFinished = new EventArchiveMachineOccupations(contentNode.get("daysAgo").asInt(7));
                events.emit(archiveFinished);
                break;
            }
        }
    }

    private boolean persistEntityToDatabase(EventNewEntity eventNewEntity) throws DatabasePersistException {

        return coreDataPersistenceManager.persistEntitySuperClass(eventNewEntity.getEntity(), eventNewEntity.isForceUpdate());
    }
}
