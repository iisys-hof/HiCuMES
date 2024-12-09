package de.iisys.sysint.hicumes.manufacturingTerminalBackend.events;

import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.EventEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EventResendBooking extends EventEntity {
    private long bookingId;

    public String toString() {
        return getClass().getSimpleName() + " with booking Id: " + bookingId;
    }
}
