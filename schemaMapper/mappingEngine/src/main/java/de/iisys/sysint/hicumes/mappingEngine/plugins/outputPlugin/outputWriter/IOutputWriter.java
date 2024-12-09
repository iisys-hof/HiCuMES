package de.iisys.sysint.hicumes.mappingEngine.plugins.outputPlugin.outputWriter;

import de.iisys.sysint.hicumes.core.utils.database.PersistenceManager;
import de.iisys.sysint.hicumes.core.utils.hazelcast.HazelServer;
import de.iisys.sysint.hicumes.core.utils.logger.Logger;
import de.iisys.sysint.hicumes.mappingEngine.exceptions.mappingException.MappingException;
import de.iisys.sysint.hicumes.mappingEngine.models.MappingAndDataSource;
import de.iisys.sysint.hicumes.mappingEngine.models.MappingWorkflowResult;
import de.iisys.sysint.hicumes.mappingEngine.plugins.IPluginInformationProvider;
import org.pf4j.ExtensionPoint;

public interface IOutputWriter extends IPluginInformationProvider, ExtensionPoint {
    public void writeMappingResult(MappingWorkflowResult mappingWorkflowResult, MappingAndDataSource mappingAndDataSource) throws MappingException;
    public void setPersistenceManager(PersistenceManager persistenceManager);
    public void setHazelServer(HazelServer hazelServer);
    public void setLogger(Logger logger);
}
