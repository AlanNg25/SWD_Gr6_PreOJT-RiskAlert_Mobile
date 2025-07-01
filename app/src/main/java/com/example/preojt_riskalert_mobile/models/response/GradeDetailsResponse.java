package com.example.preojt_riskalert_mobile.models.response;

public class GradeDetailsResponse {
    private String gradeDetailID;
    private String gradeID;
    private String gradeType;
    private double score;
    private double scoreWeight;
    private double minScr;
    private boolean isDeleted;

    public String getGradeDetailID() {
        return gradeDetailID;
    }

    public void setGradeDetailID(String gradeDetailID) {
        this.gradeDetailID = gradeDetailID;
    }

    public String getGradeID() {
        return gradeID;
    }

    public void setGradeID(String gradeID) {
        this.gradeID = gradeID;
    }

    public String getGradeType() {
        return gradeType;
    }

    public void setGradeType(String gradeType) {
        this.gradeType = gradeType;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public double getScoreWeight() {
        return scoreWeight;
    }

    public void setScoreWeight(double scoreWeight) {
        this.scoreWeight = scoreWeight;
    }

    public double getMinScr() {
        return minScr;
    }

    public void setMinScr(double minScr) {
        this.minScr = minScr;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
