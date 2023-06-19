package com.example.unipath1;

public class Feedback {
    private String student_name, opinion;
    private int studentRating;

    private int student_id;

    public Feedback(String opinion, int student_id, int prof_id, int subject_id, double lecture_rating, double lab_rating, double exam_rating, double helpfulness_rating) {
        this.opinion = opinion;
        this.student_id = student_id;
        this.prof_id = prof_id;
        this.subject_id = subject_id;
        this.lecture_rating = lecture_rating;
        this.lab_rating = lab_rating;
        this.exam_rating = exam_rating;
        this.helpfulness_rating = helpfulness_rating;
    }

    private int prof_id;
    private int subject_id;

    private double lecture_rating, lab_rating, exam_rating, helpfulness_rating, rating;

    public Feedback(String opinion, double lecture_rating, double lab_rating, double exam_rating, double helpfulness_rating) {
        this.opinion = opinion;
        this.lecture_rating = lecture_rating;
        this.lab_rating = lab_rating;
        this.exam_rating = exam_rating;
        this.helpfulness_rating = helpfulness_rating;
    }

    public Feedback(String studentName, String studentFeedback, int studentRating) {
        this.student_name = studentName;
        this.opinion = studentFeedback;
        this.studentRating = studentRating;
    }

    public String getStudent_name() {
        return student_name;
    }

    public String getOpinion() {
        return opinion;
    }

    public double getStudentRating() {
        return (lecture_rating + lab_rating + exam_rating + helpfulness_rating ) /4;
    }

    public int getStudent_id() {
        return student_id;
    }

    public int getProf_id() {
        return prof_id;
    }

    public double getLecture_rating() {
        return lecture_rating;
    }

    public double getLab_rating() {
        return lab_rating;
    }

    public double getExam_rating() {
        return exam_rating;
    }

    public double getHelpfulness_rating() {
        return helpfulness_rating;
    }

    public double getRating() {
        return rating;
    }
}
