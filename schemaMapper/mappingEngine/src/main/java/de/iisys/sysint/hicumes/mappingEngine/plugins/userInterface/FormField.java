package de.iisys.sysint.hicumes.mappingEngine.plugins.userInterface;

import lombok.Getter;

import java.util.List;

@Getter
public class FormField {
    private EFormfieldType type;
    private String key;
    private String label;
    private boolean required;
    private String defaultValue;
    private List<SelectOptions> selectOptionsList;

    public FormField(EFormfieldType type, String key, String label, boolean required, String defaultValue, List<SelectOptions> selectOptionsList) {
        this.type = type;
        this.key = key;
        this.label = label;
        this.required = required;
        this.defaultValue = defaultValue;
        this.selectOptionsList = selectOptionsList;
    }
}
