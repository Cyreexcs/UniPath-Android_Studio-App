package com.example.unipath1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    TextView nameView;
    RecyclerView subjectRecyclerView;
    DataBaseHelper dataBaseHelper;
    subjectAdapter mySubAdapter;

    static Intent intent;
    final int LAST_SEMESTER = 6, FIRST_SEMESTER = 1;
    int current_semester;
    int student_id;
    String student_name;

    //chips

    Chip semester_1_chip, semester_2_chip, semester_3_chip, semester_4_chip, semester_5_chip;

    ChipGroup chipGroup;

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
                              Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // database
        dataBaseHelper = new DataBaseHelper(this.getContext());

        // getting the subjectlist
        intent = getActivity().getIntent();
        current_semester = intent.getIntExtra("semester_id", 1);
        student_id = intent.getIntExtra("student_id", 10);

        student_name = dataBaseHelper.getStudentName(student_id);

        nameView = view.findViewById(R.id.name_view);
        nameView.setText("Hello " + student_name);

        mySubjects = dataBaseHelper.getSubjects(current_semester);

        // Assign subjectlist to ItemAdapter
        mySubAdapter = new subjectAdapter(mySubjects, student_id);

        // Set the LayoutManager that
        // this RecyclerView will use.
        subjectRecyclerView = view.findViewById(R.id.subjectsRv);


        // adapter instance is set to the
        // recyclerview to inflate the items.
        subjectRecyclerView.setAdapter(mySubAdapter);
        subjectRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));


        semester_1_chip = view.findViewById(R.id.semester_1);
        semester_2_chip = view.findViewById(R.id.semester_2);
        semester_3_chip = view.findViewById(R.id.semester_3);
        semester_4_chip = view.findViewById(R.id.semester_4);
        semester_5_chip = view.findViewById(R.id.semester_5);

        chipGroup = view.findViewById(R.id.chipGroup);

        CompoundButton.OnCheckedChangeListener checkedChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    int selectedChip = Integer.parseInt(buttonView.getText().toString());
                    Toast.makeText(getContext(), "selectedChip", Toast.LENGTH_SHORT).show();
                    subjectAdapter newSubjectAdapter;
                    mySubjects = dataBaseHelper.getSubjects(selectedChip);
                    newSubjectAdapter = new subjectAdapter(mySubjects, student_id);
                    subjectRecyclerView.setAdapter(newSubjectAdapter);
                }
            }
        };

        semester_1_chip.setOnCheckedChangeListener(checkedChangeListener);
        semester_2_chip.setOnCheckedChangeListener(checkedChangeListener);
        semester_3_chip.setOnCheckedChangeListener(checkedChangeListener);
        semester_4_chip.setOnCheckedChangeListener(checkedChangeListener);
        semester_5_chip.setOnCheckedChangeListener(checkedChangeListener);

    }

}

