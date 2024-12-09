package de.iisys.sysint.hicumes.mappingEngine.e2eTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.iisys.sysint.hicumes.mappingEngine.dataSource.reader.SingleFileReader;
import de.iisys.sysint.hicumes.mappingEngine.exceptions.dataSourceReaderExceptions.DataSourceReaderException;
import de.iisys.sysint.hicumes.mappingEngine.models.KeyValueConfig;
import de.iisys.sysint.hicumes.mappingEngine.models.MappingAndDataSource;
import de.iisys.sysint.hicumes.mappingEngine.models.dataSource.reader.DataReader;
import de.iisys.sysint.hicumes.mappingEngine.models.dataSource.reader.SingleFileReaderConfig;
import de.iisys.sysint.hicumes.mappingEngine.models.dataSource.writer.DataWriter;
import de.iisys.sysint.hicumes.mappingEngine.models.mapping.MappingConfiguration;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

public class TestData {
    public MappingAndDataSource getXmlSingleFileMapping() throws DataSourceReaderException, URISyntaxException, IOException {
        URL resource = getClass().getResource("/testData/fertigungsauftrag/Fertigungsauftrag.xml");
        var singleFileReaderConfig = new SingleFileReaderConfig(Paths.get(resource.toURI()).toString());
        var readerResult = new SingleFileReader().read(singleFileReaderConfig);
        var dataReader = new DataReader();
        dataReader.addReaderConfig(new KeyValueConfig("FILE_PATH", Paths.get(resource.toURI()).toString()));
        dataReader.setReaderID("inputPlugin-FileReader");
        dataReader.setParserID("parserPlugin-XML");


        var dataWriter = new DataWriter();
        dataWriter.setWriterID("outputPlugin-DatabaseWriter");
        dataWriter.addWriterConfig(new KeyValueConfig("DATABASE_NAME", "hicumes"));
        dataWriter.addWriterConfig(new KeyValueConfig("ENTITY_FILTER", "de.iisys.sysint.hicumes.core.entities"));

        // mapping
        var pathMapping = Paths.get(this.getClass().getResource("/testData/fertigungsauftrag/mapping.json").toURI()).toFile();
        var mapping = new ObjectMapper().readValue(pathMapping, MappingConfiguration.class);

        var mappingAndDataSource = new MappingAndDataSource("testMapping", dataReader,dataWriter,mapping, readerResult);
        return mappingAndDataSource;
    }
}
