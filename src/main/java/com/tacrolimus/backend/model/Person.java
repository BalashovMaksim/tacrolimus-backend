package com.tacrolimus.backend.model;

import com.tacrolimus.backend.enu.OrganEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "person")
public class Person extends BaseEntity{
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private LocalDate transplantationDate;

    @ElementCollection
    @CollectionTable(name = "person_organ", joinColumns = @JoinColumn(name = "person_id"))
    @Column(name = "organ")
    @Enumerated(EnumType.STRING)
    private List<OrganEnum> organ;

    private boolean isActivated;
    private boolean isDeleted;
}
