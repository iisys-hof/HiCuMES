package de.iisys.sysint.hicumes.manufacturingTerminalBackend.eventHandler;

import com.google.common.eventbus.Subscribe;
import de.iisys.sysint.hicumes.core.entities.BookingEntry;
import de.iisys.sysint.hicumes.core.entities.EntitySuperClass;
import de.iisys.sysint.hicumes.core.entities.MachineOccupation;
import de.iisys.sysint.hicumes.core.entities.enums.EBookingState;
import de.iisys.sysint.hicumes.core.utils.exceptions.database.DatabasePersistException;
import de.iisys.sysint.hicumes.core.utils.exceptions.database.DatabaseQueryException;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.ISubscribeEvent;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventNewEntity;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventNewMachineOccupation;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventResendBooking;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.manager.SSESessionManager;

public class HandleResendBooking implements ISubscribeEvent {

    @Subscribe
    public void handle(EventResendBooking eventResendBooking) throws DatabasePersistException, DatabaseQueryException {
        var bookingEntry = persistService.getPersistenceManager().getById(BookingEntry.class, eventResendBooking.getBookingId());

        bookingEntry.setBookingState(EBookingState.RESEND);
        persistService.getPersistenceManager().merge(bookingEntry);
    }
}
