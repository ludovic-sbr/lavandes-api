package com.feliiks.gardons.implementations;

import com.feliiks.gardons.entities.FileEntity;
import com.feliiks.gardons.repositories.FileRepository;
import com.feliiks.gardons.services.FileService;
import org.springframework.util.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class FileImpl implements FileService {

    public final FileRepository fileRepository;

    public FileImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public List<FileEntity> findAll() {
        return fileRepository.findAll();
    }

    public Optional<FileEntity> findById(Long id) {
        return fileRepository.findById(id);
    }

    public FileEntity store(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        FileEntity newFile = new FileEntity(fileName, file.getContentType(), file.getBytes());

        return fileRepository.save(newFile);
    }
}
