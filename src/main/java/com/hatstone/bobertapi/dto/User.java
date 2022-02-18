package com.hatstone.bobertapi.dto;

public class User {
    private String displayName;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Boolean admin;

    // Constructors //
    public User(String displayName, String firstName, String lastName, String email, String password, Boolean admin){
        this.displayName = displayName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.admin = admin;
    }

    // Getters //
    public String getDisplayName() {return displayName;}
    public String getFirstName() {return firstName;}
    public String getLastName() {return lastName;}
    public String getEmail() {return email;}
    public String getPassword() {return password;}
    public Boolean getAdmin() {return admin;}

    // Setters //
    public void setDisplayName(String displayName) {this.displayName = displayName;}
    public void setFirstName(String firstName) {this.firstName = firstName;}
    public void setLastName(String lastName) {this.lastName = lastName;}
    public void setEmail(String email) {this.email = email;}
    public void setPassword(String password) {this.password = password;}
    public void setAdmin(Boolean admin) {this.admin = admin;}
}
