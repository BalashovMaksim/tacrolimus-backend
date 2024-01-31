package com.tacrolimus.backend.mapper;

import com.tacrolimus.backend.dto.RegistrationCreateDto;
import com.tacrolimus.backend.dto.RegistrationReadDto;
import com.tacrolimus.backend.model.SantaRegistration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SantaRegistrationMapper {
    SantaRegistration toEntity(RegistrationCreateDto dto);
    RegistrationReadDto toDto(SantaRegistration entity);
}
