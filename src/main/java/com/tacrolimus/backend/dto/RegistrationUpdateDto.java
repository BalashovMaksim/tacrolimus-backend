package com.tacrolimus.backend.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
public class RegistrationUpdateDto extends BaseRegistrationSaveDto {
}
