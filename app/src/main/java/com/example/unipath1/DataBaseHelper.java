package com.example.unipath1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import kotlin.contracts.Returns;

public class DataBaseHelper extends SQLiteOpenHelper {

    public DataBaseHelper(@Nullable Context context) {
        super(context, "uniPathDB_1.db", null, 1);
        //setupProfRating();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*
        db.execSQL("DROP TABLE IF EXISTS "+ SUBJECT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS PROFESSORS_TABLE");
        db.execSQL("DROP TABLE IF EXISTS PERSON");
        onCreate(db);
        */
    }
    public ArrayList<Subject> getSubjects(int semesterId) {
        ArrayList<Subject> returnList = new ArrayList<>();

        // get data from the database
        String queryString = "SELECT * FROM Subject WHERE semester_id = " + semesterId + " ;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            do {
                String subjectName = cursor.getString(1);
                Subject subject = new Subject(subjectName, semesterId);
                returnList.add(subject);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return returnList;
    }

    public ArrayList<Professor> getProfessors(String subject) {
        ArrayList<Professor> returnList = new ArrayList<>();

        // Professor(String name, String email, String phone, String url_img)

        // get data from the database
        String queryString = "SELECT Professor.prof_id, prof_name, email, phone, image, rating FROM  Professor JOIN teaches ON Professor.prof_id = teaches.prof_id JOIN Subject ON teaches.subject_id = Subject.subject_id WHERE Subject.subject_name = " + "'"+subject+"'" + " ;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String email = cursor.getString(2);
                String phone = cursor.getString(3);
                String url_img = cursor.getString(4);
                double rating = cursor.getDouble(5);
                Professor prof = new Professor(id, name, email, phone, url_img, rating);
                returnList.add(prof);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return returnList;
    }

    public int checkUserData(String student_id, String password) {
        String queryString = "SELECT * FROM Student WHERE student_id = " + student_id + " AND password = " + "'"+password+"'" + " ;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst())
            return cursor.getInt(3);
        return -1;
    }

    public String getStudentName(int student_id) {
        String queryString = "SELECT * FROM Student WHERE student_id = " + student_id + ";";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        cursor.moveToFirst();
        return cursor.getString(2);
    }

    public int getSubjectId(String subject_name) {
        String queryString = "SELECT * FROM Subject WHERE subject_name = " + "'" + subject_name + "'" + ";";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        cursor.moveToFirst();
        return cursor.getInt(0);
    }

    public ArrayList<Feedback> getFeedbacks(int prof_id, int subject_id) {
        ArrayList<Feedback> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM Feedback WHERE prof_id = " + prof_id + " AND subject_id = " + subject_id + ";";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            do {
                double lecture_rating = cursor.getDouble(4);
                double lab_rating = cursor.getDouble(5);
                double exam_rating = cursor.getDouble(6);
                double help_rating = cursor.getDouble(7);
                String opinion = cursor.getString(8);
                Feedback feedback = new Feedback(opinion, lecture_rating, lab_rating, exam_rating, help_rating);
                returnList.add(feedback);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return returnList;
    }

    public double[] getProfAvgRating(int prof_id, int subject_id) {
        String queryString = "SELECT AVG(lecture_rating), AVG(lab_rating), AVG(exam_rating), AVG(helpfulness_rating) FROM Feedback WHERE prof_id = "+ prof_id +" AND subject_id = " + subject_id + ";";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        cursor.moveToFirst();
        double lecture_rating = cursor.getDouble(0);
        double lab_rating = cursor.getDouble(1);
        double exam_rating = cursor.getDouble(2);
        double help_rating = cursor.getDouble(3);

        cursor.close();
        db.close();
        return new double[]{lecture_rating, lab_rating, exam_rating, help_rating, (lecture_rating+lab_rating+exam_rating+help_rating)/4};
    }

    public void submitFeedback(Feedback feedback) {
        int feedback_id = getFeedbackId(feedback);
        String queryString;
        if (feedback_id == -1) {
            queryString = "INSERT INTO Feedback (student_id, subject_id, prof_id, lecture_rating, lab_rating, exam_rating, helpfulness_rating, opinion) VALUES " +
                    "(" + feedback.getStudent_id() +", " + feedback.getSubject_id() +"," + feedback.getProf_id() + ", " + feedback.getLecture_rating() + ", " + feedback.getLab_rating() + "," + feedback.getExam_rating() +"," + feedback.getHelpfulness_rating() + ", " + "'" + feedback.getOpinion() + "'" + ");";
        } else {
            queryString = "UPDATE Feedback SET lecture_rating = " + feedback.getLecture_rating() + ", lab_rating = " + feedback.getLab_rating() + ", exam_rating = " + feedback.getExam_rating() +", helpfulness_rating = " + feedback.getHelpfulness_rating() + ", opinion = " + "'" + feedback.getOpinion() + "'"
                    + " WHERE feedback_id = " + feedback_id +";";
        }

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(queryString);

        String updateQuery = "UPDATE Professor SET rating = " + updateProfRating(feedback, feedback_id) + " WHERE prof_id = " + feedback.getProf_id() + ";";
        db.execSQL(updateQuery);

        db.close();
    }

    public int getFeedbackId(Feedback feedback) {
        String queryString = "SELECT feedback_id FROM Feedback WHERE prof_id = "+ feedback.getProf_id() +" AND subject_id = " + feedback.getSubject_id() + " AND student_id = " + feedback.getStudent_id() + ";";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        boolean isEmpty = !cursor.moveToFirst();
        if (isEmpty) {
            System.out.println("new feedback");
            return -1;
        }
        return cursor.getInt(0);
    }

    private void setupProfRating() {
        String queryString = "SELECT prof_id, AVG(lecture_rating), AVG(lab_rating), AVG(exam_rating), AVG(helpfulness_rating) FROM Feedback GROUP BY prof_id;";
        SQLiteDatabase db = this.getReadableDatabase();
        SQLiteDatabase _db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            do {
                int prof_id = cursor.getInt(0);
                double lecture_rating = cursor.getDouble(1);
                double lab_rating = cursor.getDouble(2);
                double exam_rating = cursor.getDouble(3);
                double help_rating = cursor.getDouble(4);
                double rating = (lecture_rating+lab_rating+exam_rating+help_rating)/4;
                String insertQuery = "UPDATE Professor SET rating = " + rating + " WHERE prof_id = " + prof_id + ";";
                _db.execSQL(insertQuery);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        _db.close();
    }

    private double updateProfRating(Feedback feedback, int feedback_id) {
        String queryString = "SELECT rating FROM Professor WHERE prof_id = " + feedback.getProf_id() + ";";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        double rating = cursor.getDouble(0);

        // feedback counts
        String queryString2 = "SELECT count(feedback_id) FROM Feedback WHERE prof_id = " + feedback.getProf_id() + ";";
        cursor = db.rawQuery(queryString2, null);
        int n = cursor.getInt(0);

        // old avg rating
        String queryString3 = "SELECT AVG(lecture_rating), AVG(lab_rating), AVG(exam_rating), AVG(helpfulness_rating) FROM Feedback WHERE feedback_id = "+ feedback_id + ";";
        cursor = db.rawQuery(queryString3, null);
        double lecture_rating = cursor.getDouble(0);
        double lab_rating = cursor.getDouble(1);
        double exam_rating = cursor.getDouble(2);
        double help_rating = cursor.getDouble(3);
        double _rating = (lecture_rating+lab_rating+exam_rating+help_rating)/4;

        if (feedback_id != -1) {
            rating += (rating - _rating) / (n - 1);
        }
        rating += (feedback.getRating() - rating) / (n+1);


        cursor.close();
        db.close();
        return rating;
    }

    public ArrayList<Professor> getTopProfessors(int n) {
        ArrayList<Professor> returnList = new ArrayList<>();

        // Professor(String name, String email, String phone, String url_img)
        // rank , img, name, rating

        // get data from the database
        String queryString = "SELECT prof_name, image, rating FROM  Professor ORDER BY rating DESC LIMIT " + n + ";";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(0);
                String url_img = cursor.getString(1);
                double rating = cursor.getDouble(2);
                Professor prof = new Professor(name, url_img, rating);
                returnList.add(prof);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return returnList;
    }

    /*
    for the profile screen
    public Student getStudent(String student_id) {

    }
    */
}
