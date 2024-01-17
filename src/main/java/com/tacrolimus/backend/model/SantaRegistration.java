package com.tacrolimus.backend.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "santa_registration")
public class SantaRegistration extends BaseEntity{
    private String address;
    private String wishes;
    @OneToOne
    @JoinColumn(name = "person_id")
    private Person person;
}
