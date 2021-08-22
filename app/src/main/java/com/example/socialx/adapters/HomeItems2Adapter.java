package com.example.socialx.adapters;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialx.R;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

public class HomeItems2Adapter extends RecyclerView.Adapter<HomeItems2Adapter.MyViewHolder> {
    private Context mContext;
    private ArrayList<String> shopItems;
    public HomeItems2Adapter(Context context,ArrayList<String> sI2){
        this.mContext = context;
        this.shopItems = sI2;
    }
    @NonNull
    @Override
    public HomeItems2Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.homescreen_item3, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeItems2Adapter.MyViewHolder holder, int position) {
        holder.tv1.setText(shopItems.get(position));
        holder.img.setBackgroundColor(Color.parseColor("#FFFFFF"));
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do Stuff
            }
        });
    }

    @Override
    public int getItemCount() {
        return shopItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv1;
        ImageView img;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.cardview_text);
            img = itemView.findViewById(R.id.cardview_image);
        }
    }

}