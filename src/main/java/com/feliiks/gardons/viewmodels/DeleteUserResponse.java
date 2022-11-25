package com.feliiks.gardons.viewmodels;

import com.feliiks.gardons.entities.User;

public class DeleteUserResponse {
    private User user;

    public DeleteUserResponse(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
