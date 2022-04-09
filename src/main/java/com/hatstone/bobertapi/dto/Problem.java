package com.hatstone.bobertapi.dto;

import java.util.ArrayList;

public class Problem {
    private String language;
    private byte[] sourceCode;
    private int timeLimit;
    private int memoryLimit;
    private Long contestId;

    // Constructors //
    public Problem(String language, byte[] sourceCode, int timeLimit, int memoryLimit, Long contestId) {
        this.language = language;
        this.sourceCode = sourceCode;
        this.timeLimit = timeLimit;
        this.memoryLimit = memoryLimit;
        this.contestId = contestId;
    }

    // Getters //
    public String getLanguage() {return language;}
    public byte[] getSourceCode() {return sourceCode;}
    public int getTimeLimit() {return timeLimit;}
    public int getMemoryLimit() {return memoryLimit;}
    public Long getContestId() {return contestId;}

    // Setters //
    public void setLanguage(String language) {this.language = language;}
    public void setSourceCode(byte[] sourceCode) {this.sourceCode = sourceCode;}
    public void setTimeLimit(int timeLimit) {this.timeLimit = timeLimit;}
    public void setMemoryLimit(int memoryLimit) {this.memoryLimit = memoryLimit;}
    public void setContestId(Long contestId) {this.contestId = contestId;}
}
