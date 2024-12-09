package de.iisys.sysint.hicumes.mappingBackend.models.frontendMapping;


import de.iisys.sysint.hicumes.mappingEngine.models.dataSource.reader.ReaderResult;
import de.iisys.sysint.hicumes.mappingEngine.models.mapping.MappingInput;
import de.iisys.sysint.hicumes.mappingEngine.models.mapping.MappingLogging;
import de.iisys.sysint.hicumes.mappingEngine.models.mapping.MappingOutput;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FrontendMappingResponse implements  IJson{
    private FrontendTreeNode inputTree;
    private FrontendTreeNode outputTree;
    private MappingInput mappingInput;
    private MappingOutput mappingOutput;
    private MappingLogging mappingLogging;
    private ReaderResult readerResult;
}
