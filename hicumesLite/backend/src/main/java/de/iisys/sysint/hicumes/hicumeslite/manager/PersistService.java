package de.iisys.sysint.hicumes.hicumeslite.manager;

import de.iisys.sysint.hicumes.hicumeslite.entities.BookingEntry;
import de.iisys.sysint.hicumes.hicumeslite.entities.User;

import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Singleton
@AccessTimeout(value=60000)
public class PersistService {

    @PersistenceContext()
    private EntityManager em;

    public List<BookingEntry> getBookingEntriesByExternalId(String externalId) {
        try {
            var resultDesc= em.createQuery("SELECT b FROM BookingEntry b WHERE b.externalId = :externalId order by b.createDateTime desc", BookingEntry.class)
                    .setParameter("externalId", externalId)
                    .setMaxResults(5)
                    .getResultList();
            resultDesc.sort(Comparator.comparing(BookingEntry::getCreateDateTime));
            return resultDesc;
        } catch (NoResultException e) {
            return null; // No entry found
        }
    }

    public List<BookingEntry> getAllBookingEntriesByExternalId(String externalId) {
        try {
            return em.createQuery("SELECT b FROM BookingEntry b WHERE b.externalId = :externalId order by b.createDateTime asc", BookingEntry.class)
                    .setParameter("externalId", externalId)
                    .getResultList();
        } catch (NoResultException e) {
            return null; // No entry found
        }
    }

    public BookingEntry getSingleBookingEntryById(Long id) {
        try {
            return em.createQuery("SELECT b FROM BookingEntry b WHERE b.id = :id", BookingEntry.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null; // No entry found
        }
    }

    public User findUserByName(String userName) {
        try {
            return em.createQuery("SELECT u FROM User u WHERE u.shortName = :name", User.class)
                    .setParameter("name", userName)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null; // No entry found
        }
    }

    public User findUserByPersonalNumber(String number) {
        try {
            return em.createQuery("SELECT u FROM User u WHERE u.personalNumber = :number", User.class)
                    .setParameter("number", number)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null; // No entry found
        }
    }

    public List<BookingEntry> findAllByDateRange(LocalDateTime start, LocalDateTime end) {
        return em.createQuery("SELECT b FROM BookingEntry b WHERE b.startDatetime BETWEEN :start AND :end", BookingEntry.class)
                .setParameter("start", start)
                .setParameter("end", end)
                .getResultList();
    }

    public void save(BookingEntry bookingEntry) {
        em.merge(bookingEntry);
    }

    public List<BookingEntry> getAllNewAndResendBookingEntries() {
        return em.createQuery("SELECT b FROM BookingEntry b WHERE b.status in ('FINISHED','RESEND','TIMEOUT')", BookingEntry.class)
                .getResultList();
    }

    public List<BookingEntry> getAllOpenBookingEntriesByUser(String personalNumber) {
        try {
            return em.createQuery("SELECT b FROM BookingEntry b join User u on b.user.id = u.id WHERE u.personalNumber = :personalNumber and b.status = 'CREATED' order by b.createDateTime asc", BookingEntry.class)
                    .setParameter("personalNumber", personalNumber)
                    .getResultList();
        } catch (NoResultException e) {
            return null; // No entry found
        }
    }
}
