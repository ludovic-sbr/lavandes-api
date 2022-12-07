package com.feliiks.gardons.dtos;

import com.feliiks.gardons.entities.UserEntity;

public class PostUserResponse {
    private UserEntity user;

    public PostUserResponse(UserEntity user) {
        this.user = user;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
