package com.hatstone.bobertapi.dto;

import java.util.List;
import com.hatstone.bobertapi.dto.Problem;

public class ProblemWithTestCases {
    private Problem problem;
    private List<String> testCases;
    private List<String> testCaseOutcomes;

    public ProblemWithTestCases(String title, String description, Long contestId, List<String> testCases, List<String> testCaseOutcomes){
        problem = new Problem((long)0, title, description, contestId);
        this.testCases = testCases;
        this.testCaseOutcomes = testCaseOutcomes;
    }

    public Problem getProblem() {return problem;}
    public List<String> getTestCases() {return testCases;}
    public List<String> getTestCaseOutcomes() {return testCaseOutcomes;}

    public void setProblem(Problem problem) {this.problem = problem;}
    public void setTestCases(List<String> testCases) {this.testCases = testCases;}
    public void setTestCaseOutcomes(List<String> testCaseOutcomes) {this.testCaseOutcomes = testCaseOutcomes;}
}