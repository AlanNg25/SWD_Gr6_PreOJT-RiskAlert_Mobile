package com.example.preojt_riskalert_mobile.models.response;

import androidx.annotation.Nullable;

import java.util.List;

public class GradeResponse {
    private String gradeID;
    private String studentID;
    private String courseID;
    private String gradeDate;
    private double scoreAverage;
    private boolean isDeleted;
    private Course course;
    private Semester semester;

    @Nullable
    public List<GradeDetailsResponse> getGradeDetails() {
        return gradeDetails;
    }

    public void setGradeDetails(@Nullable List<GradeDetailsResponse> gradeDetails) {
        this.gradeDetails = gradeDetails;
    }

    @Nullable
    private List<GradeDetailsResponse> gradeDetails;

    public String getGradeID() {
        return gradeID;
    }

    public void setGradeID(String gradeID) {
        this.gradeID = gradeID;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getGradeDate() {
        return gradeDate;
    }

    public void setGradeDate(String gradeDate) {
        this.gradeDate = gradeDate;
    }

    public double getScoreAverage() {
        return scoreAverage;
    }

    public void setScoreAverage(double scoreAverage) {
        this.scoreAverage = scoreAverage;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }
}
