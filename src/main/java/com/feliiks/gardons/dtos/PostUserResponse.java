package com.feliiks.gardons.dtos;

import com.feliiks.gardons.viewmodels.UserEntity;

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
