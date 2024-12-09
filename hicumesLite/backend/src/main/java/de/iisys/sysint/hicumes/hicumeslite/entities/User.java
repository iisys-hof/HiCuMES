package de.iisys.sysint.hicumes.hicumeslite.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Data
@AllArgsConstructor
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String shortName;

    @Column(nullable = false)
    private String personalNumber;

}
