package com.example.unipath1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class RatingScreen extends AppCompatActivity {

    Button rateBtn;

    ArrayList<Feedback>studentsFeedback = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_screen);

        rateBtn = findViewById(R.id.rateBtn);
        fillStudentFeedback();
        RecyclerView feedbackRecycleView = findViewById(R.id.feedbackRecyclerView);
        FeedbackAdapter feedback_adapter = new FeedbackAdapter(this, studentsFeedback);
        feedbackRecycleView.setAdapter(feedback_adapter);
        feedbackRecycleView.setLayoutManager(new LinearLayoutManager(this));


        rateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SubmitScreen.class);
                startActivity(intent);
            }
        });
    }

    public void fillStudentFeedback(){
        String names[] = getResources().getStringArray(R.array.studentNames);
        String feedback[] = getResources().getStringArray(R.array.feedback);
        int rating[] = getResources().getIntArray(R.array.rating);

        for(int i=0; i< names.length; i++){
            studentsFeedback.add(new Feedback(names[i],feedback[i],rating[i]));
        }


    }
}