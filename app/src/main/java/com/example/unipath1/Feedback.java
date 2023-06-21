package com.example.unipath1;

public class Feedback {
    private String student_name, opinion;
    private int studentRating;

    private int student_id, prof_id, subject_id;

    private double lecture_rating, lab_rating, exam_rating, helpfulness_rating, rating;

    public Feedback(String opinion, double lecture_rating, double lab_rating, double exam_rating, double helpfulness_rating) {
        this.opinion = opinion;
        this.lecture_rating = lecture_rating;
        this.lab_rating = lab_rating;
        this.exam_rating = exam_rating;
        this.helpfulness_rating = helpfulness_rating;
        this.rating = (lecture_rating + lab_rating + exam_rating + helpfulness_rating) / 4;
    }

    public Feedback(int student_id, int prof_id, int subject_id, double lecture_rating, double lab_rating, double exam_rating, double helpfulness_rating, String opinion) {
        this.student_id = student_id;
        this.prof_id = prof_id;
        this.subject_id = subject_id;
        this.lecture_rating = lecture_rating;
        this.lab_rating = lab_rating;
        this.exam_rating = exam_rating;
        this.helpfulness_rating = helpfulness_rating;
        this.rating = (lecture_rating + lab_rating + exam_rating + helpfulness_rating) / 4;
        this.opinion = opinion;
    }

    public String getOpinion() {
        return opinion;
    }

    public int getStudentRating() {
        return studentRating;
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

    public int getSubject_id() {
        return subject_id;
    }
}
