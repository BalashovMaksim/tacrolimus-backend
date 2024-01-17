package com.tacrolimus.backend.model;

import com.tacrolimus.backend.enu.OrganEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "person")
public class Person extends BaseEntity{
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private LocalDate transplantationDate;

    @ElementCollection
    @CollectionTable(name = "person_organ", joinColumns = @JoinColumn(name = "person_id"))
    @Column(name = "organ")
    @Enumerated(EnumType.STRING)
    private List<OrganEnum> organ;

    @Builder.Default
    private Boolean isActivated = false;
    @Builder.Default
    private Boolean isDeleted = false;
}
