package com.example.socialx.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.socialx.R;

import java.util.ArrayList;

public class VPAdapter extends RecyclerView.Adapter<VPAdapter.MyViewHolder> {
    private Context mContext;
    private ArrayList<String> offerText,offerText2;

    public VPAdapter(Context context, ArrayList<String> list1, ArrayList<String> list2){
        this.mContext = context;
        this.offerText = list1;
        this.offerText2 = list2;
    }

    @NonNull
    @Override
    public VPAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.homescreen_item1, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VPAdapter.MyViewHolder holder, int position) {
        holder.tv1.setText(offerText.get(position));
        holder.tv2.setText(offerText2.get(position));
        holder.btn.setText("SHOP NOW");
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //do Stuff
            }
        });
    }

    @Override
    public int getItemCount() {
        return offerText.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv1,tv2;
        Button btn;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.vpText1);
            tv2 = itemView.findViewById(R.id.vpText2);
            btn = itemView.findViewById(R.id.vpbtn);
        }
    }
}

