package com.feliiks.gardons.viewmodels;

import com.feliiks.gardons.entities.Role;
import lombok.Data;

@Data
public class PatchUserRequest {
    String firstname;
    String lastname;
    String email;
    String password;
    String tel;
    Role role;
}
