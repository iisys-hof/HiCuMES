package de.iisys.sysint.hicumes.mappingEngine.dataSource.readerParser;

import de.iisys.sysint.hicumes.mappingEngine.exceptions.dataSourceReaderExceptions.DataSourceParserException;
import de.iisys.sysint.hicumes.mappingEngine.models.dataSource.reader.ReaderResult;
import de.iisys.sysint.hicumes.mappingEngine.models.mapping.MappingInput;
import org.pf4j.ExtensionPoint;

import java.util.Map;

public interface IReaderParser extends ExtensionPoint {
    MappingInput parse (ReaderResult result) throws DataSourceParserException;
    void setConfigMap(Map<String, Object> configMap);
    Map<String, Object> getConfigMap();
    Object getConfigByKey(String key);
    void addConfig(String key, Object value);
}
