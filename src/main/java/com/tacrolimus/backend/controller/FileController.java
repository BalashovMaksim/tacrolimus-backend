package com.tacrolimus.backend.controller;

import com.tacrolimus.backend.dto.FileInfoReadDto;
import com.tacrolimus.backend.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
@Tag(name = "File Controller")
public class FileController {
    private final FileService fileService;

    @PostMapping("/upload")
    @Operation(summary = "Загрузка файла",
        description = "Позволяет загрузить файл на сервер",
        responses = {
            @ApiResponse(responseCode = "200",
                description = "Файл успешно загружен",
                content = @Content(mediaType = "application/json",schema = @Schema(implementation = FileInfoReadDto.class))),
            @ApiResponse(responseCode = "400",
                description = "Некорректный запрос")
    })
    public FileInfoReadDto uploadFile(@RequestParam("file") MultipartFile file) {
        return fileService.uploadFile(file);
    }


    @GetMapping("/download/{id}")
    @Operation(summary = "Скачивание файла",
        description = "Позволяет скачать файл по ID",
        responses = {
            @ApiResponse(responseCode = "200",
                description = "Файл успешно скачан",
                content = @Content(mediaType = "application/octet-stream", schema = @Schema(type = "string", format = "binary"))),
            @ApiResponse(responseCode = "404",
                description = "Файл не найден")
    })
    public ResponseEntity<Resource> downloadFile(@Parameter(description = "ID файла для скачивания") @PathVariable UUID id) {
        Resource file = fileService.downloadFile(id);
        String mimeType = "application/octet-stream";
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(mimeType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }
}
