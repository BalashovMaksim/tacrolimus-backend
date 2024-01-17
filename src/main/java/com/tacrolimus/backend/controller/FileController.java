package com.tacrolimus.backend.controller;

import com.tacrolimus.backend.dto.FileInfoReadDto;
import com.tacrolimus.backend.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @PostMapping("/upload")
    public FileInfoReadDto uploadFile(@RequestParam("file") MultipartFile file) {
        return fileService.uploadFile(file);
    }

    public MultipartFile downloadFile(@PathVariable UUID id){
        return null;
    }
}
