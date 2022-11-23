package com.feliiks.gardons.entities;

import java.util.Date;

public class Token {
    private Long userId;

    private String username;

    private Date validity;

    private String value;

    public Token() {
    }

    public Token(Long userId, String username, Date validity, String value) {
        this.userId = userId;
        this.username = username;
        this.validity = validity;
        this.value = value;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getValidity() {
        return validity;
    }

    public void setValidity(Date validity) {
        this.validity = validity;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
