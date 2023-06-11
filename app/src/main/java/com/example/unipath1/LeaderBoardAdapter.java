package com.example.unipath1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LeaderBoardAdapter extends RecyclerView.Adapter<LeaderBoardAdapter.MyViewHolder> {
    private ArrayList<Professor> topProfs;

    public LeaderBoardAdapter(ArrayList<Professor> topProfs) {
        this.topProfs = topProfs;
    }

    @NonNull
    @Override
    public LeaderBoardAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.leaderboard_recycler_view_row, parent, false);
        return new LeaderBoardAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderBoardAdapter.MyViewHolder holder, int position) {
        holder.rank.setImageResource(topProfs.get(position).getRank());
        holder.prof_img.setImageResource(topProfs.get(position).getImage());
        holder.prof_name.setText(topProfs.get(position).getName());
        holder.ratingBar.setRating(4);
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView rank, prof_img;
        TextView prof_name;
        RatingBar ratingBar;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            rank = itemView.findViewById(R.id.rankingimg);
            prof_img = itemView.findViewById(R.id.profimg);
            prof_name = itemView.findViewById(R.id.profname);
            ratingBar = itemView.findViewById(R.id.ratingBar);
        }
    }
}
