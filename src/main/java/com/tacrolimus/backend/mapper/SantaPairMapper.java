package com.tacrolimus.backend.mapper;

import com.tacrolimus.backend.dto.SantaPairReadDto;
import com.tacrolimus.backend.dto.StatusReadDto;
import com.tacrolimus.backend.model.SantaPair;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SantaPairMapper {

    @Mapping(source = "santa.person", target = "santaId")
    @Mapping(source = "recipient.person", target = "recipientId")
    @Mapping(source = "file", target = "fileId")
    SantaPairReadDto santaPairToSantaPairReadDto(SantaPair santaPair);
    @Mapping(source = "file", target = "file")
    @Mapping(source = "status", target = "currentStatus")
    @Mapping(source = "comment", target = "comment")
    StatusReadDto toStatusReadDto(SantaPair santaPair);
}