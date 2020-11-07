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
import java.util.ListResourceBundle;

public class TrendingShowListAdapter extends RecyclerView.Adapter<TrendingShowListAdapter.TrendingShowViewHolder> {
    Context context;
    List<TrendingShowBean> list;

    public TrendingShowListAdapter(Context context, List<TrendingShowBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public TrendingShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trending_movie_list_layout,parent,false);
        return new TrendingShowViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull TrendingShowViewHolder holder, int position) {
        Glide.with(context).load(Constant.Image_Org+list.get(position).getPoster_path()).placeholder(R.drawable.ic_baseline_person_pin_24).into(holder.imageviewmovieposter);
        holder.ratingTvm.setText(list.get(position).getVote_average());
        holder.yearTV.setText(list.get(position).getFirst_air_date());
        holder.titleTvm.setText(list.get(position).getOriginal_name());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class TrendingShowViewHolder extends RecyclerView.ViewHolder{
        TextView yearTV,titleTvm,ratingTvm;
        ImageView imageviewmovieposter;
        public TrendingShowViewHolder(@NonNull View itemView) {
            super(itemView);
            yearTV = itemView.findViewById(R.id.yearTV);
            titleTvm = itemView.findViewById(R.id.titleTvm);
            ratingTvm = itemView.findViewById(R.id.ratingTvm);
            imageviewmovieposter = itemView.findViewById(R.id.imageviewmovieposter);
        }
    }
}
