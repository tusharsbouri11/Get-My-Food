package com.tsb.getmyfood.ui.myorders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tsb.getmyfood.R;
import com.tsb.getmyfood.adapters.OrdersAdapter;
import com.tsb.getmyfood.models.Order;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MyOrdersFragment extends Fragment {

    private RecyclerView ordersView;
    private ImageView emptyImg;
    private TextView emptyTxt;
    private OrdersAdapter ordersAdapter;
    private List<Order> orderList;


    public MyOrdersFragment() {
        // Required empty public constructor
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_my_orders, container, false);

        ordersView = root.findViewById(R.id.orders_view);
        emptyImg = root.findViewById(R.id.empty_img);
        emptyTxt = root.findViewById(R.id.empty_txt);
        orderList = new ArrayList<>();

        ordersView.setLayoutManager(new LinearLayoutManager(getContext()));
        ordersAdapter = new OrdersAdapter(getContext(), orderList);
        ordersView.setAdapter(ordersAdapter);

        fetchOrder();

        return root;
    }

    private void fetchOrder() {

        String userId=FirebaseAuth.getInstance().getUid();

        FirebaseDatabase.getInstance().getReference("Users")
                .child(userId)
                .child("Orders")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        orderList.clear();
                        if(dataSnapshot.exists())
                        {
                            for(DataSnapshot orderSnapshot : dataSnapshot.getChildren())
                            {
                                Order order = orderSnapshot.getValue(Order.class);
                                if(order != null)
                                    orderList.add(order);
                            }
                            Collections.reverse(orderList);
                            ordersAdapter.notifyDataSetChanged();
                            ifEmpty(orderList.isEmpty());
                        }
                        else
                            ifEmpty(true);
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Toast.makeText(getContext(), "Failed to Load Orders", Toast.LENGTH_SHORT).show();
                        ifEmpty(true);
                    }
                });

    }

    private void ifEmpty(boolean b) {
        if(b)
        {
            emptyImg.setVisibility(View.VISIBLE);
            emptyTxt.setVisibility(View.VISIBLE);
            ordersView.setVisibility(View.GONE);
        }
        else
        {
            emptyImg.setVisibility(View.GONE);
            emptyTxt.setVisibility(View.GONE);
            ordersView.setVisibility(View.VISIBLE);
        }
    }

}