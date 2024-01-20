package com.tacrolimus.backend.service;

import com.tacrolimus.backend.dto.FileInfoReadDto;
import com.tacrolimus.backend.mapper.FileInfoMapper;
import com.tacrolimus.backend.model.FileInfo;
import com.tacrolimus.backend.repository.FileInfoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService{
    private final FileInfoRepository fileInfoRepository;
    private final FileInfoMapper fileInfoMapper;

    @Override
    @Transactional
    public FileInfoReadDto uploadFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File cannot be empty");
        }

        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String extension = StringUtils.getFilenameExtension(filename);
        String uniqueFileName = UUID.randomUUID() + "." + extension;
        Path destinationFilePath = Paths.get("C:/santa_files/", uniqueFileName);

        try {
            Files.createDirectories(destinationFilePath.getParent());
            Files.copy(file.getInputStream(), destinationFilePath, StandardCopyOption.REPLACE_EXISTING);

            FileInfo fileInfo = FileInfo.builder()
                    .name(filename)
                    .url(destinationFilePath.toString())
                    .size(Long.valueOf(file.getSize()).intValue())
                    .build();

            FileInfo savedFileInfo = fileInfoRepository.save(fileInfo);
            return fileInfoMapper.entityToDto(savedFileInfo);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to save file", e);
        }
    }

    @Override
    @Transactional
    public Resource downloadFile(UUID id) {
        FileInfo fileInfo = fileInfoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("File with ID " + id + " not found"));
        try {
            Path filePath = Paths.get(fileInfo.getUrl());
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Unable to read file");
            }

        } catch (MalformedURLException e) {
            throw new RuntimeException("URL error", e);
        }
    }
}
