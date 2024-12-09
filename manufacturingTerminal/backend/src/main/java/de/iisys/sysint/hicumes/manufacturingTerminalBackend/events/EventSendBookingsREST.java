package de.iisys.sysint.hicumes.manufacturingTerminalBackend.events;

import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.EventEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;

import java.util.HashMap;

@Data
@Generated
@AllArgsConstructor
public class EventSendBookingsREST extends EventEntity {

    private HashMap<String, String> queryParams = new HashMap<>();
    private HashMap<String, String> headerParams = new HashMap<>();
    private String requestType;
    private String requestAddress;

    public String toString() {
        return getClass().getSimpleName() + " with: " + requestType + " to: " + requestAddress + " with headerParams: " + headerParams + " and queryParams: " + queryParams;
    }
}
