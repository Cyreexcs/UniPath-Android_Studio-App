package com.example.unipath1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class PickProfAdapter extends RecyclerView.Adapter<PickProfAdapter.MyViewHolder> {
    Context context;

    ArrayList<Professor> professors;

    String current_subject;

    public PickProfAdapter(Context context, ArrayList<Professor> professors, String current_subject) {
        this.context = context;
        this.professors = professors;
        this.current_subject = current_subject;
    }

    public PickProfAdapter(Context context , ArrayList<Professor> professors){
        this.context=context;
        this.professors=professors;
    }

    @NonNull
    @Override
    public PickProfAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.prof_recycler_view_row,parent,false);
        return new PickProfAdapter.MyViewHolder(view);
    }

    //assigning values back to the views when restored based on position
    @Override
    public void onBindViewHolder(@NonNull PickProfAdapter.MyViewHolder holder, int position) {
        holder.name.setText("Prof.Dr " + professors.get(position).getName());
        holder.email.setText((professors.get(position).getEmail()));
        holder.phone.setText((professors.get(position).getPhone()));
        //holder.rating.setText(Double.toString(professors.get(position).getRating()) ) ;
        holder.rating.setText(Double.toString(4));
        // img uploaded directly from google

        // if there is no image of the target then set the default one
        if (professors.get(position).getUrl_img() == null)
            holder.prof_img.setImageResource(R.drawable.default_profile_pic);
        else {
            Glide.with(context)
                    .asBitmap()
                    .load(professors.get(position).getUrl_img())
                    .into(holder.prof_img);
        }

        holder.profCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.profCardView.getContext(), RatingScreen.class);
                intent.putExtra("current_subject", current_subject);
                holder.profCardView.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return professors.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView prof_img;
        TextView name , email, phone , rating;
        CardView profCardView;
        public MyViewHolder(@NonNull View view) {
            super(view);
            prof_img = view.findViewById(R.id.photo_1);
            name = view.findViewById(R.id.name);
            email = view.findViewById(R.id.email);
            phone = view.findViewById(R.id.phone);
            rating = view.findViewById(R.id.score);


            profCardView = view.findViewById(R.id.profCardView);

            /*
            profCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(profCardView.getContext(), RatingScreen.class);
                    profCardView.getContext().startActivity(intent);
                }
            });
            */

        }
    }
}
