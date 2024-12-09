package de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus;

import lombok.Data;

import java.util.function.Consumer;
@Data
public class EventEntity {
    
    Consumer<EventResult> callback = null;
    public void executeCallback(EventResult eventResult) {
        if (callback != null) {
            callback.accept(eventResult);
        }
    }
}
