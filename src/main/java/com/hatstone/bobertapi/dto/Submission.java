package com.hatstone.bobertapi.dto;

public class Submission {
    private Long userid;
    private Long problemid;
    private byte[] data;
    private String language;

    // Constructors //
    public Submission(Long userid, Long problemid, byte[] data, String language){
        this.userid = userid;
        this.problemid = problemid;
        this.data = data;
        this.language = language;
    }

    // Getters //
    public Long getUserId() {return userid;}
    public Long getProblemId() {return problemid;}
    public byte[] getData() {return data;}
    public String getLanguage() {return language;}

    // Setters //
    public void setUserId(Long userid) {this.userid = userid;}
    public void setProblemId(Long problemid) {this.problemid = problemid;}
    public void setData(byte[] data) {this.data = data;}
    public void setLanguage(String language) {this.language = language;}
}
