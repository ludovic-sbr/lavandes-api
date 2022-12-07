package com.feliiks.gardons.dtos;

import com.feliiks.gardons.entities.UserEntity;

import java.util.List;

public class GetUsersResponse {
    private List<UserEntity> users;

    public GetUsersResponse(List<UserEntity> users) {
        this.users = users;
    }

    public List<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(List<UserEntity> users) {
        this.users = users;
    }
}
