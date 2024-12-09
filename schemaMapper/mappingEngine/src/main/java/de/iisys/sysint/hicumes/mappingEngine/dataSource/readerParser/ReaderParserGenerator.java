package de.iisys.sysint.hicumes.mappingEngine.dataSource.readerParser;

import de.iisys.sysint.hicumes.mappingEngine.exceptions.dataSourceReaderExceptions.DataSourceParserException;
import de.iisys.sysint.hicumes.mappingEngine.models.dataSource.reader.DataReader;
import de.iisys.sysint.hicumes.mappingEngine.plugins.MapperPluginManager;

public class ReaderParserGenerator {

    public IReaderParser generate(DataReader dataReader) throws DataSourceParserException {
        //MapperPluginManager pluginManager = MapperPluginManager.getInstance();
        /*switch (dataReader.getParser()) {
            case XML:
                return new XmlReaderParser(); //pluginManager.getReaderParser("mapperPlugin-XML");//
            case JSON:
                return new JsonReaderParser(); //pluginManager.getReaderParser("mapperPlugin-JSON");//
            case CSV:
//                var parser = pluginManager.getReaderParser("mapperPlugin-CSV");
//                parser.addConfig("csv_config", dataReader.getCsvReaderParserConfig());
                return new CsvReaderParser(dataReader.getCsvReaderParserConfig()); //parser;//
            default:
                throw new DataSourceParserException("Parser not implemented: " + dataReader.getParser(), null);
        }*/
        return null;
    }
}
