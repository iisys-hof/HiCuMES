package de.iisys.sysint.hicumes.mapperPlugins.outputPlugins.outputRestWriter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.iisys.sysint.hicumes.core.entities.EntitySuperClass;
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
import de.iisys.sysint.hicumes.mappingEngine.exceptions.MappingBaseException;
import de.iisys.sysint.hicumes.mappingEngine.exceptions.dataSourceReaderExceptions.DataSourceReaderException;
import de.iisys.sysint.hicumes.mappingEngine.exceptions.mappingException.MappingException;
import de.iisys.sysint.hicumes.mappingEngine.models.KeyValueConfig;
import de.iisys.sysint.hicumes.mappingEngine.models.MappingAndDataSource;
import de.iisys.sysint.hicumes.mappingEngine.models.MappingWorkflowResult;
import de.iisys.sysint.hicumes.mappingEngine.models.dataSource.reader.ReaderResult;
import de.iisys.sysint.hicumes.mappingEngine.plugins.EPluginType;
import de.iisys.sysint.hicumes.mappingEngine.plugins.PluginInformation;
import de.iisys.sysint.hicumes.mappingEngine.plugins.outputPlugin.outputWriter.IOutputWriter;
import de.iisys.sysint.hicumes.mappingEngine.plugins.userInterface.EFormfieldType;
import de.iisys.sysint.hicumes.mappingEngine.plugins.userInterface.FormField;
import de.iisys.sysint.hicumes.mappingEngine.plugins.userInterface.SelectOptions;
import okhttp3.*;
import org.pf4j.Extension;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Extension
public class RestWriter implements IOutputWriter {

    private PersistenceManager persistenceManager;
    private HazelServer hazelServer;
    Logger logger;

    @Override
    public void writeMappingResult(MappingWorkflowResult mappingWorkflowResult, MappingAndDataSource mappingAndDataSource) throws MappingException {

        ObjectMapper mapper = new ObjectMapper();
        var output = (String) mappingWorkflowResult.getMappingOutput().getByKey("EXPORT_JSON");
        JsonNode outputArrayNode = null;
        try {
            outputArrayNode  = mapper.readTree(output);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        var configMap = mappingAndDataSource.getDataWriter().getWriterConfigMap();

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");

        RequestBody body = null;
        if(outputArrayNode != null)
        {
            for (JsonNode outputNode : outputArrayNode)
            {
                if (configMap.containsKey("RESULT_AS_BODY") && configMap.get("RESULT_AS_BODY") != null && configMap.get("RESULT_AS_BODY").equals("true")) {
                    //var dings = outputNode.toString();
                    body = RequestBody.create(outputNode.toString(), MediaType.parse("application/json"));
                }
                HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(configMap.get("URL"))).newBuilder();

                if (configMap.containsKey("QUERY_PARAM") && configMap.get("QUERY_PARAM") != null) {
                    var params = getParameterList(configMap.get("QUERY_PARAM"));
                    for (var parameter : params) {
                        urlBuilder.addQueryParameter(parameter.getConfigKey(), parameter.getConfigValue());
                    }
                }

                String url = urlBuilder.build().toString();

                var request = new Request.Builder()
                        .url(url);
                if (Objects.equals(configMap.get("REQUEST_TYPE"), "GET")) {
                    request.method(configMap.get("REQUEST_TYPE"), null);
                } else {
                    request.method(configMap.get("REQUEST_TYPE"), body);
                }

                if (configMap.containsKey("HEADER") && configMap.get("HEADER") != null) {
                    var headers = getParameterList(configMap.get("HEADER"));
                    for (var header : headers) {
                        request.addHeader(header.getConfigKey(), header.getConfigValue());
                    }
                }

                try {
                    logger.logMessage("REQUEST Header: ", "-", url);
                    logger.logMessage("REQUEST Body: ", "-", outputNode.toString());
                    Response response = client.newCall(request.build()).execute();
                    logger.logMessage("RESPONSE Body: ", "-", response.body().string());
                    //System.out.println(response.body().string());
                } catch (IOException exception) {
                    logger.logMessage("Failed to send http request: ", "-", exception.getMessage(), Arrays.toString(exception.getStackTrace()));
                    //throw new MappingException("Failed to send http request: " + request, exception);
                }
            }
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
        formFields.add(new FormField(EFormfieldType.INPUT, "URL", "Adresse", true, null, null));
        formFields.add(new FormField(EFormfieldType.SELECT, "REQUEST_TYPE", "Request-Typ", true, "POST",
                new ArrayList<>(Arrays.asList(new SelectOptions("GET", "GET"), new SelectOptions("POST", "POST"), new SelectOptions("PUT", "PUT"), new SelectOptions("DELETE", "DELETE")))));
        formFields.add(new FormField(EFormfieldType.KEYVALUE, "QUERY_PARAM", "Query Parameter hinzufügen", false, null, null));
        formFields.add(new FormField(EFormfieldType.KEYVALUE, "HEADER", "Header Parameter hinzufügen", false, null, null));
        formFields.add(new FormField(EFormfieldType.TOGGLE, "RESULT_AS_BODY", "Mapping Ergebnis als JSON im Body senden?", false, "true", null));
        return formFields;
    }

    @Override
    public PluginInformation getPluginInformation() {
        PluginInformation information = new PluginInformation(getFormfields(), EPluginType.OUTPUT_WRITER, "outputPlugin-RestWriter", "An einen Rest-Endpoint senden");
        return information;
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
}
