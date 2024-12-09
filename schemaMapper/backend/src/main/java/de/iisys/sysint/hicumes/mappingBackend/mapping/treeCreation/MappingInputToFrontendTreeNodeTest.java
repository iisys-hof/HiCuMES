package de.iisys.sysint.hicumes.mappingBackend.mapping.treeCreation;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.iisys.sysint.hicumes.mappingBackend.exceptions.mappingExceptions.ParsingMappingException;
import de.iisys.sysint.hicumes.mappingEngine.dataSource.reader.SingleFileReader;
import de.iisys.sysint.hicumes.mappingEngine.dataSource.readerParser.XmlReaderParser;
import de.iisys.sysint.hicumes.mappingEngine.exceptions.dataSourceReaderExceptions.DataSourceParserException;
import de.iisys.sysint.hicumes.mappingEngine.exceptions.dataSourceReaderExceptions.DataSourceReaderException;
import de.iisys.sysint.hicumes.mappingEngine.models.dataSource.reader.SingleFileReaderConfig;
import org.junit.jupiter.api.Test;

class MappingInputToFrontendTreeNodeTest {

    @Test
    void createDataMapForTree() throws DataSourceReaderException, DataSourceParserException, JsonProcessingException, ParsingMappingException {
        var dataSource = new SingleFileReaderConfig("../testData/fertigungsauftrag/Fertigungsauftrag.xml");
        var inputData = new XmlReaderParser().parse( new SingleFileReader().read(dataSource));
        var result = new MappingInputToFrontendTreeNode().transform(inputData);
        System.out.println(result.toJson());
    }
}

