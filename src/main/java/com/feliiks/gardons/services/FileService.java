package com.feliiks.gardons.services;

import com.feliiks.gardons.entities.FileEntity;
import com.feliiks.gardons.exceptions.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface FileService {
    FileEntity saveFile(MultipartFile file) throws BusinessException;
    byte[] download(String path, String fileName);
    void deleteFile(String url);
}
