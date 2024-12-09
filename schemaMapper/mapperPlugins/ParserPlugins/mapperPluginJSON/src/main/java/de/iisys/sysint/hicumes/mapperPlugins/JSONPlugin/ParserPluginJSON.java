package de.iisys.sysint.hicumes.mapperPlugins.JSONPlugin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.iisys.sysint.hicumes.mappingEngine.models.MappingAndDataSource;
import de.iisys.sysint.hicumes.mappingEngine.plugins.EPluginType;
import de.iisys.sysint.hicumes.mappingEngine.plugins.PluginInformation;
import de.iisys.sysint.hicumes.mappingEngine.plugins.parserPlugin.dataParser.IDataParser;
import de.iisys.sysint.hicumes.mappingEngine.exceptions.dataSourceReaderExceptions.DataSourceParserException;
import de.iisys.sysint.hicumes.mappingEngine.models.dataSource.reader.ReaderResult;
import de.iisys.sysint.hicumes.mappingEngine.models.mapping.MappingInput;
import de.iisys.sysint.hicumes.mappingEngine.plugins.userInterface.FormField;
import org.pf4j.Extension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Extension
public class ParserPluginJSON implements IDataParser {

    private Map<String, String> configMap = new HashMap<>();

    @Override
    public MappingInput parse(ReaderResult result, MappingAndDataSource mappingAndDataSource) throws DataSourceParserException {
        System.out.println("Using JSON Parser Plugin for parsing");
        try {
            ObjectNode inputData = new ObjectMapper().createObjectNode();
            if(result.getAdditionalData() != null)
            {
                var inputResult = new ObjectMapper().readTree(result.getResult());
                var inputAdditional = new ObjectMapper().readTree(result.getAdditionalData());
                inputData.set("result", inputResult);
                inputData.set("additional", inputAdditional);
            }
            else
            {
                inputData = (ObjectNode) new ObjectMapper().readTree(result.getResult());
            }
            return new MappingInput(inputData);

        } catch (Exception e) {
            throw new DataSourceParserException("Failed parse JSON from string: " + result.getResult(), e);
        }
    }

    private List<FormField> getFormfields() {
        return null;
    }

    @Override
    public PluginInformation getPluginInformation() {
        PluginInformation information = new PluginInformation(getFormfields(), EPluginType.INPUT_PARSER, "parserPlugin-JSON", "JSON");
        return information;
    }

}
