package com.feliiks.gardons.implementations;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import com.feliiks.gardons.entities.FileEntity;
import com.feliiks.gardons.exceptions.BusinessException;
import com.feliiks.gardons.repositories.FileRepository;
import com.feliiks.gardons.services.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class FileImpl implements FileService {

    private final AmazonS3 amazonS3;
    public final FileRepository fileRepository;
    @Value("${aws.s3.bucket.name}")
    private String bucketName;

    public FileImpl(AmazonS3 amazonS3, FileRepository fileRepository) {
        this.amazonS3 = amazonS3;
        this.fileRepository = fileRepository;
    }

    public FileEntity saveFile(MultipartFile file) throws BusinessException {
        try {
            if (file.isEmpty())
                throw new IllegalStateException("Cannot upload empty file");

            String folder = "locations";
            String path = String.format("%s/%s", bucketName, folder);
            String fileName = String.format("%s", new Date().getTime()+"-"+file.getOriginalFilename().replaceAll(" ","_"));

            // Uploading file to s3
            amazonS3.putObject(new PutObjectRequest(path, fileName, file.getInputStream(), new ObjectMetadata()).withCannedAcl(CannedAccessControlList.PublicRead));

            // Saving file to db
            FileEntity currentFile = new FileEntity(fileName, path, "");
            currentFile.setPublicUrl(getPublicUrl(currentFile));
            return fileRepository.save(currentFile);
        } catch (IOException exc) {
            throw new BusinessException("Une erreur est survenue lors de l'envoie du fichier.");
        }
    }

    public byte[] download(String path, String key) {
        try {
            S3Object object = amazonS3.getObject(path, key);
            S3ObjectInputStream objectContent = object.getObjectContent();
            return IOUtils.toByteArray(objectContent);
        } catch (AmazonServiceException | IOException e) {
            throw new IllegalStateException("Failed to download the file", e);
        }
    }

    public String getPublicUrl(FileEntity file) {
        return "https://"+file.getPath().substring(0, file.getPath().indexOf('/'))+".s3.eu-west-3.amazonaws.com"+ file.getPath().substring(file.getPath().indexOf('/'))+"/"+file.getName();
    }

    public String getKey(String url) {
        return url.substring(url.indexOf("amazonaws.com")+14);
    }

    public void deleteFile(String url) {
        if(url != null)
            amazonS3.deleteObject(new DeleteObjectRequest(bucketName, getKey(url)));
    }

}
