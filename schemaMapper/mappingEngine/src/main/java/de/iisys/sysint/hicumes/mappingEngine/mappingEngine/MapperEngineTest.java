package de.iisys.sysint.hicumes.mappingEngine.mappingEngine;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.iisys.sysint.hicumes.core.entities.CD_Machine;
import de.iisys.sysint.hicumes.core.entities.MachineStatus;
import de.iisys.sysint.hicumes.core.utils.exceptions.fileSystem.FileSystemUtilsException;
import de.iisys.sysint.hicumes.core.utils.fileSystem.ZipFilesService;
import de.iisys.sysint.hicumes.mappingEngine.exceptions.MappingBaseException;
import de.iisys.sysint.hicumes.mappingEngine.exceptions.mappingException.MappingException;
import de.iisys.sysint.hicumes.mappingEngine.models.mapping.MappingConfiguration;
import de.iisys.sysint.hicumes.mappingEngine.models.mapping.MappingDependencyTree;
import de.iisys.sysint.hicumes.mappingEngine.models.mapping.MappingInput;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MapperEngineTest {
    @Test
    void testWithoutOutputLoop() throws IOException, MappingException {
        MappingDependencyTree tree = getMappingDependencyTree("../testData/testMappingEngine/mappingDependencyTree.json");
        MappingInput inputData = getMappingInput("../testData/testMappingEngine/input.json");
        MappingConfiguration mapping = getMappingConfiguration("../testData/testMappingEngine/mappingWithoutOutput.json");
        var mapperEngine = new MapperEngine();
        var output = mapperEngine.doMappingJS(tree, inputData, mapping);
        assertEquals(0,output.getMappingLogging().getError().size());
    }

    @Test
    void test() throws IOException, MappingException {
        MappingDependencyTree tree = getMappingDependencyTree("../testData/testMappingEngine/mappingDependencyTree.json");

        MappingInput inputData = getMappingInput("../testData/testMappingEngine/input.json");

        MappingConfiguration mapping = getMappingConfiguration("../testData/testMappingEngine/mapping.json");
        var mapperEngine = new MapperEngine();
        var output = mapperEngine.doMappingJS(tree, inputData, mapping);
        assertEquals(0,output.getMappingLogging().getError().size());
    }




    @Test
    void testSapJsonFile() throws MappingBaseException, IOException, URISyntaxException {

        MappingDependencyTree tree = getMappingDependencyTree("../testData/fertigungsauftragOData/mappingDependencyTree.json");
        MappingInput inputData = getMappingInput("../testData/fertigungsauftragOData/input.json");
        MappingConfiguration mapping = getMappingConfiguration("../testData/fertigungsauftragOData/mapping.json");

        var mapperEngine = new MapperEngine();
        var output = mapperEngine.doMappingJS(tree, inputData, mapping);
        var resultMachineStatus = output.getMappingOutput().get(MachineStatus.class);
        System.out.println(resultMachineStatus);
        var result = output.getMappingOutput().get(CD_Machine.class);
        var firstMachine = result.get(0);
        assertEquals("Produktpalette_leer", firstMachine.getName());
        var machineStatuses = output.getMappingOutput().get(MachineStatus.class);
        System.out.println(machineStatuses);
        assertEquals(1, machineStatuses.size());
        assertEquals(0,output.getMappingLogging().getError().size());
    }

    @Test
    void pathOpenerWithZip2() throws FileSystemUtilsException, MappingException {
        MapperEngine mapperEngine = new MapperEngine();
        var zipPath = "C:/HiCuMES/wildfly-24.0.1.Final/standalone/deployments/mappingBackend.war/WEB-INF/lib/mappingEngine-1.0-SNAPSHOT.jar/mapper.js";
        var input = new ZipFilesService().loadFileInsideZip(zipPath);
    }

    private MappingInput getMappingInput(String s) throws IOException {
        String inputString = Files.readString(Path.of(s));
        inputString = "{ \"jsonNode\":" + inputString + "}";
        return new ObjectMapper().readValue(inputString, MappingInput.class);
    }

    private MappingDependencyTree getMappingDependencyTree(String s) throws IOException {
        String treeString = Files.readString(Path.of(s));
        return new ObjectMapper().readValue(treeString, MappingDependencyTree.class);
    }

    private MappingConfiguration getMappingConfiguration(String s) throws IOException {
        String mappingString = Files.readString(Path.of(s));
        var mapping = new ObjectMapper().readValue(mappingString, MappingConfiguration.class);
        return mapping;
    }
}
