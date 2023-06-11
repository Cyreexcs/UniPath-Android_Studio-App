package com.example.unipath1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class LeaderBoardScreen extends AppCompatActivity {

    ArrayList<Professor> topProfs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board_screen);

        setLeaderBoard();

        System.out.println(topProfs.get(1).getRating());

        RecyclerView top_profs_recycler = findViewById(R.id.top_profs_recycler);

        LeaderBoardAdapter myLeaderBoardAdapter = new LeaderBoardAdapter(topProfs);
        top_profs_recycler.setAdapter(myLeaderBoardAdapter);
        top_profs_recycler.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setLeaderBoard() {
        int[] ranks = {R.drawable.gold_medal, R.drawable.gold_medal, R.drawable.gold_medal, R.drawable.silver_medal, R.drawable.silver_medal,R.drawable.silver_medal, R.drawable.bronze_medal, R.drawable.bronze_medal, R.drawable.bronze_medal, R.drawable.bronze_medal};
        int[] prof_img = {R.drawable.baseline_account_circle_24};
        String[] prof_names = getResources().getStringArray(R.array.prof_names);

        for (int i = 0; i < 10; i++) {
            topProfs.add(new Professor(prof_names[i], 4 , prof_img[0], ranks[i]));
        }
    }
}