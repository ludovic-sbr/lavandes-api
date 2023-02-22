package com.feliiks.gardons.dtos;

import lombok.Data;

@Data
public class PatchUserRequest {
    String firstname;
    String lastname;
    String email;
    String password;
    String repeat_password;
    String roleName;
}
