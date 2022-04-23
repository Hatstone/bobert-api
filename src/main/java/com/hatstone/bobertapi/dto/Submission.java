package com.hatstone.bobertapi.dto;

import java.io.Serializable;

public class Submission {
    private Long userId;
    private Long problemId;
    private byte[] data;
    private String language;

    // Constructors //
    public Submission(Long userId, Long problemId, byte[] data, String language){
        this.userId = userId;
        this.problemId = problemId;
        this.data = data;
        this.language = language;
    }

    // Getters //
    public Long getUserId() {return userId;}
    public Long getProblemId() {return problemId;}
    public byte[] getData() {return data;}
    public String getLanguage() {return language;}

    // Setters //
    public void setUserId(Long userId) {this.userId = userId;}
    public void setProblemId(Long problemId) {this.problemId = problemId;}
    public void setData(byte[] data) {this.data = data;}
    public void setLanguage(String language) {this.language = language;}
}
