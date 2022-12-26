package com.feliiks.gardons.services;

import com.feliiks.gardons.entities.FileEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public interface FileService {
    List<FileEntity> findAll();
    Optional<FileEntity> findById(Long id);
    FileEntity store(MultipartFile file) throws IOException;
}
