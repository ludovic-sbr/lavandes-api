package com.feliiks.gardons.security;

public class AuthenticationUser {

    private boolean isPro;

    private String username;

    public AuthenticationUser(boolean isPro, String username) {
        this.isPro = isPro;
        this.username = username;
    }

    public boolean isPro() {
        return isPro;
    }

    public void setPro(boolean pro) {
        isPro = pro;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
