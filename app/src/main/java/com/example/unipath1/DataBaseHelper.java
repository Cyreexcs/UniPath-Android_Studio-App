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
    public static final String SUBJECT_TABLE = "SUBJECT_TABLE";
    public static final String COLUMN_SUBJECT_NAME ="SUBJECT_NAME";
    public static final String COLUMN_SEMESTER_ID = "SEMESTER_ID";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "uniPath.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /*
        String createTableStatement = "CREATE TABLE " + SUBJECT_TABLE + " (" + COLUMN_SUBJECT_NAME + " TEXT PRIMARY KEY, " + COLUMN_SEMESTER_ID + " INTEGER)";
        db.execSQL(createTableStatement);
        db.execSQL("CREATE TABLE PERSON (NAME TEXT PRIMARY KEY, AGE INT)");
        */
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ SUBJECT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS PROFESSORS_TABLE");
        db.execSQL("DROP TABLE IF EXISTS PERSON");
        onCreate(db);
    }


    public boolean insertSubject(String subject, int semesterId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_SUBJECT_NAME, subject);
        cv.put(COLUMN_SEMESTER_ID, semesterId);

        long insert = db.insert(SUBJECT_TABLE, null, cv);
        return insert != -1;
    }

    public ArrayList<Subject> getSubjects(int semesterId) {
        ArrayList<Subject> returnList = new ArrayList<>();

        // get data from the database
        String queryString = "SELECT * FROM " + SUBJECT_TABLE + " WHERE " + COLUMN_SEMESTER_ID + " = " + semesterId + " ;";
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
        String queryString = "SELECT * FROM  PROFESSOR_TABLE WHERE PROF_SUBJECT = " + "'"+subject+"'" + " ;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(0);
                String email = cursor.getString(2);
                String phone = cursor.getString(3);
                String url_img = cursor.getString(4);
                Professor prof = new Professor(name, email, phone, url_img);
                returnList.add(prof);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return returnList;
    }

    public int checkUserData(String user, String password) {
        String queryString = "SELECT * FROM STUDENT_TABLE WHERE MATRIKEL_NUM = " + "'"+user+"'" + " AND PASSWORD = " + "'"+password+"'" + " ;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst())
            return cursor.getInt(2);
        return -1;
    }

}
