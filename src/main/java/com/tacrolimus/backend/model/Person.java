package com.tacrolimus.backend.model;

import com.tacrolimus.backend.enu.OrganEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private LocalDate transplantationDay;

    @ElementCollection
    @CollectionTable(name = "person_organ", joinColumns = @JoinColumn(name = "person_id"))
    @Column(name = "organ")
    @Enumerated(EnumType.STRING)
    private List<OrganEnum> organ;

    private boolean isActivated;
    private boolean isDeleted;
}
