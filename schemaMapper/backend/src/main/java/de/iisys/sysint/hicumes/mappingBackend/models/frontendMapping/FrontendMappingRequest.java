package de.iisys.sysint.hicumes.mappingBackend.models.frontendMapping;


import de.iisys.sysint.hicumes.mappingEngine.models.MappingAndDataSource;
import lombok.Data;


@Data
public class FrontendMappingRequest implements IJson {
    public FrontendMappingRequest() {
    }

    private MappingAndDataSource mappingAndDataSource;
    private boolean simulate = true;
    private boolean useSavedData = true;

    public FrontendMappingRequest(MappingAndDataSource mappingAndDataSource, boolean simulate, boolean useSavedData) {
        this.mappingAndDataSource = mappingAndDataSource;
        this.simulate = simulate;
        this.useSavedData = useSavedData;
    }
}
