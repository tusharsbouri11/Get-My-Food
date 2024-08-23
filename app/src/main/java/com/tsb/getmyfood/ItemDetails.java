package com.tsb.getmyfood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tsb.getmyfood.models.CartModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class ItemDetails extends AppCompatActivity {

    private TextView itemName, itemDesc, itemPrice, quant;
    private ImageView itemType, itemImg;
    private String getItemName, getItemDesc, getItemPrice, getImg, getItemId;
    private boolean getItemType;
    private AppCompatButton minus, plus, addCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        itemName=findViewById(R.id.item_name);
        itemDesc=findViewById(R.id.desc);
        itemPrice=findViewById(R.id.price);
        itemType=findViewById(R.id.item_type);
        itemImg=findViewById(R.id.img);

        quant=findViewById(R.id.quant);
        minus=findViewById(R.id.minus);
        plus=findViewById(R.id.plus);
        addCart=findViewById(R.id.add_cart);

        getDetails();
        setDetails();

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(quant.getText().toString().equals("1"))
                    quant.setText("1");
                else
                    quant.setText( Integer.toString(Integer.parseInt(quant.getText().toString())-1) );
            }
        });

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quant.setText( Integer.toString(Integer.parseInt(quant.getText().toString())+1) );
            }
        });

        addCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Integer.parseInt(quant.getText().toString()) > 10)
                    Toast.makeText(ItemDetails.this, "Max 10 plates/items only", Toast.LENGTH_SHORT).show();
                else {


                    if (!CartModel.checkInstance()) {
                        CartModel.setCartModelList(new LinkedHashMap<>());
                    }

                    if(CartModel.getCartModelList().containsKey(getItemName))
                    {
                        int t=CartModel.getCartModelList().get(getItemName).getCount()+Integer.parseInt(quant.getText().toString());
                        if (t > 10)
                            Toast.makeText(ItemDetails.this, "Max 10 plates/items only", Toast.LENGTH_SHORT).show();
                        else
                            CartModel.getCartModelList().get(getItemName).setCount(t);
                    }
                    else
                        CartModel.getCartModelList().put(getItemName, (new CartModel(getItemName, Integer.parseInt(quant.getText().toString()), Integer.parseInt(getItemPrice), getItemType)));

                    Toast.makeText(ItemDetails.this, "Added To Cart!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public void getDetails()
    {
        getItemName=getIntent().getStringExtra("itemName");
        getItemDesc=getIntent().getStringExtra("itemDesc");
        getItemPrice=getIntent().getStringExtra("itemPrice");
//        getItemType= Boolean.parseBoolean(getIntent().getStringExtra("itemType"));
        getItemType=getIntent().getBooleanExtra("itemType", true);
        getImg=getIntent().getStringExtra("itemImg");
//            getItemType=getIntent().getStringExtra("itemType");
    }

    public void setDetails()
    {
        Glide.with(this).load(getImg).into(itemImg);
        itemName.setText(getItemName);
        itemDesc.setText(getItemDesc);
        itemPrice.setText("Rs "+getItemPrice);
        if(getItemType)
            itemType.setImageResource(R.drawable.veg);
        else
            itemType.setImageResource(R.drawable.nonveg);



//        Toast.makeText(this, Integer.toString(getItemPrice), Toast.LENGTH_SHORT).show();
    }
}