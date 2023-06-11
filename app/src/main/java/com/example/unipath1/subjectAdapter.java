package com.example.unipath1;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class subjectAdapter extends RecyclerView.Adapter<subjectAdapter.MyViewHolder> {
    private ArrayList<Subject> subjects;

    public subjectAdapter(ArrayList<Subject> subjects) {
        this.subjects = subjects;
    }

    @NonNull
    @Override
    public subjectAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subject_recycler_view_row, parent, false);
        return new subjectAdapter.MyViewHolder(view);
    }

    public void setSubjects(ArrayList<Subject> subjects) {
        this.subjects = subjects;
    }

    @Override
    public void onBindViewHolder(@NonNull subjectAdapter.MyViewHolder holder, int position) {
        holder.subBtn.setText(subjects.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        Button subBtn;
        String clicked_subject;
        public MyViewHolder(@NonNull View view) {
            super(view);

            subBtn = view.findViewById(R.id.subBtn);
            subBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clicked_subject = subBtn.getText().toString();

                    Intent intent = new Intent(subBtn.getContext(), PickProfScreen.class);
                    intent.putExtra("clicked_subject", clicked_subject);
                    subBtn.getContext().startActivity(intent);
                }
            });

        }
    }

}
