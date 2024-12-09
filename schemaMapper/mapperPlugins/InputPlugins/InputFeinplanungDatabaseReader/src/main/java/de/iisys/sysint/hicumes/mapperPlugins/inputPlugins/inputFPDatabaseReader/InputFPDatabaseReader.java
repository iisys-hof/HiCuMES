package de.iisys.sysint.hicumes.mapperPlugins.inputPlugins.inputFPDatabaseReader;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.iisys.sysint.hicumes.core.entities.MachineOccupation;
import de.iisys.sysint.hicumes.core.entities.jsonViews.JsonViews;
import de.iisys.sysint.hicumes.core.utils.database.PersistenceManager;
import de.iisys.sysint.hicumes.core.utils.exceptions.json.JsonParsingUtilsException;
import de.iisys.sysint.hicumes.core.utils.json.JsonTransformer;
import de.iisys.sysint.hicumes.core.utils.logger.Logger;
import de.iisys.sysint.hicumes.mappingEngine.exceptions.MappingBaseException;
import de.iisys.sysint.hicumes.mappingEngine.exceptions.dataSourceReaderExceptions.DataSourceReaderException;
import de.iisys.sysint.hicumes.mappingEngine.models.KeyValueConfig;
import de.iisys.sysint.hicumes.mappingEngine.models.MappingAndDataSource;
import de.iisys.sysint.hicumes.mappingEngine.models.dataSource.reader.ReaderResult;
import de.iisys.sysint.hicumes.mappingEngine.plugins.EPluginType;
import de.iisys.sysint.hicumes.mappingEngine.plugins.PluginInformation;
import de.iisys.sysint.hicumes.mappingEngine.plugins.PluginPersistenceManager;
import de.iisys.sysint.hicumes.mappingEngine.plugins.inputPlugin.inputReader.IInputReader;
import de.iisys.sysint.hicumes.mappingEngine.plugins.userInterface.EFormfieldType;
import de.iisys.sysint.hicumes.mappingEngine.plugins.userInterface.FormField;
import org.pf4j.Extension;

import java.util.*;

@Extension
public class InputFPDatabaseReader implements IInputReader {

    private PersistenceManager persistenceManager;
    private JsonTransformer jsonTransformer = new JsonTransformer(JsonViews.ViewMachineOccupationParent.class);
    Logger logger;
    private PluginPersistenceManager pluginPersistenceManager;

    @Override
    public ReaderResult readDatasource(MappingAndDataSource mappingAndDataSource) throws MappingBaseException {
        var configMap = mappingAndDataSource.getDataReader().getReaderConfigMap();

        String JDBC_URL = (String) configMap.get("JDBC_URL");
        String USER = (String) configMap.get("USER");
        String PASSWORD = (String) configMap.get("PASSWORD");
        String DRIVER = (String) configMap.get("DRIVER");

        this.initDBConnection(JDBC_URL, USER, PASSWORD, DRIVER);
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
    }

    private List<FormField> getFormfields() {
        List<FormField> formFields = new ArrayList<>();
        formFields.add(new FormField(EFormfieldType.INPUT, "JDBC_URL", "JDBC-URL", true, null, null));
        formFields.add(new FormField(EFormfieldType.INPUT, "USER", "Benutzername", true, null, null));
        formFields.add(new FormField(EFormfieldType.INPUT, "PASSWORD", "Passwort", true, null, null));
        formFields.add(new FormField(EFormfieldType.INPUT, "DRIVER", "Datenbank Treiber", true, null, null));
        formFields.add(new FormField(EFormfieldType.TEXTAREA, "ADDITIONAL", "Zusätzliche Informationen", false, null, null));
        return formFields;
    }

    @Override
    public PluginInformation getPluginInformation() {
        PluginInformation information = new PluginInformation(getFormfields(), EPluginType.INPUT_READER, "inputPlugin-FPDatabaseReader","Aus Feinplanung einlesen");
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

        List<MachineOccupation> mocs = this.pluginPersistenceManager.getCurrentMoc();
        String json = null;
        if(mocs != null && !mocs.isEmpty())
        {
            try {
                json = jsonTransformer.writeValueAsString(mocs);
            }
            catch (JsonParsingUtilsException e) {
                throw new RuntimeException(e);
            }
        }
        if (configMap.containsKey("ADDITIONAL") && configMap.get("ADDITIONAL") != null) {
                return new ReaderResult(json, configMap.get("ADDITIONAL"));
        }
        else {
            return new ReaderResult(json, null);
        }
    }

    private void initDBConnection(String jdbcUrl, String user, String password, String driver){

        this.pluginPersistenceManager = new PluginPersistenceManager(logger);

        // Configuration for the database connection
        Map<String, String> properties = new HashMap<>();
//        properties.put("javax.persistence.jdbc.url", "jdbc:mysql://localhost:3306/hicumesFP");
//        properties.put("javax.persistence.jdbc.user", "root");
//        properties.put("javax.persistence.jdbc.password", "IuyS2015");
//        properties.put("javax.persistence.jdbc.driver", "com.mysql.cj.jdbc.Driver");
        properties.put("javax.persistence.jdbc.url", jdbcUrl);
        properties.put("javax.persistence.jdbc.user", user);
        properties.put("javax.persistence.jdbc.password", password);
        properties.put("javax.persistence.jdbc.driver", driver);

        // Set up EntityManager using a specific persistence unit
        pluginPersistenceManager.configureEntityManager("pluginPersistenceUnit", properties);

    }
}
