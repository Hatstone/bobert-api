package com.hatstone.bobertapi.dto;

import java.io.Serializable;

public class RunObject implements Serializable {
    private String language;
    private String code;
    private String args;

    public RunObject(String language, String code, String args) {
        this.language = language;
        this.code = code;
        this.args = args;
    }

    public String getLanguage() {return language;}
    public void setLanguage(String language) {this.language = language;}
    public String getCode() {return code;}
    public void setCode(String code) {this.code = code;}
    public String getArgs() {return args;}
    public void setArgs(String args) {this.args = args;}
}
