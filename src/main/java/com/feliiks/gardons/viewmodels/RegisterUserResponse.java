package com.feliiks.gardons.viewmodels;

import com.feliiks.gardons.entities.User;

public class RegisterUserResponse {
    private User registeredUser;

    public RegisterUserResponse(User registeredUser) {
        this.registeredUser = registeredUser;
    }

    public User getRegisteredUser() {
        return registeredUser;
    }

    public void setRegisteredUser(User registeredUser) {
        this.registeredUser = registeredUser;
    }
}
