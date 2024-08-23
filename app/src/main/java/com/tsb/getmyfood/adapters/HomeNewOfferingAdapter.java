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
import com.tsb.getmyfood.ItemDetails;
import com.tsb.getmyfood.R;
import com.tsb.getmyfood.WordCount;
import com.tsb.getmyfood.models.HomeNewOfferingModel;
import com.tsb.getmyfood.models.ItemsModel;

import java.util.ArrayList;
import java.util.List;

public class HomeNewOfferingAdapter extends RecyclerView.Adapter<HomeNewOfferingAdapter.ViewHolder> {

    Context context;
    List<ItemsModel> homeNewOfferingList;

    public HomeNewOfferingAdapter(Context context, List<ItemsModel> homeNewOfferingList) {
        this.context = context;
        this.homeNewOfferingList = homeNewOfferingList;
    }

    @NonNull
    @Override
    public HomeNewOfferingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.new_offering, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeNewOfferingAdapter.ViewHolder holder, int position) {

        Glide.with(context).load(homeNewOfferingList.get(position).getImgUrl()).into(holder.img);
        holder.name.setText(homeNewOfferingList.get(position).getName());
        holder.price.setText("Rs "+homeNewOfferingList.get(position).getPrice());
        holder.desc.setText(WordCount.words(homeNewOfferingList.get(position).getDesc()));
        if(homeNewOfferingList.get(position).getType())
            holder.type.setImageResource(R.drawable.veg);
        else
            holder.type.setImageResource(R.drawable.nonveg);

        holder.itemHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, ItemDetails.class);
                intent.putExtra("itemImg", homeNewOfferingList.get(position).getImgUrl());
                intent.putExtra("itemName", homeNewOfferingList.get(position).getName());
                intent.putExtra("itemDesc", homeNewOfferingList.get(position).getDesc());
                intent.putExtra("itemPrice", Integer.toString(homeNewOfferingList.get(position).getPrice()));
                intent.putExtra("itemType", homeNewOfferingList.get(position).getType());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return homeNewOfferingList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img, type;
        TextView name, desc, price;
        LinearLayout itemHolder;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img=itemView.findViewById(R.id.new_image);
            type=itemView.findViewById(R.id.new_type);

            name=itemView.findViewById(R.id.new_name);
            desc=itemView.findViewById(R.id.new_desc);
            price=itemView.findViewById(R.id.new_price);
            itemHolder=itemView.findViewById(R.id.new_item);
        }
    }
}
