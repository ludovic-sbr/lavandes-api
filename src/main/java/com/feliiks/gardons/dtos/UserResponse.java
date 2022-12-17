package com.feliiks.gardons.dtos;

import com.feliiks.gardons.entities.UserEntity;

public class UserResponse {
    private UserEntity user;

    public UserResponse(UserEntity user) {
        this.user = user;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
