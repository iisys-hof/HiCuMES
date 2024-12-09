package de.iisys.sysint.hicumes.mapperPlugins.outputPlugins.outputFPDatabaseWriter;

import de.iisys.sysint.hicumes.core.entities.EntitySuperClass;
import de.iisys.sysint.hicumes.core.entities.PersistenceOrder;
import de.iisys.sysint.hicumes.core.utils.database.PersistenceManager;
import de.iisys.sysint.hicumes.core.utils.exceptions.database.DatabasePersistException;
import de.iisys.sysint.hicumes.core.utils.exceptions.json.JsonParsingUtilsException;
import de.iisys.sysint.hicumes.core.utils.hazelcast.HazelServer;
import de.iisys.sysint.hicumes.core.utils.logger.Logger;
import de.iisys.sysint.hicumes.mappingEngine.models.MappingAndDataSource;
import de.iisys.sysint.hicumes.mappingEngine.models.MappingWorkflowResult;
import de.iisys.sysint.hicumes.mappingEngine.plugins.EPluginType;
import de.iisys.sysint.hicumes.mappingEngine.plugins.PluginInformation;
import de.iisys.sysint.hicumes.mappingEngine.plugins.PluginPersistenceManager;
import de.iisys.sysint.hicumes.mappingEngine.plugins.outputPlugin.outputWriter.IOutputWriter;
import de.iisys.sysint.hicumes.mappingEngine.plugins.userInterface.EFormfieldType;
import de.iisys.sysint.hicumes.mappingEngine.plugins.userInterface.FormField;
import org.pf4j.Extension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Extension
public class FPDatabaseWriter implements IOutputWriter {

    PersistenceManager persistenceManager;
    HazelServer hazelServer;
    Logger logger;
    private PluginPersistenceManager pluginPersistenceManager;

    @Override
    public void writeMappingResult(MappingWorkflowResult mappingWorkflowResult, MappingAndDataSource mappingAndDataSource) {
        var configMap = mappingAndDataSource.getDataWriter().getWriterConfigMap();

        String JDBC_URL = (String) configMap.get("JDBC_URL");
        String USER = (String) configMap.get("USER");
        String PASSWORD = (String) configMap.get("PASSWORD");
        String DRIVER = (String) configMap.get("DRIVER");

        this.initDBConnection(JDBC_URL, USER, PASSWORD, DRIVER);
        var output = mappingWorkflowResult.getMappingOutput();
        //Iterate output and call persist

        for (var key: PersistenceOrder.getPersistenceOrder()) {
            if(output.getByKey(key) instanceof ArrayList) {
                ArrayList<Object> value = (ArrayList<Object>) output.getByKey(key);
                for (var singleObject : value) {
                    //System.out.println(singleObject);
                    try {
                        pluginPersistenceManager.persistEntitySuperClass((EntitySuperClass) singleObject, true);
                    } catch (DatabasePersistException e) {
                        //TODO ERROR HANDLING
                        e.printStackTrace();
                    }
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
        formFields.add(new FormField(EFormfieldType.INPUT, "JDBC_URL", "JDBC-URL", true, null, null));
        formFields.add(new FormField(EFormfieldType.INPUT, "USER", "Benutzername", true, null, null));
        formFields.add(new FormField(EFormfieldType.INPUT, "PASSWORD", "Passwort", true, null, null));
        formFields.add(new FormField(EFormfieldType.INPUT, "DRIVER", "Datenbank Treiber", true, null, null));
        formFields.add(new FormField(EFormfieldType.TEXTAREA, "ADDITIONAL", "Zusätzliche Informationen", false, null, null));
        return formFields;
    }

    @Override
    public PluginInformation getPluginInformation() {
        PluginInformation information = new PluginInformation(getFormfields(), EPluginType.OUTPUT_WRITER, "outputPlugin-FPDatabaseWriter", "Feinplanung Datenbank");
        return information;
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
