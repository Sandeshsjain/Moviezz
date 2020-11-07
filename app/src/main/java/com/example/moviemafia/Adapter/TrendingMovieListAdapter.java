package com.example.moviemafia.Adapter;

import android.content.Context;
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

public class TrendingMovieListAdapter extends RecyclerView.Adapter<TrendingMovieListAdapter.TrendingMovieListViewHolder> {
    Context context;
    List<TrendingMovieBean> list;

    public TrendingMovieListAdapter(Context context, List<TrendingMovieBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public TrendingMovieListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trending_movie_list_layout,parent,false);
        return new TrendingMovieListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrendingMovieListViewHolder holder, int position) {
        Glide.with(context).load(Constant.Image_Org+list.get(position).getPoster_path()).placeholder(R.drawable.ic_baseline_person_pin_24).into(holder.imageviewmovieposter);
        holder.ratingTv.setText(list.get(position).getVote_average());
        holder.yearTV.setText(list.get(position).getRelease_date());
        holder.titleTv.setText(list.get(position).getOriginal_title());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class TrendingMovieListViewHolder extends RecyclerView.ViewHolder{
        TextView yearTV,titleTv,ratingTv;
        ImageView imageviewmovieposter;
        public TrendingMovieListViewHolder(@NonNull View itemView) {
            super(itemView);
            yearTV = itemView.findViewById(R.id.yearTV);
            titleTv = itemView.findViewById(R.id.titleTvm);
            ratingTv = itemView.findViewById(R.id.ratingTvm);
            imageviewmovieposter = itemView.findViewById(R.id.imageviewmovieposter);
        }
    }
}
