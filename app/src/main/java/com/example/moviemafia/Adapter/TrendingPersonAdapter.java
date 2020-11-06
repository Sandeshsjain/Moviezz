package com.example.moviemafia.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviemafia.Bean.TrendingPersonBean;
import com.example.moviemafia.Network.Constant;
import com.example.moviemafia.R;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

public class TrendingPersonAdapter extends RecyclerView.Adapter<TrendingPersonAdapter.TrendingPersonViewHolder>{
    Context context;
    List<TrendingPersonBean> trendingPersonBeanList;

    public TrendingPersonAdapter(Context context, List<TrendingPersonBean> trendingPersonBeanList) {
        this.context = context;
        this.trendingPersonBeanList = trendingPersonBeanList;
    }

    @NonNull
    @Override
    public TrendingPersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trending_person_layout,parent,false);
        return new TrendingPersonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrendingPersonViewHolder holder, int position) {
        Glide.with(context).load(Constant.Image_Org+trendingPersonBeanList.get(position).getProfile_path()).placeholder(R.drawable.ic_baseline_person_pin_24).into(holder.circular_image_view);
        String first_name = trendingPersonBeanList.get(position).getName().split(" ")[0];
        Log.d("Adapter", "onBindViewHolder: "+first_name);
        holder.name_textview.setText(first_name);
    }

    @Override
    public int getItemCount() {
        return trendingPersonBeanList.size();
    }

    public class TrendingPersonViewHolder extends RecyclerView.ViewHolder{
        CircularImageView circular_image_view;
        TextView name_textview;
        public TrendingPersonViewHolder(@NonNull View itemView) {
            super(itemView);
            circular_image_view = itemView.findViewById(R.id.circular_image_view);
            name_textview = itemView.findViewById(R.id.name_textview);
        }
    }
}
