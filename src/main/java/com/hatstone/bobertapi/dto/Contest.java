package com.hatstone.bobertapi.dto;

import java.util.ArrayList;
import java.util.List;

public class Contest {
    private String title;
    private List<Long> users;
    private List<Long> problems;

    // Constructors //
    public Contest(String title){
        this.title = title;
        this.users = new ArrayList<>();
        this.problems = new ArrayList<>();
    }

    public Contest(String title, List<Long> problems, List<Long> users){
        this.title = title;
        this.problems = problems;
        this.users = users;
    }

    // Getters //
    public String getTitle() {return title;}
    public List<Long> getUsers() {return users;}
    public List<Long> getProblems() {return problems;}

    // Setters //
    public void setTitle(String title) {this.title = title;}
}
