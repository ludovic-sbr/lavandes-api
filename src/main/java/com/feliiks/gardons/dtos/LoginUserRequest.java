package com.feliiks.gardons.dtos;

import lombok.Data;

@Data
public class LoginUserRequest {
    String email;
    String password;
    String google_id;
}
