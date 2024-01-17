package com.tacrolimus.backend.service;

import com.tacrolimus.backend.dto.FileInfoReadDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface FileService {
    FileInfoReadDto uploadFile(MultipartFile file);
    MultipartFile downloadFile(UUID id);
}
