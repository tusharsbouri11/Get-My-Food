package com.tsb.getmyfood.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tsb.getmyfood.R;
import com.tsb.getmyfood.models.Order;
import com.tsb.getmyfood.models.OrderItem;

import java.util.ArrayList;
import java.util.List;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrderViewHolder> {

    private Context context;
    private List<Order> orderList;

    // Constructor
    public OrdersAdapter(Context context, List<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the order layout
        View view = LayoutInflater.from(context).inflate(R.layout.order_layout, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderList.get(position);

        // Set order ID, date, and total price
        holder.orderIdTextView.setText(order.getOrderid());
        holder.dateTextView.setText("Order Placed on "+order.getDate());
        holder.totalPriceTextView.setText("Total: Rs " + order.getTotal());

        // Set up the RecyclerView for items within this order
        List<OrderItem> itemList = new ArrayList<>(order.getItems().values());
        OrdersItemAdapter itemAdapter = new OrdersItemAdapter(context, itemList);
        holder.itemsRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        holder.itemsRecyclerView.setAdapter(itemAdapter);

    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    // ViewHolder class
    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView orderIdTextView;
        TextView dateTextView;
        TextView totalPriceTextView;
        RecyclerView itemsRecyclerView;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            orderIdTextView = itemView.findViewById(R.id.orderIdTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            totalPriceTextView = itemView.findViewById(R.id.totalPriceTextView);
            itemsRecyclerView = itemView.findViewById(R.id.itemsRecyclerView);
        }
    }
}
