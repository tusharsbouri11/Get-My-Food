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
import com.tsb.getmyfood.models.ItemsModel;

import java.util.List;

public class CategoryItemsAdapter extends RecyclerView.Adapter<CategoryItemsAdapter.ViewHolder> {

    Context context;
    List<ItemsModel> categoryItemsList;

    public CategoryItemsAdapter(Context context, List<ItemsModel> categoryItemsList) {
        this.context = context;
        this.categoryItemsList = categoryItemsList;
    }

    @NonNull
    @Override
    public CategoryItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryItemsAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.category_details_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryItemsAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(categoryItemsList.get(position).getImgUrl()).into(holder.img);
        holder.name.setText(categoryItemsList.get(position).getName());
        holder.price.setText("Rs "+categoryItemsList.get(position).getPrice());
        holder.desc.setText(WordCount.words(categoryItemsList.get(position).getDesc()));
        holder.type.setImageResource( (categoryItemsList.get(position).getType()) ? R.drawable.veg : R.drawable.nonveg );

        holder.itemHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, ItemDetails.class);
                intent.putExtra("itemImg", categoryItemsList.get(position).getImgUrl());
                intent.putExtra("itemName", categoryItemsList.get(position).getName());
                intent.putExtra("itemDesc", categoryItemsList.get(position).getDesc());
                intent.putExtra("itemPrice", Integer.toString(categoryItemsList.get(position).getPrice()));
                intent.putExtra("itemType", categoryItemsList.get(position).getType());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryItemsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView img, type;
        private TextView name, desc, price;
        private LinearLayout itemHolder;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img=itemView.findViewById(R.id.cat_image);
            type=itemView.findViewById(R.id.cat_type);
            price=itemView.findViewById(R.id.cat_price);
            name=itemView.findViewById(R.id.cat_name);
            desc=itemView.findViewById(R.id.cat_desc);
            itemHolder=itemView.findViewById(R.id.cat_item);
        }
    }
}
