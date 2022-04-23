package com.hatstone.bobertapi.dto;

public class User {
    private String email;
    private Boolean admin;

    // Constructors //
    public User(String email, Boolean admin){
        this.email = email;
        this.admin = admin;
    }

    // Getters //
    public String getEmail() {return email;}
    public Boolean getAdmin() {return admin;}

    // Setters //
    public void setEmail(String email) {this.email = email;}
    public void setAdmin(Boolean admin) {this.admin = admin;}
}
