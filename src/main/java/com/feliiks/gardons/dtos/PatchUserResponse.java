package com.feliiks.gardons.dtos;

import com.feliiks.gardons.entities.UserEntity;

public class PatchUserResponse {
    private UserEntity user;

    public PatchUserResponse(UserEntity user) {
        this.user = user;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
