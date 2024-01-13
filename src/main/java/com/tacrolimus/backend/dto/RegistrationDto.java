package com.tacrolimus.backend.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class RegistrationDto {
    private String address;
    private String wishes;
    private UUID person;
}
