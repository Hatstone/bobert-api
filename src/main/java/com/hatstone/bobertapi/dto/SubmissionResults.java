package com.hatstone.bobertapi.dto;

public class SubmissionResults {
    private Long id;
    private Double portionCorrect;

    public SubmissionResults(Long id, Double portionCorrect){
        this.id = id;
        this.portionCorrect = portionCorrect;
    }

    public Long getId() {return id;}
    public Double getPortionCorrect() {return portionCorrect;}
    public void setId(Long id) {this.id = id;}
    public void setPortionCorrect(Double portionCorrect) {this.portionCorrect = portionCorrect;}
}