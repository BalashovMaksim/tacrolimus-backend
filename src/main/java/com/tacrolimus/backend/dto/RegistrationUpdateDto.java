package com.tacrolimus.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Schema(description = "DTO для обновления информации участника")
public class RegistrationUpdateDto extends BaseRegistrationSaveDto {
}
