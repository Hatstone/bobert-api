package com.hatstone.bobertapi.dto;

import java.util.ArrayList;

public class Problem {
    private Long id;
    private String title;
    private String description;
    private Long contestId;

    // Constructors //
    public Problem(Long id, String title, String description, Long contestId) {
        this.id = id;
        this.description = description;
        this.title = title;
        this.contestId = contestId;
    }

    // Getters //
    public Long getId() {return id;}
    public String getTitle() {return title;}
    public String getDescription() {return description;}
    public Long getContestId() {return contestId;}

    // Setters //
    public void setId(Long id) {this.id = id;}
    public void setTitle(String title) {this.title = title;}
    public void setDescription(String description) {this.description = description;}
    public void setContestId(Long contestId) {this.contestId = contestId;}
}
