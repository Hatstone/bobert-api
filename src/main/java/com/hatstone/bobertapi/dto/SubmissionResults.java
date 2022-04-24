package com.hatstone.bobertapi.dto;

public class SubmissionResults {
    private Long id;
    private Float portionCorrect;

    public SubmissionResults(Long id, Float portionCorrect){
        this.id = id;
        this.portionCorrect = portionCorrect;
    }

    public Long getId() {return id;}
    public Float getPortionCorrect() {return portionCorrect;}
    public void setId(Long id) {this.id = id;}
    public void setPortionCorrect(Float portionCorrect) {this.portionCorrect = portionCorrect;}
}