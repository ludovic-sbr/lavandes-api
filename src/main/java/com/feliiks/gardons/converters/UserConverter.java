package com.feliiks.gardons.converters;

import com.feliiks.gardons.dtos.LoginRequest;
import com.feliiks.gardons.dtos.PatchUserRequest;
import com.feliiks.gardons.dtos.PostUserRequest;
import com.feliiks.gardons.entities.RoleEntity;
import com.feliiks.gardons.entities.UserEntity;
import com.feliiks.gardons.models.UserModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserConverter {
    @Autowired
    private ModelMapper mapper;

    public UserEntity getLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() == null) {
            return null;
        }

        return (UserEntity) authentication.getPrincipal();
    }

    public UserEntity convertToEntity(LoginRequest loginRequest) {
        mapper.typeMap(LoginRequest.class, UserEntity.class).addMappings(elt -> {
            elt.skip(UserEntity::setId);
            elt.skip(UserEntity::setFirstname);
            elt.skip(UserEntity::setLastname);
            elt.skip(UserEntity::setRole);
        });

        return mapper.map(loginRequest, UserEntity.class);
    }

    public UserEntity convertToEntity(PostUserRequest userRequest) {
        mapper.typeMap(PostUserRequest.class, UserEntity.class).addMappings(elt -> elt.skip(UserEntity::setId));

        UserEntity userEntity = mapper.map(userRequest, UserEntity.class);

        userEntity.setRole(new RoleEntity(userRequest.getRoleName()));

        return userEntity;
    }

    public UserEntity convertToEntity(PatchUserRequest userRequest) {
        mapper.typeMap(PatchUserRequest.class, UserEntity.class).addMappings(elt -> elt.skip(UserEntity::setId));
        mapper.typeMap(PatchUserRequest.class, UserEntity.class).addMappings(elt -> elt.skip(UserEntity::setGoogle_id));

        UserEntity userEntity = mapper.map(userRequest, UserEntity.class);

        userEntity.setRole(new RoleEntity(userRequest.getRoleName()));

        return userEntity;
    }

    public UserEntity convertToEntity(UserModel user) {
        return mapper.map(user, UserEntity.class);
    }

    public UserModel convertToModel(UserEntity user) {
        return mapper.map(user, UserModel.class);
    }
}
