package com.example.unipath1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import java.util.ArrayList;

public class PickProfScreen extends AppCompatActivity {

    ArrayList<Professor> professors = new ArrayList<>();
    int[] prof_photos ={R.drawable.slimimage};

    DataBaseHelper dataBaseHelper;

    String receiver_subject;
    int student_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_prof_screen);

        RecyclerView rec_view =findViewById(R.id.my_rec_view);

        dataBaseHelper = new DataBaseHelper(this);
        Intent intent = getIntent();
        receiver_subject = intent.getStringExtra("clicked_subject");
        student_id = intent.getIntExtra("student_id", 10);
        professors = dataBaseHelper.getProfessors(receiver_subject);

        PickProfAdapter adapter = new PickProfAdapter(this, professors, receiver_subject, student_id);
        rec_view.setAdapter(adapter);
        rec_view.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setProfessor(){
        String[] names=getResources().getStringArray(R.array.prof_names);
        String[] phone_numbers=getResources().getStringArray(R.array.phone_numbers);
        String[] emails=getResources().getStringArray(R.array.emails);
        String[] scores=getResources().getStringArray(R.array.scores);

        for (int i=0; i<emails.length;i++) {
            professors.add(new Professor(names[i],emails[i],phone_numbers[i],Float.parseFloat(scores[i]) ,prof_photos[0])  );

        }
    }

}