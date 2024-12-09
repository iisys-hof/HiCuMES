package XMLPlugin;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.iisys.sysint.hicumes.mappingEngine.models.MappingAndDataSource;
import de.iisys.sysint.hicumes.mappingEngine.plugins.EPluginType;
import de.iisys.sysint.hicumes.mappingEngine.plugins.PluginInformation;
import de.iisys.sysint.hicumes.mappingEngine.plugins.parserPlugin.dataParser.IDataParser;
import de.iisys.sysint.hicumes.mappingEngine.exceptions.dataSourceReaderExceptions.DataSourceParserException;
import de.iisys.sysint.hicumes.mappingEngine.models.dataSource.reader.ReaderResult;
import de.iisys.sysint.hicumes.mappingEngine.models.mapping.MappingInput;
import de.iisys.sysint.hicumes.mappingEngine.plugins.userInterface.FormField;
import org.json.JSONObject;
import org.json.XML;
import org.pf4j.Extension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Extension
public class ParserPluginXML implements IDataParser {
    private Map<String, String> configMap = new HashMap<>();

    @Override
    public MappingInput parse(ReaderResult result, MappingAndDataSource mappingAndDataSource) throws DataSourceParserException {
        System.out.println("Using XML Parser Plugin for parsing");
        JSONObject javaJson = XML.toJSONObject(result.getResult());
        try {
            var inputData = new ObjectMapper().readTree(javaJson.toString());
            return new MappingInput(inputData);
        } catch (Exception e) {
            throw new DataSourceParserException("Failed parse XML to JSON: " + result.getResult(), e);
        }
    }

    private List<FormField> getFormfields() {
        return null;
    }

    @Override
    public PluginInformation getPluginInformation() {
        PluginInformation information = new PluginInformation(getFormfields(), EPluginType.INPUT_PARSER, "parserPlugin-XML", "XML");
        return information;
    }
}
