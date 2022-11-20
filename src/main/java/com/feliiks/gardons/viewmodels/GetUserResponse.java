package com.feliiks.gardons.viewmodels;

import com.feliiks.gardons.entities.User;

public class GetUserResponse {
    private User user;

    public GetUserResponse(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
