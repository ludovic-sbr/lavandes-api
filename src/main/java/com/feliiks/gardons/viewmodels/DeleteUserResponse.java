package com.feliiks.gardons.viewmodels;

import com.feliiks.gardons.entities.User;

public class DeleteUserResponse {
    private User deletedUser;

    public DeleteUserResponse(User deletedUser) {
        this.deletedUser = deletedUser;
    }

    public User getDeletedUser() {
        return deletedUser;
    }

    public void setDeletedUser(User deletedUser) {
        this.deletedUser = deletedUser;
    }
}
