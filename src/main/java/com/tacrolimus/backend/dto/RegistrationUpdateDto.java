package com.tacrolimus.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
public class RegistrationUpdateDto extends BaseRegistrationSaveDto {
    @JsonIgnore
    private transient UUID personId;
}
