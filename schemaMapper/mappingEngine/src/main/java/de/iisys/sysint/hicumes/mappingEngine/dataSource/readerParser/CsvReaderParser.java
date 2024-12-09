package de.iisys.sysint.hicumes.mappingEngine.dataSource.readerParser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import de.iisys.sysint.hicumes.mappingEngine.exceptions.dataSourceReaderExceptions.DataSourceParserException;
import de.iisys.sysint.hicumes.mappingEngine.models.dataSource.reader.ReaderResult;
import de.iisys.sysint.hicumes.mappingEngine.models.dataSource.readerParser.CsvReaderParserConfig;
import de.iisys.sysint.hicumes.mappingEngine.models.mapping.MappingInput;

import java.util.Map;

public class CsvReaderParser implements IReaderParser {


    private CsvReaderParserConfig csvReaderParserConfig;
    ObjectMapper mapper = new ObjectMapper();
    CsvMapper csvMapper = new CsvMapper();

    public CsvReaderParser(CsvReaderParserConfig csvReaderParserConfig) {
        this.csvReaderParserConfig = csvReaderParserConfig;
    }

    @Override
    public MappingInput parse (ReaderResult result) throws DataSourceParserException {
        try {

            var separator = csvReaderParserConfig.getSeparatorChar();
            CsvSchema orderLineSchema = CsvSchema.emptySchema().withHeader().withColumnSeparator(separator);
            var orderLines = csvMapper.readerFor(JsonNode.class)
                    .with(orderLineSchema)
                    .readValues(result.getResult());

            JsonNode array = mapper.valueToTree(orderLines.readAll());
            return new MappingInput(array);

        } catch (Exception e) {
            throw new DataSourceParserException("Failed parse CSV from string with separator : " + csvReaderParserConfig.getSeparatorChar() + " and input text: " + result.getResult(), e);
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
