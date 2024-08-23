package com.tsb.getmyfood.ui.cart;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tsb.getmyfood.R;
import com.tsb.getmyfood.adapters.MyCartsAdapter;
import com.tsb.getmyfood.models.CartModel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class MyCartsFragment extends Fragment {

    private LinkedHashMap<String, CartModel> cartModelList;
    private static ImageView emptyImg;
    private static TextView emptyTxt;
    private TextView cprice, ccgst, csgst, ccdisc, cdelivery, ctotal;
    private static AppCompatButton order;
    private static LinearLayout priceView;
    private static RecyclerView cartView;
    private int total;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root = inflater.inflate(R.layout.fragment_my_carts, container, false);

        emptyImg=root.findViewById(R.id.empty_img);
        emptyTxt=root.findViewById(R.id.empty_txt);
        order=root.findViewById(R.id.place_order);
        cartView=root.findViewById(R.id.cart_view);
        priceView=root.findViewById(R.id.price_view);

        cprice=root.findViewById(R.id.cfrag_price);
        ccgst=root.findViewById(R.id.cfrag_cgst);
        csgst=root.findViewById(R.id.cfrag_sgst);
        ccdisc=root.findViewById(R.id.cfrag_disc);
        cdelivery=root.findViewById(R.id.cfrag_delivery);
        ctotal=root.findViewById(R.id.cfrag_total);

        if(CartModel.checkInstance() || CartModel.checkSize())
        {
            emptyImg.setVisibility(View.GONE);
            emptyTxt.setVisibility(View.GONE);
//            Toast.makeText(getContext(), Integer.toString(CartModel.cartModelList.size()), Toast.LENGTH_SHORT).show();

            cartView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
            MyCartsAdapter cart=new MyCartsAdapter(getActivity(), CartModel.getCartModelList());
            cart.setOnCartUpdateListener(new MyCartsAdapter.OnCartUpdateListener() {
                @Override
                public void onCartUpdated() {
                    setPrice();
                }
            });
            cartView.setAdapter(cart);
            setPrice();

            order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    placeOrder();
                }
            });

        }
        else
            onCartEmpty(getContext());

        return root;

    }

    private void placeOrder()
    {
        String id= FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase database=FirebaseDatabase.getInstance();

        String orderId=getDate("ddMMyyyyhhmmssSSS");
        String date=getDate("dd MMM yyyy, hh:mm a");

        LinkedHashMap<String, Object> orderDetails=new LinkedHashMap<>();
        HashMap<String, Object> list=new HashMap<>();

        orderDetails.put("total", this.total);
        orderDetails.put("date", date);
        orderDetails.put("orderid", orderId);

        for(String key: cartModelList.keySet())
        {
            CartModel item=cartModelList.get(key);
            LinkedHashMap<String, Object> items = new LinkedHashMap<>();
            items.put("name", key);
            items.put("count",item.getCount());
            items.put("type",item.getType());
            items.put("price",item.getPrice()*item.getCount());

            list.put(key, items);
        }

        orderDetails.put("items", list);

        database.getReference().child("Users").child(id).child("Orders")
                .child(orderId)
                .setValue(orderDetails)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(getContext(), "Order placed successfully!", Toast.LENGTH_SHORT).show();

                        CartModel.getCartModelList().clear();
                        cartView.getAdapter().notifyDataSetChanged();
                        total=0;
                        onCartEmpty(getContext());
                    }
                    else
                    {
                        Toast.makeText(getContext(), "Failed to place order", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private String getDate(String pattern) {
        // Fetch the current date and time
        LocalDateTime now = LocalDateTime.now();

        // Define a formatter with the required pattern
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

        // Format the current date and time
        return now.format(formatter);

    }

    private String getOrderId() {
        // Fetch the current date and time
        LocalDateTime now = LocalDateTime.now();

        // Define a formatter with the required pattern
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyyHHmmssSSS");

        // Format the current date and time

        return now.format(formatter);
    }

    private void setPrice() {

        int totalPrice=0;
        int delivery=50;
        cartModelList = CartModel.getCartModelList();

        for (CartModel i : cartModelList.values() )
            totalPrice = totalPrice + (i.getCount() * i.getPrice());

        cprice.setText("Rs "+Integer.toString(totalPrice));

        int discount=totalPrice*10/100;
        totalPrice-= discount;
        int gst=totalPrice*8/100;
        total=totalPrice+(2*gst)+delivery+10;

        ccgst.setText("Rs "+Integer.toString(gst));
        csgst.setText("Rs "+Integer.toString(gst));
        ccdisc.setText("Rs "+Integer.toString(discount));
        cdelivery.setText("Rs "+Integer.toString(delivery));
        ctotal.setText("Rs "+Integer.toString(total));
    }

    public static void onCartEmpty(Context context) {
        emptyImg.setVisibility(View.VISIBLE);
        emptyTxt.setVisibility(View.VISIBLE);
        order.setVisibility(View.GONE);
        cartView.setVisibility(View.GONE);
        priceView.setVisibility(View.GONE);
        Toast.makeText(context, "Cart is Empty!", Toast.LENGTH_SHORT).show();
    }
}