package de.iisys.sysint.hicumes.manufacturingTerminalBackend.events;

import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.EventEntity;
import lombok.Getter;

import java.util.List;

@Getter
public class EventSetActiveToolSettings extends EventEntity  {

    private int camundaMachineOccupationId;
    private List<Integer> toolSettingIds;

    public EventSetActiveToolSettings(int camundaMachineOccupationId, List<Integer> toolSettingIds) {
        this.camundaMachineOccupationId = camundaMachineOccupationId;
        this.toolSettingIds = toolSettingIds;
    }

    public String toString() {
        return getClass().getSimpleName() + " with content: " + camundaMachineOccupationId + " and toolsettings " + toolSettingIds;
    }
}
