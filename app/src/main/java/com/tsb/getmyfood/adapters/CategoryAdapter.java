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

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    Context context;
    ArrayList<HomeCategoryModel> categoryList;

    public CategoryAdapter(Context context, ArrayList<HomeCategoryModel> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.category, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(context).load(categoryList.get(position).getImgUrl()).into(holder.img);
        holder.name.setText(categoryList.get(position).getName());
        holder.desc.setText(WordCount.words(categoryList.get(position).getDesc()));

        holder.catHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, CategoryDetails.class);
                intent.putExtra("name", categoryList.get(position).getName());
                intent.putExtra("desc", categoryList.get(position).getDesc());
                intent.putExtra("img", categoryList.get(position).getImgUrl());
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView name, desc;
        LinearLayout catHolder;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.category_image);
            name=itemView.findViewById(R.id.category_name);
            desc=itemView.findViewById(R.id.category_desc);
            catHolder=itemView.findViewById(R.id.category_holder);
        }
    }
}
