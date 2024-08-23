package com.tsb.getmyfood.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.tsb.getmyfood.R;
import com.tsb.getmyfood.models.CartModel;
import com.tsb.getmyfood.ui.cart.MyCartsFragment;

import java.util.ArrayList;
import java.util.HashMap;

public class MyCartsAdapter extends RecyclerView.Adapter<MyCartsAdapter.ViewHolder> {

    Context context;
    HashMap<String, CartModel> cartModelList;
    ArrayList<String> cartSet;
    private OnCartUpdateListener onCartUpdateListener;

    public interface OnCartUpdateListener {
        void onCartUpdated();
    }

    public void setOnCartUpdateListener(OnCartUpdateListener listener) {
        this.onCartUpdateListener = listener;
    }

    public MyCartsAdapter(Context context, HashMap<String, CartModel> cartModelList) {
        this.context = context;
        this.cartModelList = cartModelList;
        this.cartSet = new ArrayList<>(cartModelList.keySet());
    }

    @NonNull
    @Override
    public MyCartsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyCartsAdapter.ViewHolder holder, int position) {
        String x=cartSet.get(position);

        holder.name.setText(x);
        holder.type.setImageResource( cartModelList.get(x).getType() ? R.drawable.veg : R.drawable.nonveg );
        holder.quant.setText( Integer.toString(cartModelList.get(x).getCount()) );

        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int t=cartModelList.get(x).getCount();
                if(t<10) {
                    cartModelList.get(x).setCount(t + 1);
                    notifyCartUpdated();
                }
                else
                {
                    Toast.makeText(context, "Max 10 plates/items only", Toast.LENGTH_SHORT).show();
                    cartModelList.get(x).setCount(10);
                }
                holder.quant.setText( Integer.toString(cartModelList.get(x).getCount()) );
                holder.price.setText( "Rs "+Integer.toString(cartModelList.get(x).getCount()*cartModelList.get(x).getPrice()) );
            }
        });

        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int t=cartModelList.get(x).getCount();
                if(t>1)
                {
                    cartModelList.get(x).setCount(t-1);
                    holder.quant.setText( Integer.toString(cartModelList.get(x).getCount()) );
                    holder.price.setText( "Rs "+Integer.toString(cartModelList.get(x).getCount()*cartModelList.get(x).getPrice()) );
                    notifyCartUpdated();
                }
                else
                {
                    Toast.makeText(context, "Item Removed", Toast.LENGTH_SHORT).show();
                    removeItem(x, position);
                }
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeItem(x, position);
                Toast.makeText(context, "Item Removed", Toast.LENGTH_SHORT).show();
            }
        });

        holder.price.setText( "Rs "+Integer.toString(cartModelList.get(x).getCount()*cartModelList.get(x).getPrice()) );
    }

    private void removeItem(String x, int position) {
        cartModelList.remove(x);
        cartSet.remove(x);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, cartModelList.size());
        if(cartModelList.isEmpty())
            MyCartsFragment.onCartEmpty(context);
        notifyCartUpdated();
    }


    @Override
    public int getItemCount() {
        return cartModelList.size();
    }

    private void notifyCartUpdated() {
        if (onCartUpdateListener != null) {
            onCartUpdateListener.onCartUpdated();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView type, delete;
        private AppCompatButton minus, plus;
        private TextView name, quant, price;

        public ViewHolder(View itemView) {
            super(itemView);

            type=itemView.findViewById(R.id.cart_type);
            delete=itemView.findViewById(R.id.cart_del);
            name=itemView.findViewById(R.id.cart_name);
            quant=itemView.findViewById(R.id.cart_multiplier);
            price=itemView.findViewById(R.id.cart_price);
            plus=itemView.findViewById(R.id.cart_plus);
            minus=itemView.findViewById(R.id.cart_minus);
        }
    }
}
