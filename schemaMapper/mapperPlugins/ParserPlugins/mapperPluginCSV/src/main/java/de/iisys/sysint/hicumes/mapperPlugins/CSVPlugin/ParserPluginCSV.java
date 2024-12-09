package de.iisys.sysint.hicumes.mapperPlugins.CSVPlugin;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import de.iisys.sysint.hicumes.mappingEngine.models.MappingAndDataSource;
import de.iisys.sysint.hicumes.mappingEngine.plugins.EPluginType;
import de.iisys.sysint.hicumes.mappingEngine.plugins.PluginInformation;
import de.iisys.sysint.hicumes.mappingEngine.plugins.parserPlugin.dataParser.IDataParser;
import de.iisys.sysint.hicumes.mappingEngine.exceptions.dataSourceReaderExceptions.DataSourceParserException;
import de.iisys.sysint.hicumes.mappingEngine.models.dataSource.reader.ReaderResult;
import de.iisys.sysint.hicumes.mappingEngine.models.mapping.MappingInput;
import de.iisys.sysint.hicumes.mappingEngine.plugins.userInterface.EFormfieldType;
import de.iisys.sysint.hicumes.mappingEngine.plugins.userInterface.FormField;
import org.pf4j.Extension;

import java.util.ArrayList;
import java.util.List;

@Extension
public class ParserPluginCSV implements IDataParser {

    @Override
    public MappingInput parse(ReaderResult result, MappingAndDataSource mappingAndDataSource) throws DataSourceParserException {
        System.out.println("Using CSV Parser Plugin for parsing");
        ObjectMapper mapper = new ObjectMapper();
        CsvMapper csvMapper = new CsvMapper();
        var parserConfig = mappingAndDataSource.getDataReader().getParserConfigByKey("SEPARATOR_CHAR");
        if(parserConfig == null)
        {
            return null;
        }
        String separator = parserConfig.getConfigValue();
        System.out.println("Separator: " + separator);

        try {

            CsvSchema orderLineSchema = CsvSchema.emptySchema().withHeader().withColumnSeparator(separator.charAt(0));
            var orderLines = csvMapper.readerFor(JsonNode.class)
                    .with(orderLineSchema)
                    .readValues(result.getResult());

            JsonNode array = mapper.valueToTree(orderLines.readAll());
            ObjectNode node = mapper.createObjectNode();
            node.set("result", array);
            return new MappingInput(node);

        } catch (Exception e) {
            throw new DataSourceParserException("Failed parse CSV from string with separator : " + separator.charAt(0) + " and input text: " + result.getResult(), e);
        }
    }

    private List<FormField> getFormfields() {
        List<FormField> formFields = new ArrayList<>();
        formFields.add(new FormField(EFormfieldType.INPUT, "SEPARATOR_CHAR", "Trennzeichen", true, ";", null));
        return formFields;
    }

    @Override
    public PluginInformation getPluginInformation() {
        PluginInformation information = new PluginInformation(getFormfields(), EPluginType.INPUT_PARSER, "parserPlugin-CSV", "CSV");
        return information;
    }

}
