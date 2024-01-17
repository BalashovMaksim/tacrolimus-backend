package com.tacrolimus.backend.service;

import com.tacrolimus.backend.dto.FileInfoReadDto;
import com.tacrolimus.backend.model.FileInfo;
import com.tacrolimus.backend.repository.FileRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService{

    private final FileRepository fileRepository;

    @Override
    @Transactional
    public FileInfoReadDto uploadFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Файл не может быть пустым");
        }

        try {
            String extension = Objects.requireNonNull(file.getOriginalFilename())
                    .substring(file.getOriginalFilename().lastIndexOf("."));
            String uniqueFileName = UUID.randomUUID().toString() + extension;
            Path destinationFilePath = Paths.get("C:/santa_files/", uniqueFileName);

            Files.createDirectories(destinationFilePath.getParent());

            Files.copy(file.getInputStream(), destinationFilePath, StandardCopyOption.REPLACE_EXISTING);

            FileInfo fileInfo = FileInfo.builder()
                    .name(file.getOriginalFilename())
                    .url(destinationFilePath.toString())
                    .size((int) file.getSize())
                    .build();

            fileInfo = fileRepository.save(fileInfo);

            return new FileInfoReadDto(fileInfo.getUrl(), fileInfo.getName(), fileInfo.getSize());
        } catch (IOException e) {
            throw new IllegalStateException("Не удалось сохранить файл", e);
        }
    }

    @Override
    public MultipartFile downloadFile(UUID id) {
        return null;
    }
}
