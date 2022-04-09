package com.hatstone.bobertapi.dto;

import java.util.ArrayList;

public class Problem {
    private String language;
    private Long timeLimit;
    private Long memoryLimit;
    private Long contestId;

    // Constructors //
    public Problem(String language, Long timeLimit, Long memoryLimit, Long contestId) {
        this.language = language;
        this.timeLimit = timeLimit;
        this.memoryLimit = memoryLimit;
        this.contestId = contestId;
    }

    // Getters //
    public String getLanguage() {return language;}
    public Long getTimeLimit() {return timeLimit;}
    public Long getMemoryLimit() {return memoryLimit;}
    public Long getContestId() {return contestId;}

    // Setters //
    public void setLanguage(String language) {this.language = language;}
    public void setTimeLimit(Long timeLimit) {this.timeLimit = timeLimit;}
    public void setMemoryLimit(Long memoryLimit) {this.memoryLimit = memoryLimit;}
    public void setContestId(Long contestId) {this.contestId = contestId;}
}
