
package com.example.student.controller;

import com.example.student.service.GridFsStorageService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/files")
public class FileController {

    private final GridFsStorageService storageService;

    public FileController(GridFsStorageService storageService) {
        this.storageService = storageService;
    }

    // Upload file
    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(
            @RequestParam("file") MultipartFile file
    ) throws IOException {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Please select a file"));
        }

        String fileId = storageService.uploadFile(
                file.getInputStream(),
                file.getOriginalFilename(),
                file.getContentType()
        );

        return ResponseEntity.ok(
                Map.of(
                        "message", "File uploaded successfully",
                        "fileId", fileId,
                        "fileName", file.getOriginalFilename()
                )
        );
    }

    // Download file
    @GetMapping("/{fileId}")
    public ResponseEntity<InputStreamResource> downloadFile(
            @PathVariable String fileId
    ) throws IOException {

        GridFsResource resource = storageService.downloadFile(fileId);

        String filename = resource.getFilename();

        String contentType = resource.getContentType() != null
                ? resource.getContentType()
                : MediaType.APPLICATION_OCTET_STREAM_VALUE;

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + filename + "\""
                )
                .body(new InputStreamResource(resource.getInputStream()));
    }
}
