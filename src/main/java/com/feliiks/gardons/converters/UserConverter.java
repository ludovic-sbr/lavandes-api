package com.feliiks.gardons.converters;

import com.feliiks.gardons.dtos.CompleteUserRequest;
import com.feliiks.gardons.dtos.LoginUserRequest;
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

    public UserEntity convertToEntity(LoginUserRequest loginUserRequest) {
        mapper.typeMap(LoginUserRequest.class, UserEntity.class).addMappings(elt -> {
            elt.skip(UserEntity::setId);
            elt.skip(UserEntity::setFirstname);
            elt.skip(UserEntity::setLastname);
            elt.skip(UserEntity::setTel);
            elt.skip(UserEntity::setRole);
            elt.skip(UserEntity::setIs_user_completed);
        });

        return mapper.map(loginUserRequest, UserEntity.class);
    }

    public UserEntity convertToEntity(PostUserRequest postUserRequest) {
        mapper.typeMap(PostUserRequest.class, UserEntity.class).addMappings(elt -> {
            elt.skip(UserEntity::setId);
            elt.skip(UserEntity::setRole);
            elt.skip(UserEntity::setIs_user_completed);
        });

        return mapper.map(postUserRequest, UserEntity.class);
    }

    public UserEntity convertToEntity(CompleteUserRequest completeUserRequest) {
        mapper.typeMap(CompleteUserRequest.class, UserEntity.class).addMappings(elt -> {
            elt.skip(UserEntity::setId);
            elt.skip(UserEntity::setEmail);
            elt.skip(UserEntity::setPassword);
            elt.skip(UserEntity::setGoogle_id);
            elt.skip(UserEntity::setRole);
            elt.skip(UserEntity::setIs_user_completed);
        });

        return mapper.map(completeUserRequest, UserEntity.class);
    }

    public UserEntity convertToEntity(PatchUserRequest patchUserRequest) {
        mapper.typeMap(PatchUserRequest.class, UserEntity.class).addMappings(elt -> {
            elt.skip(UserEntity::setId);
            elt.skip(UserEntity::setGoogle_id);
        });

        UserEntity userEntity = mapper.map(patchUserRequest, UserEntity.class);

        userEntity.setRole(new RoleEntity(patchUserRequest.getRoleName()));

        return userEntity;
    }

    public UserEntity convertToEntity(UserModel user) {
        return mapper.map(user, UserEntity.class);
    }

    public UserModel convertToModel(UserEntity user) {
        return mapper.map(user, UserModel.class);
    }
}
