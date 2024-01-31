package com.tacrolimus.backend.mapper;

import com.tacrolimus.backend.dto.FileInfoReadDto;
import com.tacrolimus.backend.model.FileInfo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FileInfoMapper {
    FileInfoReadDto entityToDto(FileInfo fileInfo);
}
