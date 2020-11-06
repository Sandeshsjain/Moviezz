package com.example.moviemafia.Adapter;

import android.content.Context;
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

public class TrendingPersonListAdpater extends RecyclerView.Adapter<TrendingPersonListAdpater.TrendingPersonListViewholder>{
    Context context;
    List<TrendingPersonBean> trendingPersonBeanList;

    public TrendingPersonListAdpater(Context context, List<TrendingPersonBean> trendingPersonBeanList) {
        this.context = context;
        this.trendingPersonBeanList = trendingPersonBeanList;
    }

    @NonNull
    @Override
    public TrendingPersonListViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trending_person_item_list_layout,parent,false);
        return new TrendingPersonListViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrendingPersonListViewholder holder, int position) {
        Glide.with(context).load(Constant.Image_Org+trendingPersonBeanList.get(position).getProfile_path()).placeholder(R.drawable.ic_baseline_person_pin_24).into(holder.circularimageview);
        holder.nameTV.setText(trendingPersonBeanList.get(position).getName());
        holder.proffessionTV.setText(trendingPersonBeanList.get(position).getKnown_for_department());
    }

    @Override
    public int getItemCount() {
        return trendingPersonBeanList.size();
    }

    public class TrendingPersonListViewholder extends RecyclerView.ViewHolder{
        CircularImageView circularimageview;
        TextView nameTV,proffessionTV;
        public TrendingPersonListViewholder(@NonNull View itemView) {
            super(itemView);
            circularimageview = itemView.findViewById(R.id.circularimageview);
            nameTV = itemView.findViewById(R.id.nameTV);
            proffessionTV = itemView.findViewById(R.id.proffessionTV);
        }
    }
}
