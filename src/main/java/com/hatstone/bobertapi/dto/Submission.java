package com.hatstone.bobertapi.dto;

public class Submission {
    private Long userid;
    private Long problemid;
    private String data;
    private String language;

    // Constructors //
    public Submission(Long userid, Long problemid, String data, String language){
        this.userid = userid;
        this.problemid = problemid;
        this.data = data;
        this.language = language;
    }

    // Getters //
    public Long getUserId() {return userid;}
    public Long getProblemId() {return problemid;}
    public String getData() {return data;}
    public String getLanguage() {return language;}

    // Setters //
    public void setUserId(Long userid) {this.userid = userid;}
    public void setProblemId(Long problemid) {this.problemid = problemid;}
    public void setData(String data) {this.data = data;}
    public void setLanguage(String language) {this.language = language;}
}
