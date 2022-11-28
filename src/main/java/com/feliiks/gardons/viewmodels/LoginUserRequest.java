package com.feliiks.gardons.viewmodels;

import lombok.Data;

@Data
public class LoginUserRequest {
    String email;
    String password;
    String googleId;
}
