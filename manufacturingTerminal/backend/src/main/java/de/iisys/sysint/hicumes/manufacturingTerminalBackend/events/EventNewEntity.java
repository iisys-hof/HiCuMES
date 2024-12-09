package de.iisys.sysint.hicumes.manufacturingTerminalBackend.events;

import de.iisys.sysint.hicumes.core.entities.EntitySuperClass;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.EventEntity;
import lombok.Getter;

public class EventNewEntity extends EventEntity  {
    @Getter
    EntitySuperClass entity;

    @Getter
    boolean forceUpdate = false;

    @Getter
    boolean isCamundaSubMachineOccupation = false;

    public EventNewEntity(EntitySuperClass entity) {
        this.entity = entity;
    }
    public EventNewEntity(EntitySuperClass entity, boolean forceUpdate) {
        this.entity = entity;
        this.forceUpdate = forceUpdate;
    }

    public EventNewEntity(EntitySuperClass entity, boolean forceUpdate, boolean isCamundaSubMachineOccupation) {
        this.entity = entity;
        this.forceUpdate = forceUpdate;
        this.isCamundaSubMachineOccupation = isCamundaSubMachineOccupation;
    }

    public String toString() {
        return getClass().getSimpleName() + " with content: " + entity.getClass().getSimpleName();
    }
}
