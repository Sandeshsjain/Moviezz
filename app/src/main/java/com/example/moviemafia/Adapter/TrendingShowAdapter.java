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
import com.example.moviemafia.Bean.TrendingShowBean;
import com.example.moviemafia.Network.Constant;
import com.example.moviemafia.R;

import java.util.List;

public class TrendingShowAdapter extends RecyclerView.Adapter<TrendingShowAdapter.TrendingShowViewHolder> {
    Context context;
    List<TrendingShowBean> list;

    public TrendingShowAdapter(Context context, List<TrendingShowBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public TrendingShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item_layout,parent,false);
        return new TrendingShowAdapter.TrendingShowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrendingShowViewHolder holder, int position) {
        Glide.with(context).load(Constant.Image_Org+list.get(position).getPoster_path()).placeholder(R.drawable.ic_baseline_person_pin_24).into(holder.posterImage);
        holder.rating_tv.setText(list.get(position).getVote_average());
        String first = list.get(position).getOriginal_name().split(" ")[0];
        holder.titleTV.setText(first+"...");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class TrendingShowViewHolder extends RecyclerView.ViewHolder{
        TextView rating_tv,titleTV;
        ImageView posterImage;
        public TrendingShowViewHolder(@NonNull View itemView) {
            super(itemView);
            posterImage = itemView.findViewById(R.id.poster_image_view);
            rating_tv = itemView.findViewById(R.id.rating_tv);
            titleTV = itemView.findViewById(R.id.titleTV);
        }
    }
}
