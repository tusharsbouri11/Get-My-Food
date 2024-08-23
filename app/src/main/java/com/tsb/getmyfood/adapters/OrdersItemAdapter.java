package com.tsb.getmyfood.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.tsb.getmyfood.R;
import com.tsb.getmyfood.models.OrderItem;

import java.util.List;

public class OrdersItemAdapter extends RecyclerView.Adapter<OrdersItemAdapter.ViewHolder> {

    private Context context;
    private List<OrderItem> itemList;
    public OrdersItemAdapter(Context context, List<OrderItem> itemList) {
        this.context=context;
        this.itemList=itemList;
    }

    @NonNull
    @Override
    public OrdersItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersItemAdapter.ViewHolder holder, int position) {
        OrderItem item=itemList.get(position);

        holder.type.setImageResource( item.getType() ? R.drawable.veg : R.drawable.nonveg );
        holder.name.setText(item.getName());
        holder.multiplier.setText(Integer.toString(item.getCount()));
        holder.price.setText("Rs "+item.getPrice());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView type;
        AppCompatTextView name, price;
        TextView multiplier;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            type=itemView.findViewById(R.id.order_type);
            name=itemView.findViewById(R.id.order_name);
            multiplier=itemView.findViewById(R.id.order_multiplier);
            price=itemView.findViewById(R.id.order_price);
        }
    }
}
