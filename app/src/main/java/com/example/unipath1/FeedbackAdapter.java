package com.example.unipath1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class  FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.MyViewHolder> {

    Context context;
    ArrayList<Feedback>studentsFeedback;

    public FeedbackAdapter(Context context, ArrayList<Feedback> studentsFeedback) {
        this.context = context;
        this.studentsFeedback = studentsFeedback;
    }



    @NonNull
    @Override
    public FeedbackAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.feedback_recycler_view_row, parent, false);
        return new FeedbackAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.studentName.setText(studentsFeedback.get(position).getStudentName());
        holder.studentFeedback.setText(studentsFeedback.get(position).getStudentFeedback());
        System.out.println(studentsFeedback.get(position).getStudentRating());
        holder.rating.setRating(studentsFeedback.get(position).getStudentRating());

    }

    @Override
    public int getItemCount() {
        return studentsFeedback.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        RatingBar rating;
        TextView studentName, studentFeedback;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            rating = itemView.findViewById(R.id.studentRatingStars);
            studentName = itemView.findViewById(R.id.studentName);
            studentFeedback = itemView.findViewById(R.id.feedbacktxt);
        }
    }
}
