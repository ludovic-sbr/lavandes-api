package com.feliiks.gardons.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "users")
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    private String firstname;
    private String lastname;
    @NotBlank
    private String email;
    private String password;
    private String tel;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "role_id", nullable = false)
    private RoleModel role;
    private String google_id;
    @NotBlank
    private boolean is_user_completed;

    public UserModel() {
        super();
    }

    public UserModel(
            String firstname,
            String lastname,
            String email,
            String password,
            String tel,
            RoleModel role,
            String google_id,
            boolean is_user_completed) {
        super();
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

    public RoleModel getRole() {
        return role;
    }

    public void setRole(RoleModel role) {
        this.role = role;
    }

    public String getGoogle_id() {
        return google_id;
    }

    public void setGoogle_id(String google_id) {
        this.google_id = google_id;
    }

    public boolean isIs_user_completed() {
        return is_user_completed;
    }

    public void setIs_user_completed(boolean is_user_completed) {
        this.is_user_completed = is_user_completed;
    }
}
