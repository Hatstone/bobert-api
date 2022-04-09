package com.hatstone.bobertapi.dto;

import java.util.ArrayList;

public class Problem {
    private String title;
    private String description;
    private Long timeLimit;
    private Long memoryLimit;
    private Long contestId;

    // Constructors //
    public Problem(String title, String description, Long timeLimit, Long memoryLimit, Long contestId) {
        this.description = description;
        this.title = title;
        this.timeLimit = timeLimit;
        this.memoryLimit = memoryLimit;
        this.contestId = contestId;
    }

    // Getters //
    public String getTitle() {return title;}
    public String getDescription() {return description;}
    public Long getTimeLimit() {return timeLimit;}
    public Long getMemoryLimit() {return memoryLimit;}
    public Long getContestId() {return contestId;}

    // Setters //
    public void setTitle(String title) {this.title = title;}
    public void setDescription(String description) {this.description = description;}
    public void setTimeLimit(Long timeLimit) {this.timeLimit = timeLimit;}
    public void setMemoryLimit(Long memoryLimit) {this.memoryLimit = memoryLimit;}
    public void setContestId(Long contestId) {this.contestId = contestId;}
}
