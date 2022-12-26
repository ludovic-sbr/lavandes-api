package com.feliiks.gardons.repositories;

import com.feliiks.gardons.converters.FileConverter;
import com.feliiks.gardons.entities.FileEntity;
import com.feliiks.gardons.models.FileModel;
import com.feliiks.gardons.repositories.jpa.FileJpaRepository;
import org.springframework.stereotype.Component;

@Component
public class FileRepository {
    private final FileJpaRepository fileJpaRepository;
    private final FileConverter fileConverter;

    public FileRepository(
            FileJpaRepository fileJpaRepository,
            FileConverter fileConverter) {
        this.fileJpaRepository = fileJpaRepository;
        this.fileConverter = fileConverter;
    }

    public FileEntity save(FileEntity file) {
        FileModel currentFile = fileConverter.convertToModel(file);

        fileJpaRepository.save(currentFile);

        return fileConverter.convertToEntity(currentFile);
    }
}
