package de.iisys.sysint.hicumes.mapperPlugins.outputPlugins.outputRequestProductionOrders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.iisys.sysint.hicumes.core.entities.EntitySuperClass;
import de.iisys.sysint.hicumes.core.entities.ProductionOrder;
import de.iisys.sysint.hicumes.core.entities.jsonViews.JsonViews;
import de.iisys.sysint.hicumes.core.events.Events;
import de.iisys.sysint.hicumes.core.utils.database.PersistenceManager;
import de.iisys.sysint.hicumes.core.utils.exceptions.database.DatabaseQueryException;
import de.iisys.sysint.hicumes.core.utils.exceptions.json.JsonParsingUtilsException;
import de.iisys.sysint.hicumes.core.utils.hazelcast.AsyncManager;
import de.iisys.sysint.hicumes.core.utils.hazelcast.EventMappingCamunda;
import de.iisys.sysint.hicumes.core.utils.hazelcast.HazelServer;
import de.iisys.sysint.hicumes.core.utils.json.JsonTransformer;
import de.iisys.sysint.hicumes.core.utils.logger.Logger;
import de.iisys.sysint.hicumes.mappingEngine.exceptions.dataSourceReaderExceptions.DataSourceReaderException;
import de.iisys.sysint.hicumes.mappingEngine.models.KeyValueConfig;
import de.iisys.sysint.hicumes.mappingEngine.models.MappingAndDataSource;
import de.iisys.sysint.hicumes.mappingEngine.models.MappingWorkflowResult;
import de.iisys.sysint.hicumes.mappingEngine.models.dataSource.reader.ReaderResult;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Extension
public class RequestProductionOrders implements IOutputWriter {

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
        String classname = (String) configMap.get("CLASSNAME");
        String searchColumn = (String) configMap.get("SEARCH_COLUMN");
        String compareValue = (String) configMap.get("COMPARE_VALUE");

        var replacements = getParameterList(configMap.get("REPLACEMENT_LIST"));



        System.out.println("Running Outputplugin SyncProductionOrder");
        if(mappingWorkflowResult.getMappingLogging() != null)
        {
            System.out.println(mappingWorkflowResult.getMappingLogging().getLog());
            System.out.println(mappingWorkflowResult.getMappingLogging().getError());
        }
        for (var entityList: output.getResult().values()) {
            ArrayList<Object> value = (ArrayList<Object>) entityList;
            if(value.size() > 0)
            {
                ObjectMapper mapper = new ObjectMapper();
                for (Object singleObject : value) {
                    //System.out.println(singleObject);

                    var obj = (EntitySuperClass) singleObject;
                    Object objectCompareValue = reflectionGetter(obj, compareValue);
                    try {
                        //var order = persistenceManager.getByClassnameAndField(ProductionOrder.class.getSimpleName(), obj.getExternalId(),"externalId");

                        Object object = null;
                        var searchValue = reflectionGetter(obj, searchColumn);
                        object = this.persistenceManager.getByClassnameAndField(classname, (String) searchValue, searchColumn);

                        //System.out.println(order);
                        Object databaseCompareValue = reflectionGetter(object, compareValue);

                        //Check if new versionNr is different from saved one. If yes, we update the data
                        if(objectCompareValue != null && !objectCompareValue.equals(databaseCompareValue))
                        {
                            sendReportMappingRequest(mappingName, useSavedData, isSimulate, readerConfig, mapper, obj, replacements);
                        }
                    } catch (DatabaseQueryException e) {
                        //e.printStackTrace();
                        //New BA that is not in database, so we need to add it
                        sendReportMappingRequest(mappingName, useSavedData, isSimulate, readerConfig, mapper, obj, replacements);
                    }


                }
            }
        }
    }

    //https://www.baeldung.com/java-string-formatting-named-placeholders
    public String stringFormat(String template, Map<String, Object> parameters) {
        StringBuilder newTemplate = new StringBuilder(template);
        List<Object> valueList = new ArrayList<>();

        Matcher matcher = Pattern.compile("[$][{](\\w+)}").matcher(template);

        while (matcher.find()) {
            String key = matcher.group(1);

            String paramName = "${" + key + "}";
            int index = newTemplate.indexOf(paramName);
            if (index != -1) {
                newTemplate.replace(index, index + paramName.length(), "%s");
                valueList.add(parameters.get(key));
            }
        }

        return String.format(newTemplate.toString(), valueList.toArray());
    }

    private void sendReportMappingRequest(String mappingName, String useSavedData, String isSimulate, String readerConfig, ObjectMapper mapper, EntitySuperClass obj, KeyValueConfig[] replacements) {
        ObjectNode runMappingNode = mapper.createObjectNode();
        runMappingNode.put("eventType", "RUN_MAPPING");
        ObjectNode eventContentNode = runMappingNode.putObject("eventContent");
        eventContentNode.put("mappingName", mappingName);
        eventContentNode.put("useSavedData", useSavedData);
        eventContentNode.put("isSimulate", isSimulate);

        Map<String, Object> stringReplacements = new HashMap<>();
        if(replacements != null) {
            for (var replacement : replacements) {

                stringReplacements.put(replacement.getConfigKey(), reflectionGetter(obj, replacement.getConfigValue()));
            }
        }
        String readerConfigModified = stringFormat(readerConfig, stringReplacements);
        eventContentNode.put("readerConfig", readerConfigModified);
        try {
            sendEventViaHazelcast(runMappingNode);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private KeyValueConfig[] getParameterList(String params) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            KeyValueConfig[] parameters = mapper.readValue(params, KeyValueConfig[].class);
            return parameters;
        }
        catch (JsonProcessingException exception)
        {
            return null;
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

        formFields.add(new FormField(EFormfieldType.INPUT, "CLASSNAME", "Klassenname", true, null, null));
        formFields.add(new FormField(EFormfieldType.INPUT, "SEARCH_COLUMN", "Spaltenname", true, "externalId", null));
        formFields.add(new FormField(EFormfieldType.INPUT, "COMPARE_VALUE", "Vergleichsvariable", true, null, null));
        formFields.add(new FormField(EFormfieldType.KEYVALUE, "REPLACEMENT_LIST", "Ersetzungsparameter hinzufügen (key = string template, value = variable", false, null, null));

        formFields.add(new FormField(EFormfieldType.INPUT, "MAPPING_NAME", "Name des auszuführenden Mappings", true, null, null));
        formFields.add(new FormField(EFormfieldType.TOGGLE, "USE_SAVED_DATA", "Nur gespeicherte Daten verwenden?", false, "false", null));
        formFields.add(new FormField(EFormfieldType.TOGGLE, "IS_SIMULATE", "Keine Daten in DB speichern?", false, "false", null));
        formFields.add(new FormField(EFormfieldType.TEXTAREA, "READER_CONFIG", "Config für InputReader des auszuführenden Mappings", false, null, null));
        return formFields;
    }

    @Override
    public PluginInformation getPluginInformation() {
        PluginInformation information = new PluginInformation(getFormfields(), EPluginType.OUTPUT_WRITER, "outputPlugin-RequestProductionOrders", "Anderes Mapping ausführen");
        return information;
    }

    private void sendEventViaHazelcast(JsonNode event) throws IOException {
        hazelServer.sendEvent(Events.RunMappingTopic.getTopic(), event);
    }
}
