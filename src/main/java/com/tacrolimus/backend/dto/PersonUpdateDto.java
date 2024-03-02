package com.tacrolimus.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@SuperBuilder
@Schema(description = "DTO для обновление информации о человеке")
public class PersonUpdateDto extends BasePersonSaveDto {
}
