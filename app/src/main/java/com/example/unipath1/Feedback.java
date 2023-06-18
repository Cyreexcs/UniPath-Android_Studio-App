package com.example.unipath1;

public class Feedback {
    private String studentName, studentFeedback;
    private int studentRating;


    public Feedback(String studentName, String studentFeedback, int studentRating) {
        this.studentName = studentName;
        this.studentFeedback = studentFeedback;
        this.studentRating = studentRating;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getStudentFeedback() {
        return studentFeedback;
    }

    public int getStudentRating() {
        return studentRating;
    }
}
