package de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus;

import com.google.common.eventbus.EventBus;
import de.iisys.sysint.hicumes.core.utils.logger.Logger;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.function.Consumer;


/**
 * https://www.baeldung.com/guava-eventbus
 */
@SuppressWarnings("UnstableApiUsage")
@Singleton
public class Events implements IEmitEvent {

    private final Logger logger = new Logger(this.getClass().getName());
    @Inject
    Instance<ISubscribeEvent> instance;
    private EventBus eventBus;
    private boolean isInit = false;

    public void initEventBus() {
        logger.logMessage("Starting EventBus", ":");
        eventBus = new EventBus();
        for (ISubscribeEvent classInstance : instance) {
            register(classInstance);
        }
    }

    public void register(ISubscribeEvent classInstance) {
        logger.logMessage("Register new subscription: " + classInstance);
        eventBus.register(classInstance);

    }

    @Override
    public void emit(EventEntity event) {
       emit(event, null);
    }

    @Override
    public void emit(EventEntity event, Consumer<EventResult> callback) {
        event.setCallback(callback);
        if (!isInit) {
            initEventBus();
            isInit = true;
        }
        logger.logMessage("SEND INTERNAL EVENT: ","->" , event);
        eventBus.post(event);
    }
}
