package de.iisys.sysint.hicumes.mapperPlugins.inputPlugins.inputFileReader;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.iisys.sysint.hicumes.core.utils.database.PersistenceManager;
import de.iisys.sysint.hicumes.core.utils.logger.Logger;
import de.iisys.sysint.hicumes.mappingEngine.exceptions.MappingBaseException;
import de.iisys.sysint.hicumes.mappingEngine.exceptions.dataSourceReaderExceptions.DataSourceReaderException;
import de.iisys.sysint.hicumes.mappingEngine.models.KeyValueConfig;
import de.iisys.sysint.hicumes.mappingEngine.models.MappingAndDataSource;
import de.iisys.sysint.hicumes.mappingEngine.models.dataSource.reader.ReaderResult;
import de.iisys.sysint.hicumes.mappingEngine.plugins.EPluginType;
import de.iisys.sysint.hicumes.mappingEngine.plugins.PluginInformation;
import de.iisys.sysint.hicumes.mappingEngine.plugins.inputPlugin.inputReader.IInputReader;
import de.iisys.sysint.hicumes.mappingEngine.plugins.userInterface.EFormfieldType;
import de.iisys.sysint.hicumes.mappingEngine.plugins.userInterface.FormField;
import de.iisys.sysint.hicumes.mappingEngine.plugins.userInterface.SelectOptions;
import okhttp3.*;
import org.pf4j.Extension;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Extension
public class InputRestWebservice implements IInputReader {

    private PersistenceManager persistenceManager;
    Logger logger;

    @Override
    public ReaderResult readDatasource(MappingAndDataSource mappingAndDataSource) throws MappingBaseException {

        try {
            var readerResult = readDataSource(mappingAndDataSource);
            return readerResult;
        } catch (DataSourceReaderException e)
        {
            throw new DataSourceReaderException("Failed to read mappingAndDataSource!", e);
        }
    }

    @Override
    public void setPersistenceManager(PersistenceManager persistenceManager) {
        this.persistenceManager = persistenceManager;
    }

    private List<FormField> getFormfields() {
        List<FormField> formFields = new ArrayList<>();
        formFields.add(new FormField(EFormfieldType.INPUT, "URL", "Adresse", true, null, null));
        formFields.add(new FormField(EFormfieldType.SELECT, "REQUEST_TYPE", "Request-Typ", true, "GET",
                new ArrayList<>(Arrays.asList(new SelectOptions("POST", "POST"), new SelectOptions("GET", "GET")))));
        formFields.add(new FormField(EFormfieldType.KEYVALUE, "QUERY_PARAM", "Query Parameter hinzufügen", false, null, null));
        formFields.add(new FormField(EFormfieldType.KEYVALUE, "HEADER", "Header Parameter hinzufügen", false, null, null));
        formFields.add(new FormField(EFormfieldType.TEXTAREA, "BODY", "Body", false, null, null));
        formFields.add(new FormField(EFormfieldType.TEXTAREA, "ADDITIONAL", "Zusätzliche Informationen", false, null, null));
        return formFields;
    }

    @Override
    public PluginInformation getPluginInformation() {
        PluginInformation information = new PluginInformation(getFormfields(), EPluginType.INPUT_READER, "inputPlugin-RestWebservice","Aus REST Webservice einlesen");
        return information;
    }

    @Override
    public void setLogger(Logger logger) {
        this.logger = logger;
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

    private ReaderResult readDataSource(MappingAndDataSource mappingAndDataSource) throws DataSourceReaderException {
        //TODO implement post request
        var configMap = mappingAndDataSource.getDataReader().getReaderConfigMap();

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");

        RequestBody body = null;
        if(configMap.containsKey("BODY") && configMap.get("BODY") != null)
        {
            body = RequestBody.create(configMap.get("BODY"), MediaType.parse("application/json"));
        }
        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(configMap.get("URL"))).newBuilder();

        if(configMap.containsKey("QUERY_PARAM") && configMap.get("QUERY_PARAM") != null)
        {
            var params = getParameterList(configMap.get("QUERY_PARAM"));
            for (var parameter : params)
            {
                urlBuilder.addQueryParameter(parameter.getConfigKey(), parameter.getConfigValue());
            }
        }

        String url = urlBuilder.build().toString();

        var request = new Request.Builder()
                .url(url);
        if(Objects.equals(configMap.get("REQUEST_TYPE"), "GET"))
        {
            request.method(configMap.get("REQUEST_TYPE"), null);
        }
        else
        {
            request.method(configMap.get("REQUEST_TYPE"), body);
        }

        if(configMap.containsKey("HEADER") && configMap.get("HEADER") != null)
        {
            var headers = getParameterList(configMap.get("HEADER"));
            for (var header : headers) {
                request.addHeader(header.getConfigKey(), header.getConfigValue());
            }
        }

        try {
            Response response = client.newCall(request.build()).execute();
            if(configMap.containsKey("ADDITIONAL") && configMap.get("ADDITIONAL") != null)
            {
                return new ReaderResult(response.body().string(), configMap.get("ADDITIONAL"));
            }
            return new ReaderResult(response.body().string());
        }
        catch (IOException exception)
        {
            throw new DataSourceReaderException("Failed to send http request: " + request, exception);
        }
    }
}
