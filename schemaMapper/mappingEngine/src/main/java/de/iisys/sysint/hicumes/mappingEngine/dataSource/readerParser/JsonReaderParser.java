package de.iisys.sysint.hicumes.mappingEngine.dataSource.readerParser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.iisys.sysint.hicumes.mappingEngine.exceptions.dataSourceReaderExceptions.DataSourceParserException;
import de.iisys.sysint.hicumes.mappingEngine.models.dataSource.reader.ReaderResult;
import de.iisys.sysint.hicumes.mappingEngine.models.mapping.MappingInput;

import java.util.Map;

public class JsonReaderParser  implements IReaderParser {

    public MappingInput parse (ReaderResult result) throws DataSourceParserException {
        try {

            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            var inputData = mapper.readTree(result.getResult());
            return new MappingInput(inputData);

        } catch (Exception e) {
            throw new DataSourceParserException("Failed parse JSON from string: " + result.getResult(), e);
        }
    }

    @Override
    public void setConfigMap(Map<String, Object> configMap) {

    }

    @Override
    public Map<String, Object> getConfigMap() {
        return null;
    }

    @Override
    public Object getConfigByKey(String key) {
        return null;
    }

    @Override
    public void addConfig(String key, Object value) {

    }
}
