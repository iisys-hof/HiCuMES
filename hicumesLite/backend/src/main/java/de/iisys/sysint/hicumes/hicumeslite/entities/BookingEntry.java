package de.iisys.sysint.hicumes.hicumeslite.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.iisys.sysint.hicumes.hicumeslite.adapters.JPADurationAdapter;
import de.iisys.sysint.hicumes.hicumeslite.adapters.LocalDateTimeAdapter;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Data
@ToString(callSuper = true)
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BookingEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String externalId;

    @OneToOne(cascade = CascadeType.MERGE)
    private User user;

    @Column
    private double amount;

    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    @Column
    private LocalDateTime startDateTime;

    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    @Column
    private LocalDateTime endDateTime;

    @Column
    @Convert(converter = JPADurationAdapter.class)
    private Duration duration;

    @Column
    @Convert(converter = JPADurationAdapter.class)
    private Duration breakDuration;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Column
    private String note;

    @CreationTimestamp
    @JsonIgnore
    @ToString.Exclude
    private LocalDateTime createDateTime;

    @Lob
    @Column( length = 1000000 )
    String message;

    @Lob
    @Column( length = 1000000 )
    String response;


    public enum Status {
        CREATED,
        FINISHED,
        SENT,
        ERROR,
        CONFIRMED,
        RESEND,
        OTHER,
        TIMEOUT
    }

}
