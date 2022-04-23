package com.hatstone.bobertapi.dto;

public class ContestWithAdmin {
    private Contest contest;
    private Boolean isAdmin;

    public ContestWithAdmin(Contest contest, Boolean isAdmin) {
        this.contest = contest;
        this.isAdmin = isAdmin;
    }

    public void setContest(Contest contest) { this.contest = contest; }
    public void setIsAdmin(Boolean isAdmin) { this.isAdmin = isAdmin; }
    public Contest getContest() { return contest; }
    public Boolean getIsAdmin() { return isAdmin; }
}