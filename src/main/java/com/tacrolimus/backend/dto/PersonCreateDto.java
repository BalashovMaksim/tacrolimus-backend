package com.tacrolimus.backend.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@SuperBuilder
public class PersonCreateDto extends BasePersonSaveDto {
}
