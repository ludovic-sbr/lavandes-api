package com.feliiks.gardons.viewmodels;

import com.feliiks.gardons.entities.User;

public class PatchUserResponse {
    private User user;

    public PatchUserResponse(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
