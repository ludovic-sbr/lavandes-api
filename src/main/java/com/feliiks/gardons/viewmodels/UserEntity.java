package com.feliiks.gardons.viewmodels;

public class UserEntity {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String tel;
    private RoleEntity role;
    private String google_id;
    private Boolean is_user_completed;

    public UserEntity() {
    }

    public UserEntity(
            String firstname,
            String lastname,
            String email,
            String password,
            String tel,
            RoleEntity role,
            String google_id,
            boolean is_user_completed) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.tel = tel;
        this.role = role;
        this.google_id = google_id;
        this.is_user_completed = is_user_completed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public RoleEntity getRole() {
        return role;
    }

    public void setRole(RoleEntity role) {
        this.role = role;
    }

    public String getGoogle_id() {
        return google_id;
    }

    public void setGoogle_id(String google_id) {
        this.google_id = google_id;
    }

    public Boolean getIs_user_completed() {
        return is_user_completed;
    }

    public void setIs_user_completed(Boolean is_user_completed) {
        this.is_user_completed = is_user_completed;
    }
}
