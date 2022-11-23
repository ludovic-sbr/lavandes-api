package com.feliiks.gardons.viewmodels;

import com.feliiks.gardons.entities.User;

public class PostUserResponse {
    private User registeredUser;

    public PostUserResponse(User registeredUser) {
        this.registeredUser = registeredUser;
    }

    public User getRegisteredUser() {
        return registeredUser;
    }

    public void setRegisteredUser(User registeredUser) {
        this.registeredUser = registeredUser;
    }
}
