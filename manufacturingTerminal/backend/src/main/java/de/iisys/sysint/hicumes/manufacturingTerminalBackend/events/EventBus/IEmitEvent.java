package de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus;

import java.util.function.Consumer;

public interface IEmitEvent {
    void emit(EventEntity event);
    void emit(EventEntity event, Consumer<EventResult> callback);
}
