package com.feliiks.gardons.viewmodels;

import com.feliiks.gardons.entities.User;

public class PatchUserResponse {
    User patchedUser;

    public PatchUserResponse(User patchedUser) {
        this.patchedUser = patchedUser;
    }

    public User getPatchedUser() {
        return patchedUser;
    }

    public void setPatchedUser(User patchedUser) {
        this.patchedUser = patchedUser;
    }
}
