package com.feliiks.gardons.dtos;

import lombok.Data;

@Data
public class UserRequest {
    String firstname;
    String lastname;
    String email;
    String password;
    String repeat_password;
    String google_id;
    String roleName;
}
