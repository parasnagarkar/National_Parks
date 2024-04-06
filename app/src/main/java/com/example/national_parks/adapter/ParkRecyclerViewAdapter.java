package com.example.national_parks.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.national_parks.R;
import com.example.national_parks.model.Park;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ParkRecyclerViewAdapter extends RecyclerView.Adapter<ParkRecyclerViewAdapter.ViewHolder> {

    private final List<Park> parkList;
    private final OnparkClickListner onparkClickListner;

    public ParkRecyclerViewAdapter(List<Park> parkList,OnparkClickListner parkClickListner) {
        this.parkList = parkList;
        this.onparkClickListner = parkClickListner;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.park_row,parent,false);
        return new ViewHolder(v,onparkClickListner);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Park park = parkList.get(position);
        holder.parkname.setText(park.getFullName());
        holder.parkType.setText(park.getDesignation());
        holder.parkState.setText(park.getStates());
        if(park.getImages().size()>0) {
            Picasso.get().load(park.getImages().get(0).getUrl())
                    .placeholder(android.R.drawable.stat_sys_download)
                    .error(android.R.drawable.stat_notify_error)
                    .resize(100,100)
                    .centerCrop()
                    .into(holder.parkImage);

        }

    }

    @Override
    public int getItemCount() {
        return parkList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView parkImage;
        public TextView parkname;
        public TextView parkType;
        public TextView parkState;
        OnparkClickListner parklistner;
        public ViewHolder(@NonNull View itemView,OnparkClickListner parklistner) {
            super(itemView);
            parkImage = itemView.findViewById(R.id.imageView);
            parkname = itemView.findViewById(R.id.row_park_name_textview);
            parkType = itemView.findViewById(R.id.row_park_type_textview);
            parkState = itemView.findViewById(R.id.row_park_state_textView);
            this.parklistner = parklistner;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

            Park currPark = parkList.get(getAdapterPosition());
            onparkClickListner.onParkClicked(currPark);

        }
    }
}
