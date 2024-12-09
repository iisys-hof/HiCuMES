package de.iisys.sysint.hicumes.mappingEngine.plugins;

import de.iisys.sysint.hicumes.mappingEngine.plugins.userInterface.FormField;
import lombok.Getter;

import java.util.List;

@Getter
public class PluginInformation {
    private List<FormField> uiFormfields;
    private EPluginType pluginType;
    private String pluginID;
    private String pluginDisplayName;

    public PluginInformation(List<FormField> uiFormfields, EPluginType pluginType, String pluginID, String pluginDisplayName) {
        this.uiFormfields = uiFormfields;
        this.pluginType = pluginType;
        this.pluginID = pluginID;
        this.pluginDisplayName = pluginDisplayName;
    }
}
