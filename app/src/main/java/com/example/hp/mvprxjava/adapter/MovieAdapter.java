package com.example.hp.mvprxjava.adapter;

import android.content.Context;
import android.icu.util.ValueIterator;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hp.mvprxjava.R;
import com.example.hp.mvprxjava.model.MovieResult;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    List<MovieResult> movieList;
    Context context;

    public MovieAdapter(Context context,List<MovieResult> movieList) {
        this.context = context;
        this.movieList = movieList;
    }


    @NonNull
    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.move_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.ViewHolder holder, int position) {
        holder.tvTitle.setText(movieList.get(position).getTitle());
        holder.tvOverview.setText(movieList.get(position).getOverview());
        holder.tvReleaseDate.setText(movieList.get(position).getRelease_date());
        Glide.with(context).load("https://image.tmdb.org/t/p/w500/"+movieList.get(position).getPoster_path()).into(holder.ivMovie);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle,tvOverview,tvReleaseDate;
        ImageView ivMovie;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvOverview = (TextView) itemView.findViewById(R.id.tvOverView);
            tvReleaseDate = (TextView) itemView.findViewById(R.id.tvReleaseDate);
            ivMovie = (ImageView) itemView.findViewById(R.id.ivMovie);
        }
    }
}
