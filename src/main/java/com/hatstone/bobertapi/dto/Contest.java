package com.hatstone.bobertapi.dto;

import java.util.ArrayList;
import java.util.List;

public class Contest {
    private String title;
    private List<User> users;
    private List<Problem> problems;

    // Constructors //
    Contest(String title){
        this.title = title;
        this.users = new ArrayList<>();
        this.problems = new ArrayList<>();
    }

    // Getters //
    public String getTitle() {return title;}
    public List<User> getUsers() {return users;}
    public List<Problem> getProblems() {return problems;}

    // Setters //
    public void setTitle(String title) {this.title = title;}
}
