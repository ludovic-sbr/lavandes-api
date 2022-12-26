package com.feliiks.gardons.repositories;

import com.feliiks.gardons.converters.FileConverter;
import com.feliiks.gardons.entities.FileEntity;
import com.feliiks.gardons.models.FileModel;
import com.feliiks.gardons.repositories.jpa.FileJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class FileRepository {
    private final FileJpaRepository fileJpaRepository;
    private final FileConverter fileConverter;

    public FileRepository(FileJpaRepository fileJpaRepository, FileConverter fileConverter) {
        this.fileJpaRepository = fileJpaRepository;
        this.fileConverter = fileConverter;
    }

    public List<FileEntity> findAll() {
        List<FileModel> allFiles = fileJpaRepository.findAll();

        return allFiles.stream().map(fileConverter::convertToEntity).toList();
    }

    public Optional<FileEntity> findById(Long id) {
        Optional<FileModel> file = fileJpaRepository.findById(id);

        if (file.isEmpty()) return Optional.empty();

        return Optional.of(fileConverter.convertToEntity(file.get()));
    }

    public FileEntity save(FileEntity file) {
        FileModel currentFile = fileConverter.convertToModel(file);

        fileJpaRepository.save(currentFile);

        return fileConverter.convertToEntity(currentFile);
    }
}
