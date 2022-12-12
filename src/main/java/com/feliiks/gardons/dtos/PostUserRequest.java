package com.feliiks.gardons.dtos;

import lombok.Data;

@Data
public class PostUserRequest {
    String firstname;
    String lastname;
    String email;
    String password;
    String repeat_password;
    String tel;
    String google_id;
}
