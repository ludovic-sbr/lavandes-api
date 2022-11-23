package com.feliiks.gardons.viewmodels;

import lombok.Data;

@Data
public class PostUserRequest {
    String firstname;
    String lastname;
    String email;
    String password;
    String tel;
}
