package com.tacrolimus.backend.dto;

import com.tacrolimus.backend.enu.OrganEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Schema(description = "Транспортный DTO, содержащий информацию о человеке")
public class PersonReadDto {
    @Schema(description = "Имя")
    private String firstName;

    @Schema(description = "Фамилия")
    private String lastName;

    @Schema(description = "Дата рождения")
    private LocalDate birthday;

    @Schema(description = "Дата трансплантации")
    private LocalDate transplantationDate;

    @Schema(description = "Список трансплантированных органов")
    private List<OrganEnum> organ;
}
