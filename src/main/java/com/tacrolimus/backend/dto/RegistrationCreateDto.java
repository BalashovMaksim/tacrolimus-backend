package com.tacrolimus.backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
public class RegistrationCreateDto extends BaseRegistrationSaveDto{
    @NotNull(message = "Person ID cannot be null")
    private UUID personId;
}