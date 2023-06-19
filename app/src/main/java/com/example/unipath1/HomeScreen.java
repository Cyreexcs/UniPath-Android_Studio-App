package com.example.unipath1;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.unipath1.databinding.ActivityHomeScreenBinding;

import java.util.ArrayList;

public class HomeScreen extends AppCompatActivity {

    //ArrayList<Subject> mySubjects = new ArrayList<>();
    ActivityHomeScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityHomeScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());
        String student_id = getIntent().getStringExtra("student_id");
        Toast toast =Toast.makeText(getApplicationContext(), "ID is : " + student_id,Toast.LENGTH_LONG);
        toast.show();
        binding.myNavi.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home) {
                replaceFragment(new HomeFragment());
            }
            else if (item.getItemId() == R.id.profile) {
                ProfileFragment profileFragment = new ProfileFragment();
                Bundle bundle = new Bundle();
                bundle.putString("student_id",student_id);
                profileFragment.setArguments(bundle);
                replaceFragment(profileFragment);

            }
            else if (item.getItemId() == R.id.leaderboard) {
                replaceFragment(new LeaderBoardFragment());
            }
            else if (item.getItemId() == R.id.exit) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();

            }

            return true;
        });

    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }



}