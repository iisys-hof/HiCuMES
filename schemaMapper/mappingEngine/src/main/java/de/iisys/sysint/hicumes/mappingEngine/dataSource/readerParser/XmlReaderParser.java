package de.iisys.sysint.hicumes.mappingEngine.dataSource.readerParser;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.iisys.sysint.hicumes.mappingEngine.exceptions.dataSourceReaderExceptions.DataSourceParserException;
import de.iisys.sysint.hicumes.mappingEngine.models.dataSource.reader.ReaderResult;
import de.iisys.sysint.hicumes.mappingEngine.models.mapping.MappingInput;
import org.json.JSONObject;
import org.json.XML;

import javax.ejb.Singleton;
import java.util.Map;

@Singleton
public class XmlReaderParser implements IReaderParser {

    public MappingInput parse (ReaderResult result) throws DataSourceParserException {
        JSONObject javaJson = XML.toJSONObject(result.getResult());
        try {
            var inputData = new ObjectMapper().readTree(javaJson.toString());
            return new MappingInput(inputData);
        } catch (Exception e) {
            throw new DataSourceParserException("Failed parse CSV to JSON: " + result.getResult(), e);
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
