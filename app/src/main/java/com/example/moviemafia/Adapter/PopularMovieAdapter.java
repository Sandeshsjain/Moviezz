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
import com.example.moviemafia.Bean.PopulerMovieBean;
import com.example.moviemafia.Network.Constant;
import com.example.moviemafia.R;

import java.util.List;

public class PopularMovieAdapter extends RecyclerView.Adapter<PopularMovieAdapter.PopularMovieViewHolder> {
    Context context;
    List<PopulerMovieBean> list;

    public PopularMovieAdapter(Context context, List<PopulerMovieBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public PopularMovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item_layout,parent,false);
        return new PopularMovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularMovieViewHolder holder, int position) {
        Glide.with(context).load(Constant.Image_Org+list.get(position).getPoster_path()).placeholder(R.drawable.ic_baseline_person_pin_24).into(holder.posterImage);
        //holder.titleTV.setText(trendingMovieBeanList.get(position).getOriginal_title());
        String firstTitle = list.get(position).getOriginal_title().split(" ")[0];
        holder.titleTV.setText(firstTitle+"...");
        holder.rating_tv.setText(list.get(position).getVote_average());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class PopularMovieViewHolder extends RecyclerView.ViewHolder{
        TextView rating_tv,titleTV;
        ImageView posterImage;
        public PopularMovieViewHolder(@NonNull View itemView) {
            super(itemView);
            posterImage = itemView.findViewById(R.id.poster_image_view);
            rating_tv = itemView.findViewById(R.id.rating_tv);
            titleTV = itemView.findViewById(R.id.titleTV);
        }
    }
}
