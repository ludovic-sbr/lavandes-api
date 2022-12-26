package com.feliiks.gardons.dtos;

import com.feliiks.gardons.entities.FileEntity;

import java.util.List;

public class FilesResponse {
    private List<FileEntity> files;

    public FilesResponse(List<FileEntity> files) {
        this.files = files;
    }

    public List<FileEntity> getFiles() {
        return files;
    }

    public void setFiles(List<FileEntity> files) {
        this.files = files;
    }
}
