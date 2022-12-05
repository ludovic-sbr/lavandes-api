package com.feliiks.gardons.converters;

import com.feliiks.gardons.sqlmodels.RoleModel;
import com.feliiks.gardons.viewmodels.RoleEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleConverter {
    @Autowired
    private ModelMapper mapper;

    public RoleEntity convertToEntity(RoleModel role) {
        return mapper.map(role, RoleEntity.class);
    }

    public RoleModel convertToModel(RoleEntity role) {
        return mapper.map(role, RoleModel.class);
    }
}
