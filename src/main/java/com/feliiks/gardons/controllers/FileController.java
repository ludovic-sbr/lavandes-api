package com.feliiks.gardons.controllers;

import com.feliiks.gardons.dtos.FileResponse;
import com.feliiks.gardons.dtos.FilesResponse;
import com.feliiks.gardons.entities.FileEntity;
import com.feliiks.gardons.exceptions.BusinessException;
import com.feliiks.gardons.services.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Tag(name = "File")
@RestController
@RequestMapping("/file")
public class FileController {
    public final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @Operation(summary = "List all files.")
    @GetMapping(produces = "application/json")
    public ResponseEntity<FilesResponse> getAllFiles() {
        List<FileEntity> files = fileService.findAll();

        return ResponseEntity.status(200).body(new FilesResponse(files));
    }

    @Operation(summary = "Get a specific file.")
    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<FileResponse> getFileById(@PathVariable("id") Long id) throws BusinessException {
        Optional<FileEntity> file = fileService.findById(id);

        if (file.isEmpty()) {
            String errorMessage = String.format("Le fichier '%s' n'existe pas.", id);
            throw new BusinessException(errorMessage);
        }

        return ResponseEntity.status(200).body(new FileResponse(file.get()));
    }
}
