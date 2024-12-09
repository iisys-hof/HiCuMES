package de.iisys.sysint.hicumes.mappingEngine.dataSource.reader;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.iisys.sysint.hicumes.mappingEngine.exceptions.dataSourceReaderExceptions.DataSourceReaderException;
import de.iisys.sysint.hicumes.mappingEngine.models.MappingAndDataSource;
import de.iisys.sysint.hicumes.mappingEngine.models.dataSource.reader.SingleFileReaderConfig;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

class SingleFileReaderTest {

    @Test
    void read() throws URISyntaxException, DataSourceReaderException {
        URL resource = getClass().getResource("/exampleData/fertigungsauftrag/Fertigungsauftrag.xml");
        var readerResource = new SingleFileReaderConfig(Paths.get(resource.toURI()).toString());

        var resultResource = new SingleFileReader().read(readerResource);
        System.out.println(resultResource.getResult().substring(1,100));


    }
    @Test
    void generateFromJson() throws IOException {
        var json = "{\"dataReader\": { " +
                "\"singleFileReaderConfig\": {\n" +
                "                \"path\": \"PathFertigungsauftragXML\"\n" +
                "            }}}";
        var config = new ObjectMapper().readValue(json, MappingAndDataSource.class);
        System.out.println(config);

    }
}