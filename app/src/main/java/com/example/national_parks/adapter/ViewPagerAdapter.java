package com.example.national_parks.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.national_parks.R;
import com.example.national_parks.model.Images;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.ImageSlider> {

    private List<Images> imagesList;

    public ViewPagerAdapter(List<Images> imagesList) {
        this.imagesList = imagesList;
    }

    @NonNull
    @Override
    public ImageSlider onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewpager_row,parent,false);

        return new ImageSlider(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageSlider holder, int position) {
        Picasso.get().load(imagesList.get(position).getUrl()).fit().placeholder(android.R.drawable.stat_notify_error).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return imagesList.size();
    }

    public class ImageSlider extends RecyclerView.ViewHolder{

        public ImageView imageView;
        public ImageSlider(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ViewPager_ImageView);
        }
    }
}
