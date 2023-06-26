package com.example.demo.dto.request;

import java.time.LocalDate;
import java.util.Set;

public class SignUpForm {
    private Long id;
    private String name;
    private String username;
    private String email;
    private String password;
    private LocalDate birthday;
    private Set<String> roles;

    public SignUpForm() {
    }

    public SignUpForm(Long id, String name, String username, String email, String password, LocalDate birthday, Set<String> roles) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.birthday = birthday;
        this.roles = roles;
    }

    public SignUpForm(String name, String username, String email, String password, LocalDate birthday, Set<String> roles) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.birthday = birthday;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
