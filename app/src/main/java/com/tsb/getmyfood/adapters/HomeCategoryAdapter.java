package com.tsb.getmyfood.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tsb.getmyfood.CategoryDetails;
import com.tsb.getmyfood.R;
import com.tsb.getmyfood.WordCount;
import com.tsb.getmyfood.models.HomeCategoryModel;

import java.util.ArrayList;

public class HomeCategoryAdapter extends RecyclerView.Adapter<HomeCategoryAdapter.ViewHolder> {

    Context context;
    ArrayList<HomeCategoryModel> homeCategoryList;

    public HomeCategoryAdapter(Context context, ArrayList<HomeCategoryModel> homeCategoryList)
    {
        this.context=context;
        this.homeCategoryList=homeCategoryList;
    }

    @NonNull
    @Override
    public HomeCategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_category, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeCategoryAdapter.ViewHolder holder, int position) {

        Glide.with(context).load(homeCategoryList.get(position).getImgUrl()).into(holder.img);
        holder.name.setText(homeCategoryList.get(position).getName());
        holder.desc.setText(WordCount.words(homeCategoryList.get(position).getDesc()));

        holder.catHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, CategoryDetails.class);
                intent.putExtra("name", homeCategoryList.get(position).getName());
                intent.putExtra("desc", homeCategoryList.get(position).getDesc());
                intent.putExtra("img", homeCategoryList.get(position).getImgUrl());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return homeCategoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView name, desc;
        LinearLayout catHolder;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img=itemView.findViewById(R.id.home_category_image);
            name=itemView.findViewById(R.id.home_category_name);
            desc=itemView.findViewById(R.id.home_category_desc);
            catHolder=itemView.findViewById(R.id.home_category_holder);
        }
    }
}
