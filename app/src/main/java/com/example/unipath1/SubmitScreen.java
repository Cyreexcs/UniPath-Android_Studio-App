package com.example.unipath1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.DragAndDropPermissions;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class SubmitScreen extends AppCompatActivity {

    TextView prof_name_view;
    ImageView prof_img_view;

    Button submit_button;

    int student_id, prof_id, subject_id;
    String prof_name, prof_img;

    RatingBar prof_lecture_rating_bar, prof_lab_rating_bar, prof_exam_rating_bar, prof_helpfulness_rating_bar;
    EditText opinionTxt;
    double prof_lecture_rating, prof_lab_rating, prof_exam_rating, prof_helpfulness_rating;
    String opinion;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_screen);

        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);

        prof_name_view = findViewById(R.id.nameTxt);
        prof_img_view = findViewById(R.id.prof_img);

        Intent intent = getIntent();
        prof_name = intent.getStringExtra("prof_name");
        prof_img = intent.getStringExtra("prof_img");

        student_id = intent.getIntExtra("student_id", 404);
        prof_id = intent.getIntExtra("prof_id", 404);
        subject_id = intent.getIntExtra("subject_id", 404);


        prof_name_view.setText("Prof.Dr " + prof_name);
        if (prof_img == null)
            prof_img_view.setImageResource(R.drawable.default_profile_pic);
        else {
            Glide.with(this).asBitmap().load(prof_img).into(prof_img_view);
        }

        prof_lecture_rating_bar = findViewById(R.id.lecture_bar);
        prof_lab_rating_bar = findViewById(R.id.lab_bar);
        prof_exam_rating_bar = findViewById(R.id.exam_bar);
        prof_helpfulness_rating_bar = findViewById(R.id.help_bar);
        opinionTxt = findViewById(R.id.opinion);

        submit_button = findViewById(R.id.submit_button);

        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkFeedback()) {
                    Feedback feedback = new Feedback(student_id, prof_id, subject_id, prof_lecture_rating, prof_lab_rating, prof_exam_rating, prof_helpfulness_rating, opinion);
                    dataBaseHelper.submitFeedback(feedback);
                    //before i can uncomment this i should overwrite all onBackPressed() functions
                    startActivity(PickProfAdapter.intent);
                }
                else
                    Toast.makeText(SubmitScreen.this, "invalid feedback", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private boolean checkFeedback() {
        prof_lecture_rating = prof_lecture_rating_bar.getRating();
        prof_lab_rating = prof_lab_rating_bar.getRating();
        prof_exam_rating = prof_exam_rating_bar.getRating();
        prof_helpfulness_rating = prof_helpfulness_rating_bar.getRating();
        opinion = opinionTxt.getText().toString();
        return prof_lecture_rating >= 1 && prof_lab_rating >= 1 && prof_exam_rating >= 1 && prof_helpfulness_rating >= 1;
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        startActivity(PickProfAdapter.intent);
    }
}

