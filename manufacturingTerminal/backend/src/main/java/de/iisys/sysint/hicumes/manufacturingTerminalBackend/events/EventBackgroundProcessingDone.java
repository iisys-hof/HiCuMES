package de.iisys.sysint.hicumes.manufacturingTerminalBackend.events;

import de.iisys.sysint.hicumes.core.entities.MachineOccupation;
import de.iisys.sysint.hicumes.core.utils.exceptions.json.JsonParsingUtilsException;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.entities.CamundaMachineOccupation;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.EventEntity;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.json.JsonTransformerMachineOccupation;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.json.Json;

@AllArgsConstructor
@Data
public class EventBackgroundProcessingDone extends EventEntity {
    private EventEntity causedByEvent;
    private CamundaMachineOccupation camundaMachineOccupation;

    private final JsonTransformerMachineOccupation jsonTransformerMachineOccupation = new JsonTransformerMachineOccupation();

    public String toString() {
        try {
            var json = Json.createObjectBuilder().add("causedByEvent", causedByEvent.toString());
            json.add("camundaMachineOccupation", jsonTransformerMachineOccupation.getJsonResponse(camundaMachineOccupation).getEntity().toString());
            return json.build().toString();
        } catch (JsonParsingUtilsException e) {
            e.printStackTrace();
        }
        return null;
    }
}
