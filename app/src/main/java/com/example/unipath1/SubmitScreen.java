package com.example.unipath1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class SubmitScreen extends AppCompatActivity {

    TextView prof_name_view;
    ImageView prof_img_view;

    int prof_id, subject_id;
    String prof_name, prof_img;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_screen);

        prof_name_view = findViewById(R.id.nameTxt);
        prof_img_view = findViewById(R.id.prof_img);

        Intent intent = getIntent();
        prof_name = intent.getStringExtra("prof_name");
        prof_img = intent.getStringExtra("prof_img");

        prof_name_view.setText("Prof.Dr " + prof_name);
        if (prof_img == null)
            prof_img_view.setImageResource(R.drawable.default_profile_pic);
        else {
            Glide.with(this).asBitmap().load(prof_img).into(prof_img_view);
        }

    }
}