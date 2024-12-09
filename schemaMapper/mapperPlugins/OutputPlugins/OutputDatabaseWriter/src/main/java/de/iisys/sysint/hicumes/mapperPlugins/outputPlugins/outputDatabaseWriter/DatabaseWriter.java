package de.iisys.sysint.hicumes.mapperPlugins.outputPlugins.outputDatabaseWriter;

import de.iisys.sysint.hicumes.core.entities.EntitySuperClass;
import de.iisys.sysint.hicumes.core.utils.database.PersistenceManager;
import de.iisys.sysint.hicumes.core.utils.exceptions.database.DatabasePersistException;
import de.iisys.sysint.hicumes.core.utils.hazelcast.HazelServer;
import de.iisys.sysint.hicumes.core.utils.logger.Logger;
import de.iisys.sysint.hicumes.mappingEngine.models.MappingAndDataSource;
import de.iisys.sysint.hicumes.mappingEngine.models.MappingWorkflowResult;
import de.iisys.sysint.hicumes.mappingEngine.plugins.EPluginType;
import de.iisys.sysint.hicumes.mappingEngine.plugins.PluginInformation;
import de.iisys.sysint.hicumes.mappingEngine.plugins.outputPlugin.outputWriter.IOutputWriter;
import de.iisys.sysint.hicumes.mappingEngine.plugins.userInterface.EFormfieldType;
import de.iisys.sysint.hicumes.mappingEngine.plugins.userInterface.FormField;
import org.pf4j.Extension;

import java.util.ArrayList;
import java.util.List;

@Extension
public class DatabaseWriter implements IOutputWriter {

    PersistenceManager persistenceManager;
    HazelServer hazelServer;
    Logger logger;

    @Override
    public void writeMappingResult(MappingWorkflowResult mappingWorkflowResult, MappingAndDataSource mappingAndDataSource) {
        var output = mappingWorkflowResult.getMappingOutput();
        //Iterate output and call persist
        for (var singleObject: output.resultAsList()) {
            try {
                persistenceManager.persistEntitySuperClass((EntitySuperClass) singleObject);
            } catch (DatabasePersistException e) {
                //TODO ERROR HANDLING
                e.printStackTrace();
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
        formFields.add(new FormField(EFormfieldType.INPUT, "DATABASE_NAME", "Datenbankname", true, "hicumes", null));
        formFields.add(new FormField(EFormfieldType.INPUT, "ENTITY_FILTER", "Entitätsfilter", true, "de.iisys.sysint.hicumes.core.entities", null));
        return formFields;
    }

    @Override
    public PluginInformation getPluginInformation() {
        PluginInformation information = new PluginInformation(getFormfields(), EPluginType.OUTPUT_WRITER, "outputPlugin-DatabaseWriter", "Interne Datenbank");
        return information;
    }
}
