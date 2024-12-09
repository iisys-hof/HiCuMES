package de.iisys.sysint.hicumes.mapperPlugins.inputPlugins.inputSQLDatabase;

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
import org.pf4j.Extension;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Extension
public class InputSQLDatabase implements IInputReader {

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
        formFields.add(new FormField(EFormfieldType.SELECT, "DATABASE_TYPE", "Datenbank-Typ", true, "mysql",
                new ArrayList<>(Arrays.asList(new SelectOptions("MySQL", "mysql"), new SelectOptions("PostgreSQL", "postgresql"),
                        new SelectOptions("Oracle", "oracle"), new SelectOptions("MS SQL", "sqlserver")))));
        formFields.add(new FormField(EFormfieldType.INPUT, "URL", "Adresse", true, null, null));
        formFields.add(new FormField(EFormfieldType.INPUT, "PORT", "Port", true, null, null));
        formFields.add(new FormField(EFormfieldType.INPUT, "DATABASE", "Datenbankname", true, null, null));
        formFields.add(new FormField(EFormfieldType.INPUT, "USER", "User", true, null, null));
        formFields.add(new FormField(EFormfieldType.INPUT, "PASSWORD", "Password", true, null, null));

        formFields.add(new FormField(EFormfieldType.TEXTAREA, "QUERY", "DB Query", true, null, null));
        return formFields;
    }

    @Override
    public PluginInformation getPluginInformation() {
        PluginInformation information = new PluginInformation(getFormfields(), EPluginType.INPUT_READER, "inputPlugin-SQLDatabase","Aus SQL Datenbank einlesen");
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
        var configMap = mappingAndDataSource.getDataReader().getReaderConfigMap();
        String jdbcUrl = null, url = null, port = null, database = null, user = null, password = null, query = null;
        if(configMap.containsKey("URL") && configMap.get("URL") != null)
        {
            url = configMap.get("URL");
        }
        if(configMap.containsKey("PORT") && configMap.get("PORT") != null)
        {
            port = configMap.get("PORT");
        }
        if(configMap.containsKey("DATABASE") && configMap.get("DATABASE") != null)
        {
            database = configMap.get("DATABASE");
        }
        if(configMap.containsKey("USER") && configMap.get("USER") != null)
        {
            user = configMap.get("USER");
        }
        if(configMap.containsKey("PASSWORD") && configMap.get("PASSWORD") != null)
        {
            password = configMap.get("PASSWORD");
        }
        if(configMap.containsKey("QUERY") && configMap.get("QUERY") != null)
        {
            query = configMap.get("QUERY");
        }
        if(configMap.containsKey("DATABASE_TYPE") && configMap.get("DATABASE_TYPE") != null)
        {
            if(url != null && port != null && database != null)
            {
                switch (configMap.get("DATABASE_TYPE"))
                {
                    case "mysql":
                    {
                        jdbcUrl = "jdbc:mysql://" + url + ":" + port + "/" + database;
                        break;
                    }
                    case "postgresql":
                    {
                        jdbcUrl = "jdbc:postgresql://" + url + ":" + port + "/" + database;;
                        break;
                    }
                    case "oracle":
                    {
                        jdbcUrl = "jdbc:oracle:thin:@" + url + ":" + port + ":" + database;
                        break;
                    }
                    case "sqlserver":
                    {
                        jdbcUrl = "jdbc:sqlserver://" + url + ":" + port + ";databaseName=" + database;
                        break;
                    }
                }
            }
        }

        try {
            // Establish a connection
            Connection connection = null;
            if (jdbcUrl != null) {
                connection = DriverManager.getConnection(jdbcUrl, user, password);
            }
            else
            {
                return null;
            }

            // Create a PreparedStatement
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();
            List<Map<String, Object>> results = new ArrayList<>();

            while (resultSet.next()) {
                Map<String, Object> row = new HashMap<>();
                // Retrieve column names from metadata and populate the map
                int columnCount = resultSet.getMetaData().getColumnCount();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = resultSet.getMetaData().getColumnName(i);
                    Object columnValue = resultSet.getObject(i);
                    row.put(columnName, columnValue);
                }
                results.add(row);
            }

            // Convert the list of results to JSON
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(results);
            String result = "{\"queryResult\": " + json + "}";
            resultSet.close();
            preparedStatement.close();
            connection.close();
            return new ReaderResult(result);
        } catch (SQLException | JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
