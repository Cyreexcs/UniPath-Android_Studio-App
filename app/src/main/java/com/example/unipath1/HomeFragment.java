package com.example.unipath1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    Button nextBtn, preBtn;
    TextView nameView;
    RecyclerView subjectRecyclerView;
    DataBaseHelper dataBaseHelper;
    subjectAdapter mySubAdapter;

    final int LAST_SEMESTER = 6, FIRST_SEMESTER = 1;
    int current_semester;
    String current_student_id, student_name;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    ArrayList<Subject> mySubjects = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    private void setSubjects() {
        String[] subjects = getResources().getStringArray(R.array.subjects);
        for (int i = 0; i < subjects.length; i++) {
            mySubjects.add(new Subject(subjects[i]));
        }
    }
    CardView c1;

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view,
                              Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        // database
        dataBaseHelper = new DataBaseHelper(this.getContext());

        // getting the subjectlist
        Intent intent = getActivity().getIntent();
        current_semester = intent.getIntExtra("semester_id", 1);
        current_student_id = intent.getStringExtra("student_id");

        student_name = dataBaseHelper.getStudentName(current_student_id);

        nameView = view.findViewById(R.id.name_view);
        nameView.setText("Hello " + student_name);

        mySubjects = dataBaseHelper.getSubjects(current_semester);

        // Assign subjectlist to ItemAdapter
        mySubAdapter = new subjectAdapter(mySubjects);

        // Set the LayoutManager that
        // this RecyclerView will use.
        subjectRecyclerView = view.findViewById(R.id.subjectsRv);


        // adapter instance is set to the
        // recyclerview to inflate the items.
        subjectRecyclerView.setAdapter(mySubAdapter);
        subjectRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        nextBtn = view.findViewById(R.id.nextBtn);
        preBtn = view.findViewById(R.id.previousBtn);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (current_semester < LAST_SEMESTER - 1) {
                    current_semester++;
                    mySubjects = dataBaseHelper.getSubjects(current_semester);
                    subjectAdapter adapter = new subjectAdapter(mySubjects);
                    subjectRecyclerView.setAdapter(adapter);
                }
            }
        });

        preBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (current_semester > FIRST_SEMESTER) {
                    current_semester--;
                    mySubjects = dataBaseHelper.getSubjects(current_semester);
                    subjectAdapter adapter = new subjectAdapter(mySubjects);
                    subjectRecyclerView.setAdapter(adapter);
                }
            }
        });


    }

    public void GoTo() {
        Intent intent = new Intent(getActivity(), PickProfScreen.class);
        startActivity(intent);
    }

}

