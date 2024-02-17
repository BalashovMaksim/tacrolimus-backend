package com.tacrolimus.backend.service;

import com.tacrolimus.backend.dto.FileInfoReadDto;
import com.tacrolimus.backend.exception.*;
import com.tacrolimus.backend.mapper.FileInfoMapper;
import com.tacrolimus.backend.model.FileInfo;
import com.tacrolimus.backend.repository.FileInfoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService{
    private final FileInfoRepository fileInfoRepository;

    private final FileInfoMapper fileInfoMapper;

    @Value("${spring.file.destination-path}")
    private String destinationPath;

    @Transactional
    public FileInfoReadDto uploadFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new EmptyFileException();
        }

        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        String extension = StringUtils.getFilenameExtension(filename);
        String uniqueFileName = UUID.randomUUID().toString() + "." + extension;
        Path destinationFilePath = Paths.get(destinationPath, uniqueFileName);

        try {
            Files.createDirectories(destinationFilePath.getParent());
            Files.copy(file.getInputStream(), destinationFilePath, StandardCopyOption.REPLACE_EXISTING);

            FileInfo savedFileInfo = fileInfoRepository.save(FileInfo.builder()
                    .name(filename)
                    .url(destinationFilePath.toString())
                    .size(Math.toIntExact(file.getSize()))
                    .build());

            return fileInfoMapper.entityToDto(savedFileInfo);
        } catch (IOException e) {
            throw new FileStorageException(uniqueFileName,e);
        }
    }

    @Transactional
    public Resource downloadFile(UUID id) {
        FileInfo fileInfo = fileInfoRepository.findById(id)
                .orElseThrow(() -> new FileNotFoundException(id));
        try {
            Path filePath = Paths.get(fileInfo.getUrl());
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new FileNotReadableException();
            }

        } catch (MalformedURLException e) {
            throw new InvalidFileUrlException(e);
        }
    }
}
