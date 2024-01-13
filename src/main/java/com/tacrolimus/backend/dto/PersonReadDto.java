package com.tacrolimus.backend.dto;

import com.tacrolimus.backend.enu.OrganEnum;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class PersonReadDto {
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private LocalDate transplantationDay;
    private List<OrganEnum> organ;
}
