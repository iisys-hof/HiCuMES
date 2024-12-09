package de.iisys.sysint.hicumes.mappingEngine.plugins.inputPlugin.inputReader;

import de.iisys.sysint.hicumes.core.utils.database.PersistenceManager;
import de.iisys.sysint.hicumes.core.utils.logger.Logger;
import de.iisys.sysint.hicumes.mappingEngine.exceptions.MappingBaseException;
import de.iisys.sysint.hicumes.mappingEngine.exceptions.dataSourceReaderExceptions.DataSourceReaderException;
import de.iisys.sysint.hicumes.mappingEngine.models.MappingAndDataSource;
import de.iisys.sysint.hicumes.mappingEngine.models.dataSource.reader.ReaderResult;
import de.iisys.sysint.hicumes.mappingEngine.plugins.IPluginInformationProvider;
import org.pf4j.ExtensionPoint;

public interface IInputReader extends IPluginInformationProvider, ExtensionPoint {
    public ReaderResult readDatasource(MappingAndDataSource mappingAndDataSource) throws DataSourceReaderException, MappingBaseException;
    public void setPersistenceManager(PersistenceManager persistenceManager);
    public void setLogger(Logger logger);
}
