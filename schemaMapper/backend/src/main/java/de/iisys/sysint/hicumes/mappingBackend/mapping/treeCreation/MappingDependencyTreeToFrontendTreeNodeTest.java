package de.iisys.sysint.hicumes.mappingBackend.mapping.treeCreation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.iisys.sysint.hicumes.mappingEngine.models.mapping.MappingDependencyTree;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

class MappingDependencyTreeToFrontendTreeNodeTest {

    MappingDependencyTree loadMappingDependencyTree() throws IOException, URISyntaxException {
        var pathMapping = Paths.get(this.getClass().getResource("/testData/mappingDependencyTree.json").toURI()).toFile();


        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        var mapping = mapper.readValue(pathMapping, MappingDependencyTree.class);
        return mapping;
    }

    @Test
    void transform() throws IOException, URISyntaxException {
        var mapping = loadMappingDependencyTree();
        var transformed = new MappingDependencyTreeToFrontendTreeNode().transform(mapping);
        System.out.println(transformed.toJson());
    }
}
