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
        super(context, "uniPath2_copy.db", null, 1);
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
        String queryString = "SELECT * FROM Student WHERE student_id = " + "'"+ student_id + "'"+ " AND password = " + "'"+password+"'" + " ;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst())
            return cursor.getInt(3);
        return -1;
    }

    public String getStudentName(String student_id) {
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
                double lecture_rating = cursor.getDouble(3);
                double lab_rating = cursor.getDouble(4);
                double exam_rating = cursor.getDouble(5);
                double help_rating = cursor.getDouble(6);
                String opinion = cursor.getString(7);
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
    public void update_or_add_feedback(String opinion, int student_id, int prof_id, int subject_id, double lecture_rating, double lab_rating, double exam_rating, double helpfulness_rating){
        String queryString = "REPLACE INTO Feedback values ( " + student_id + " , "+ subject_id + " , "+ prof_id + " , "+ lecture_rating + " , "+lab_rating + " , "+ exam_rating + " , "+ helpfulness_rating + " , ' "+ opinion + " ' )" + ";";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        cursor.moveToFirst();
        cursor.close();
        db.close();
    }
    public Student getStudent(int student_id){
        String queryString = "SELECT * FROM Student WHERE student_id = " + student_id + ";";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        Student student = new Student();
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(2);
                String nationality = cursor.getString(5);
                String address = cursor.getString(6);
                int age = cursor.getInt(7);
                String uni = cursor.getString(8);
                String url_img = cursor.getString(4);
                String deg = cursor.getString(9);
                student = new Student(name,uni,deg,nationality,age,url_img,student_id,address);
            }
            while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return student;
    }
    /*
    for the profile screen
    public Student getStudent(String student_id) {

    }
    */
}
