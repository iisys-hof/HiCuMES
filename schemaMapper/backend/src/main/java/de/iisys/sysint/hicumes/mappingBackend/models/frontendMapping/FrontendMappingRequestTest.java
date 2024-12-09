package de.iisys.sysint.hicumes.mappingBackend.models.frontendMapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.iisys.sysint.hicumes.mappingEngine.e2eTest.TestData;
import de.iisys.sysint.hicumes.mappingEngine.exceptions.dataSourceReaderExceptions.DataSourceReaderException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

class FrontendMappingRequestTest {

    @Test
    void getMappingAndDataSource() throws DataSourceReaderException, IOException, URISyntaxException {
        var dataSource = new TestData().getXmlSingleFileMapping();
        dataSource.getReaderResult().setResult("hi");
        var request = new FrontendMappingRequest(dataSource, true, true);
        System.out.println(request.toJson());

        String json = "{\n" +
                "  \"mappingAndDataSource\": {\n" +
                "    \"dataWriter\": {\n" +
                "      \"writerType\": \"INTERNALDATASCHEMA\",\n" +
                "      \"writerParserType\": \"INTERNALDATASCHEMA\"\n" +
                "    }\n" +
                "  }\n" +
                "}";


        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        var mapping = mapper.readValue(json, FrontendMappingRequest.class);
        System.out.println(mapping);
    }
}
