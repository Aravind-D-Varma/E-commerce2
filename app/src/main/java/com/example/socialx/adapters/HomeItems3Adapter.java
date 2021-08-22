package com.example.socialx.adapters;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.socialx.R;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

public class HomeItems3Adapter extends RecyclerView.Adapter<HomeItems3Adapter.MyViewHolder> {
    private Context mContext;
    private ArrayList<String> shopItems;
    public HomeItems3Adapter(Context context,ArrayList<String> sI2){
        this.mContext = context;
        this.shopItems = sI2;
    }
    @NonNull
    @Override
    public HomeItems3Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.homescreen_item4, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeItems3Adapter.MyViewHolder holder, int position) {
        holder.text.setText(shopItems.get(position));
        Glide.with(mContext).load(R.drawable.images__3_).into(holder.img);
        //holder.img.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.images__3_));
        holder.price.setText("US $ 1.42");
        holder.sold.setText("4256 sold");
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
        TextView text,price,sold;
        ImageView img;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.cardview2_text);
            img = itemView.findViewById(R.id.cardview2_image);
            price = itemView.findViewById(R.id.cardview2_price);
            sold = itemView.findViewById(R.id.cardview2_sold);
        }
    }

}