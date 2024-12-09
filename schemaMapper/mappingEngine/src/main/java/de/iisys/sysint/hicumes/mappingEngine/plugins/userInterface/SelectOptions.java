package de.iisys.sysint.hicumes.mappingEngine.plugins.userInterface;

import lombok.Getter;

@Getter
public class SelectOptions {
    private String label;
    private String value;

    public SelectOptions(String label, String value)
    {
        this.label = label;
        this.value = value;
    }
}
