package com.example.socialx.adapters;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.socialx.R;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

public class HomeItemsAdapter extends RecyclerView.Adapter<HomeItemsAdapter.MyViewHolder> {
    private Context mContext;
    private ArrayList<String> shopItems;
    public HomeItemsAdapter(Context context,ArrayList<String> sI){
        this.mContext = context;
        this.shopItems = sI;
    }
    @NonNull
    @Override
    public HomeItemsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.homescreen_item2, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeItemsAdapter.MyViewHolder holder, int position) {
        holder.tv1.setText(shopItems.get(position));
        Glide.with(mContext).load(R.drawable.images__1_).into(holder.img);
        //holder.img.setBackgroundDrawable(ContextCompat.getDrawable(mContext,R.drawable.images__1_));
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
        ShapeableImageView img;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.homeitem2_text);
            img = itemView.findViewById(R.id.homeitem2_image);
        }
    }

}
