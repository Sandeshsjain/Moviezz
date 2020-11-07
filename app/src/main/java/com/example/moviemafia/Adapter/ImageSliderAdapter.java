package com.example.moviemafia.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.moviemafia.Bean.TrendingMovieBean;
import com.example.moviemafia.Network.Constant;
import com.example.moviemafia.R;
import com.smarteist.autoimageslider.SliderView;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

public class ImageSliderAdapter extends SliderViewAdapter<ImageSliderAdapter.ImageSliderViewHolder> {
    Context context;
    List<TrendingMovieBean> list;

    public ImageSliderAdapter(Context context, List<TrendingMovieBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ImageSliderViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.image_slider_layout,parent,false);
        return new ImageSliderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageSliderViewHolder viewHolder, int position) {
        Glide.with(context).load(Constant.Image_Org+list.get(position).getBackdrop_path()).placeholder(R.drawable.ic_baseline_person_pin_24).into(viewHolder.image_view_slider);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    public static class ImageSliderViewHolder extends SliderViewAdapter.ViewHolder{
        ImageView image_view_slider;
        public ImageSliderViewHolder(View itemView) {
            super(itemView);
            image_view_slider = itemView.findViewById(R.id.image_view_slider);
        }
    }
}
