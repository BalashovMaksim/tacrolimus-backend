package com.tacrolimus.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class RegistrationReadDto {
    private String address;
    private String wishes;
    private PersonReadDto person;
}
