package com.feliiks.gardons.viewmodels;

import lombok.Data;

@Data
public class PatchUserRequest {
    String firstname;
    String lastname;
    String email;
    String password;
    String tel;
    String roleName;
}
