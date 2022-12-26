package com.feliiks.gardons.converters;

import com.feliiks.gardons.entities.FileEntity;
import com.feliiks.gardons.models.FileModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileConverter {
    @Autowired
    private ModelMapper mapper;

    public FileEntity convertToEntity(FileModel file) {
        return mapper.map(file, FileEntity.class);
    }

    public FileModel convertToModel(FileEntity file) {
        return mapper.map(file, FileModel.class);
    }
}
