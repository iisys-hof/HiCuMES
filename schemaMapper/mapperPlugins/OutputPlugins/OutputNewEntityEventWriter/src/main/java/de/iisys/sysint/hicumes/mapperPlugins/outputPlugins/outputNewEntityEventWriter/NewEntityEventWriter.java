package de.iisys.sysint.hicumes.mapperPlugins.outputPlugins.outputNewEntityEventWriter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import de.iisys.sysint.hicumes.core.entities.EntitySuperClass;
import de.iisys.sysint.hicumes.core.entities.PersistenceOrder;
import de.iisys.sysint.hicumes.core.entities.jsonViews.JsonViews;
import de.iisys.sysint.hicumes.core.events.Events;
import de.iisys.sysint.hicumes.core.utils.database.PersistenceManager;
import de.iisys.sysint.hicumes.core.utils.exceptions.database.DatabasePersistException;
import de.iisys.sysint.hicumes.core.utils.exceptions.json.JsonParsingUtilsException;
import de.iisys.sysint.hicumes.core.utils.hazelcast.AsyncManager;
import de.iisys.sysint.hicumes.core.utils.hazelcast.EventMappingCamunda;
import de.iisys.sysint.hicumes.core.utils.hazelcast.HazelServer;
import de.iisys.sysint.hicumes.core.utils.json.JsonTransformer;
import de.iisys.sysint.hicumes.core.utils.logger.Logger;
import de.iisys.sysint.hicumes.mappingEngine.models.MappingAndDataSource;
import de.iisys.sysint.hicumes.mappingEngine.models.MappingWorkflowResult;
import de.iisys.sysint.hicumes.mappingEngine.plugins.EPluginType;
import de.iisys.sysint.hicumes.mappingEngine.plugins.PluginInformation;
import de.iisys.sysint.hicumes.mappingEngine.plugins.outputPlugin.outputWriter.IOutputWriter;
import de.iisys.sysint.hicumes.mappingEngine.plugins.userInterface.EFormfieldType;
import de.iisys.sysint.hicumes.mappingEngine.plugins.userInterface.FormField;
import org.pf4j.Extension;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Extension
public class NewEntityEventWriter implements IOutputWriter {

    private String coreDataTopic = Events.CoreDataTopic.getTopic();
    private PersistenceManager persistenceManager;
    private HazelServer hazelServer;
    private JsonTransformer jsonTransformer = new JsonTransformer(JsonViews.SchemaMapperDefault.class);
    Logger logger;
     ObjectMapper mapper = new ObjectMapper();

    @Override
    public void writeMappingResult(MappingWorkflowResult mappingWorkflowResult, MappingAndDataSource mappingAndDataSource) {

        var configMap = mappingAndDataSource.getDataWriter().getWriterConfigMap();
        var output = mappingWorkflowResult.getMappingOutput();
        //Iterate output and call persist

        var newEntityEventJson = mapper.createObjectNode();
        newEntityEventJson.put("eventType", "NEW_MULTIPLE");
        var eventContentNode = newEntityEventJson.putObject("eventContent");
        var contentArray = eventContentNode.putArray("contents");

        for (var key: PersistenceOrder.getPersistenceOrder()) {
            if(output.getByKey(key) instanceof ArrayList) {
                ArrayList<Object> value = (ArrayList<Object>) output.getByKey(key);
                for (var singleObject : value) {
                    //System.out.println(singleObject);
                    try {

                        var singleContent = mapper.createObjectNode();
                        singleContent.put("eventClass", singleObject.getClass().getSimpleName());
                        singleContent.set("classContent", jsonTransformer.transformObjectToJson(singleObject));
                        //System.out.println(newEntityEventJson);

                        if(configMap.containsKey("FORCE_UPDATE") && configMap.get("FORCE_UPDATE") != null && configMap.get("FORCE_UPDATE").equals("true"))
                        {
                            singleContent.put("forceUpdate", true);
                        }
                        contentArray.add(singleContent);
                    } catch (JsonParsingUtilsException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        try {
            sendEventViaHazelcast(newEntityEventJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setPersistenceManager(PersistenceManager persistenceManager) {
        this.persistenceManager = persistenceManager;
    }

    @Override
    public void setHazelServer(HazelServer hazelServer) {
        this.hazelServer = hazelServer;
    }

    @Override
    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    private List<FormField> getFormfields() {
        List<FormField> formFields = new ArrayList<>();
        //formFields.add(new FormField(EFormfieldType.INPUT, "DATABASE_NAME", "Datenbankname", true, "hicumes", null));
        formFields.add(new FormField(EFormfieldType.INPUT, "ENTITY_FILTER", "Entitätsfilter", true, "de.iisys.sysint.hicumes.core.entities", null));
        formFields.add(new FormField(EFormfieldType.TOGGLE, "FORCE_UPDATE", "Datenbank-Einträge überschreiben?", false, "false", null));
        return formFields;
    }

    @Override
    public PluginInformation getPluginInformation() {
        PluginInformation information = new PluginInformation(getFormfields(), EPluginType.OUTPUT_WRITER, "outputPlugin-NewEntityEventWriter", "Interne Datenbank als Event");
        return information;
    }

    private void sendEventViaHazelcast(JsonNode event) throws IOException {
        //System.out.println(hazelServer.sendAndWait(coreDataTopic, event));
        hazelServer.sendEvent(coreDataTopic, event);
    }
}
