package de.iisys.sysint.hicumes.core.utils.fileSystem;

import de.iisys.sysint.hicumes.core.utils.exceptions.fileSystem.FileSystemUtilsException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ZipFilesServiceTest {

    @Test
    void loadFileInsideZip() throws FileSystemUtilsException {
        var zipService = new ZipFilesService();
        var file = zipService.loadFileInsideZip("C:/HiCuMES/wildfly-24.0.1.Final/standalone/deployments/mappingBackend.war/WEB-INF/lib/mappingEngine-1.0-SNAPSHOT.jar/lodash.min.js");
        System.out.println(file);
    }

}