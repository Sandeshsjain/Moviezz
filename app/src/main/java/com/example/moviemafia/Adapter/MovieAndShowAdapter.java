package com.example.moviemafia.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviemafia.Bean.TrendingMovieBean;
import com.example.moviemafia.Network.Constant;
import com.example.moviemafia.R;

import java.util.List;

public class MovieAndShowAdapter extends RecyclerView.Adapter<MovieAndShowAdapter.MovieShowViewHolder> {
    Context context;
    List<TrendingMovieBean> trendingMovieBeanList;

    public MovieAndShowAdapter(Context context, List<TrendingMovieBean> trendingMovieBeanList) {
        Log.d("adapter", "MovieAndShowAdapter: ");
        this.context = context;
        this.trendingMovieBeanList = trendingMovieBeanList;
    }
    @NonNull
    @Override
    public MovieShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item_layout,parent,false);
        return new MovieAndShowAdapter.MovieShowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieShowViewHolder holder, int position) {
        Glide.with(context).load(Constant.Image_Org+trendingMovieBeanList.get(position).getPoster_path()).placeholder(R.drawable.ic_baseline_person_pin_24).into(holder.posterImage);
        //holder.titleTV.setText(trendingMovieBeanList.get(position).getOriginal_title());
        String firstTitle = trendingMovieBeanList.get(position).getOriginal_title().split(" ")[0];
        holder.titleTV.setText(firstTitle+"...");
        holder.rating_tv.setText(trendingMovieBeanList.get(position).getVote_average());
    }

    @Override
    public int getItemCount() {
        return trendingMovieBeanList.size();
    }

    public static class MovieShowViewHolder extends RecyclerView.ViewHolder{
        TextView rating_tv,titleTV;
        ImageView posterImage;
        public MovieShowViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.d("adpter", "MovieShowViewHolder: ");
            posterImage = itemView.findViewById(R.id.poster_image_view);
            rating_tv = itemView.findViewById(R.id.rating_tv);
            titleTV = itemView.findViewById(R.id.titleTV);
        }
    }

}
