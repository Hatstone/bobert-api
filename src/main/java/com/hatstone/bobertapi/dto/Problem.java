package com.hatstone.bobertapi.dto;

public class Problem {
    private String language;
    private String sourceCode;
    private String inputCode;
    private int timeLimit;
    private int memoryLimit;
    private String status;
    private Long contestId;

    // Constructors //
    public Problem(String language, String sourceCode, String inputCode, int timeLimit, int memoryLimit, String status, Long contestId) {
        this.language = language;
        this.sourceCode = sourceCode;
        this.inputCode = inputCode;
        this.timeLimit = timeLimit;
        this.memoryLimit = memoryLimit;
        this.status = status;
        this.contestId = contestId;
    }

    // Getters //
    public String getLanguage() {return language;}
    public String getSourceCode() {return sourceCode;}
    public String getInputCode() {return inputCode;}
    public int getTimeLimit() {return timeLimit;}
    public int getMemoryLimit() {return memoryLimit;}
    public String getStatus() {return status;}
    public Long getContestId() {return contestId;}

    // Setters //
    public void setLanguage(String language) {this.language = language;}
    public void setSourceCode(String sourceCode) {this.sourceCode = sourceCode;}
    public void setInputCode(String inputCode) {this.inputCode = inputCode;}
    public void setTimeLimit(int timeLimit) {this.timeLimit = timeLimit;}
    public void setMemoryLimit(int memoryLimit) {this.memoryLimit = memoryLimit;}
    public void setStatus(String status) {this.status = status;}
    public void setContestId(Long contestId) {this.contestId = contestId;}
}
