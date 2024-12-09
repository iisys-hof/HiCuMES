package de.iisys.sysint.hicumes.mapperPlugins.outputPlugins.outputSyncProductionOrders;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.iisys.sysint.hicumes.core.entities.EntitySuperClass;
import de.iisys.sysint.hicumes.core.entities.ProductionOrder;
import de.iisys.sysint.hicumes.core.entities.jsonViews.JsonViews;
import de.iisys.sysint.hicumes.core.events.Events;
import de.iisys.sysint.hicumes.core.utils.database.PersistenceManager;
import de.iisys.sysint.hicumes.core.utils.exceptions.database.DatabaseQueryException;
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

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Extension
public class SyncProductionOrders implements IOutputWriter {

    private PersistenceManager persistenceManager;
    private HazelServer hazelServer;
    private JsonTransformer jsonTransformer = new JsonTransformer(JsonViews.SchemaMapperDefault.class);
    Logger logger;
    ObjectMapper mapper = new ObjectMapper();

    @Override
    public void writeMappingResult(MappingWorkflowResult mappingWorkflowResult, MappingAndDataSource mappingAndDataSource) {

        var configMap = mappingAndDataSource.getDataWriter().getWriterConfigMap();
        var output = mappingWorkflowResult.getMappingOutput();

        String mappingName = (String) configMap.get("MAPPING_NAME");
        String useSavedData = (String) configMap.get("USE_SAVED_DATA");
        String isSimulate = (String) configMap.get("IS_SIMULATE");
        String readerConfig = (String) configMap.get("READER_CONFIG");

        System.out.println("Running Outputplugin SyncProductionOrder");
        System.out.println(output.resultAsList());

        if(mappingWorkflowResult.getMappingLogging() != null)
        {
            System.out.println(mappingWorkflowResult.getMappingLogging().getLog());
            System.out.println(mappingWorkflowResult.getMappingLogging().getError());
        }
        for (var entityList: output.getResult().values()) {
            ArrayList<Object> value = (ArrayList<Object>) entityList;

            if(value.size() > 0)
            {

                var counterUpdated = 0;
                var counterNew = 0;
                ObjectMapper mapper = new ObjectMapper();
                for (Object singleObject : value) {

                    var obj = (EntitySuperClass) singleObject;
                    System.out.println(obj.getExternalId());
                    String versionNr = (String) reflectionGetter(obj, "versionNr");
                    try {
                        var order = persistenceManager.getByClassnameAndField(ProductionOrder.class.getSimpleName(), obj.getExternalId(),"externalId");

                        String dbVersionNr = (String) reflectionGetter(order, "versionNr");

                        System.out.println(obj.getExternalId() + ", " + versionNr + ", db: " + dbVersionNr);
                        //Check if new versionNr is different from saved one. If yes, we update the data
                        if(versionNr != null && !versionNr.equals(dbVersionNr))
                        {
                            counterUpdated++;
                            sendReportMappingRequest(mappingName, useSavedData, isSimulate, readerConfig, mapper, obj, versionNr);
                        }
                    } catch (DatabaseQueryException e) {
                        //e.printStackTrace();
                        //New BA that is not in database, so we need to add it
                        counterNew++;
                        sendReportMappingRequest(mappingName, useSavedData, isSimulate, readerConfig, mapper, obj, versionNr);
                    }


                }

                logger.logMessage("Sync stats: ", "-", "List length: " + value.size() + ", new entries: " + counterNew + ", updated entries: " + counterUpdated);
            }
        }
    }

    private void sendReportMappingRequest(String mappingName, String useSavedData, String isSimulate, String readerConfig, ObjectMapper mapper, EntitySuperClass obj, String versionNr) {
        ObjectNode runMappingNode = mapper.createObjectNode();
        runMappingNode.put("eventType", "RUN_SYNC");
        ObjectNode eventContentNode = runMappingNode.putObject("eventContent");
        eventContentNode.put("mappingName", mappingName);
        eventContentNode.put("useSavedData", useSavedData);
        eventContentNode.put("isSimulate", isSimulate);

        String readerConfigNumber = String.format(readerConfig, obj.getExternalId(), versionNr);
        eventContentNode.put("readerConfig", readerConfigNumber);
        try {
            sendEventViaHazelcast(runMappingNode);
            //WAIT FOR MAPPINGDONE
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private Object reflectionGetter(Object obj, String propertyName)
    {
        //USE Reflection to get getter for extended database field versionNr
        try {
            PropertyDescriptor pd = new PropertyDescriptor(propertyName, obj.getClass());
            Method getter = pd.getReadMethod();
            return getter.invoke(obj);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | IntrospectionException e) {
            e.printStackTrace();
            return null;
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
        formFields.add(new FormField(EFormfieldType.INPUT, "MAPPING_NAME", "Name des auszuführenden Mappings", true, null, null));
        formFields.add(new FormField(EFormfieldType.TOGGLE, "USE_SAVED_DATA", "Nur gespeicherte Daten verwenden?", false, "false", null));
        formFields.add(new FormField(EFormfieldType.TOGGLE, "IS_SIMULATE", "Keine Daten in DB speichern?", false, "false", null));
        formFields.add(new FormField(EFormfieldType.TEXTAREA, "READER_CONFIG", "Config für InputReader des auszuführenden Mappings", false, null, null));
        return formFields;
    }

    @Override
    public PluginInformation getPluginInformation() {
        PluginInformation information = new PluginInformation(getFormfields(), EPluginType.OUTPUT_WRITER, "outputPlugin-SyncProductionOrders", " - BAs synchronisieren");
        return information;
    }

    private void sendEventViaHazelcast(JsonNode event) throws IOException {
        //hazelServer.sendAndWait(Events.MappingReceiveTopic.getTopic(), event);
        hazelServer.sendEvent(Events.MappingReceiveTopic.getTopic(), event);
    }
}
