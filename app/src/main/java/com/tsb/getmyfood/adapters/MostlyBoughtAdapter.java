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
import com.tsb.getmyfood.models.MostlyBoughtModel;

import java.util.ArrayList;
import java.util.List;

public class MostlyBoughtAdapter extends RecyclerView.Adapter<MostlyBoughtAdapter.ViewHolder> {

    private Context context;
//    private ArrayList<MostlyBoughtModel> mostlyBoughtList;
    private List<ItemsModel> mostlyBoughtList;

    public MostlyBoughtAdapter(Context context, List<ItemsModel> mostlyBoughtList) {
        this.context = context;
        this.mostlyBoughtList = mostlyBoughtList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.mostly_bought, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(context).load(mostlyBoughtList.get(position).getImgUrl()).into(holder.img);
        holder.name.setText(mostlyBoughtList.get(position).getName());
        holder.desc.setText(WordCount.words(mostlyBoughtList.get(position).getDesc()));
        holder.price.setText("Rs "+ mostlyBoughtList.get(position).getPrice());
        if(mostlyBoughtList.get(position).getType())
            holder.type.setImageResource(R.drawable.veg);
        else
            holder.type.setImageResource(R.drawable.nonveg);

        holder.itemHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, ItemDetails.class);
                intent.putExtra("itemImg", mostlyBoughtList.get(position).getImgUrl());
                intent.putExtra("itemImg", mostlyBoughtList.get(position).getImgUrl());
                intent.putExtra("itemName", mostlyBoughtList.get(position).getName());
                intent.putExtra("itemDesc", mostlyBoughtList.get(position).getDesc());
                intent.putExtra("itemPrice", Integer.toString(mostlyBoughtList.get(position).getPrice()));
                intent.putExtra("itemType", mostlyBoughtList.get(position).getType());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mostlyBoughtList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img, type;
        TextView name, desc, price;
        LinearLayout itemHolder;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img=itemView.findViewById(R.id.most_image);
            name=itemView.findViewById(R.id.most_name);
            desc=itemView.findViewById(R.id.most_desc);
            price=itemView.findViewById(R.id.most_price);
            type=itemView.findViewById(R.id.most_type);
            itemHolder=itemView.findViewById(R.id.mostlyItem);
        }
    }
}
