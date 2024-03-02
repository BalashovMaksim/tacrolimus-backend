package com.tacrolimus.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Schema(description = "DTO для создания участника")
public class RegistrationCreateDto extends BaseRegistrationSaveDto{
    @NotNull(message = "Person ID cannot be null")
    @Schema(description = "ID человека для регистрации")
    private UUID personId;
}