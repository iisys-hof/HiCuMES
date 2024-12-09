package de.iisys.sysint.hicumes.manufacturingTerminalBackend.events;

import de.iisys.sysint.hicumes.core.utils.exceptions.json.JsonParsingUtilsException;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.entities.CamundaMachineOccupation;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.EventEntity;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.json.JsonTransformerMachineOccupation;

import javax.json.Json;
import java.time.LocalDateTime;

public class EventHeartBeat extends EventEntity {
    public String toString() {
        var json = Json.createObjectBuilder().add("causedByEvent", this.getClass().getSimpleName());
        json.add("timeStamp", LocalDateTime.now().toString());
        return json.build().toString();
    }
}
