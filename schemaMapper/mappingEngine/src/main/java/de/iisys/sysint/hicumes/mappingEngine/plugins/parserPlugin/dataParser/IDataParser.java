package de.iisys.sysint.hicumes.mappingEngine.plugins.parserPlugin.dataParser;

import de.iisys.sysint.hicumes.mappingEngine.exceptions.dataSourceReaderExceptions.DataSourceParserException;
import de.iisys.sysint.hicumes.mappingEngine.models.MappingAndDataSource;
import de.iisys.sysint.hicumes.mappingEngine.models.dataSource.reader.ReaderResult;
import de.iisys.sysint.hicumes.mappingEngine.models.mapping.MappingInput;
import de.iisys.sysint.hicumes.mappingEngine.plugins.IPluginInformationProvider;
import org.pf4j.ExtensionPoint;

public interface IDataParser extends IPluginInformationProvider, ExtensionPoint {
    MappingInput parse(ReaderResult result, MappingAndDataSource mappingAndDataSource) throws DataSourceParserException;
}
