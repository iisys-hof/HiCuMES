package de.iisys.sysint.hicumes.mappingEngine.models;

import de.iisys.sysint.hicumes.mappingEngine.models.dataSource.reader.ReaderResult;
import de.iisys.sysint.hicumes.mappingEngine.models.mapping.MappingDependencyTree;
import de.iisys.sysint.hicumes.mappingEngine.models.mapping.MappingInput;
import de.iisys.sysint.hicumes.mappingEngine.models.mapping.MappingLogging;
import de.iisys.sysint.hicumes.mappingEngine.models.mapping.MappingOutput;
import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class MappingWorkflowResult {
    public MappingWorkflowResult() {
    }

    private MappingInput mappingInput;
    private MappingOutput mappingOutput;
    private MappingLogging mappingLogging;
    private MappingDependencyTree mappingDependencyTree;
    private ReaderResult readerResult;
}