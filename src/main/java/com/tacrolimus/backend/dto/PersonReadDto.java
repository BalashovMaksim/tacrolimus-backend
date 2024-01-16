package com.tacrolimus.backend.dto;

import com.tacrolimus.backend.enu.OrganEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonReadDto {
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private LocalDate transplantationDate;
    private List<OrganEnum> organ;
}
