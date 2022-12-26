package com.feliiks.gardons.dtos;

import com.feliiks.gardons.entities.FileEntity;

public class FileResponse {
    private FileEntity file;

    public FileResponse(FileEntity file) {
        this.file = file;
    }

    public FileEntity getFile() {
        return file;
    }

    public void setFile(FileEntity file) {
        this.file = file;
    }
}