package com.tsb.getmyfood.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class CartModel {

//    private static CartModel INSTANCE;

    private static LinkedHashMap<String, CartModel> cartModelList;
    private String name, id;
    private int count, price;
    private boolean type;

    public static boolean checkInstance()
    {
        if(cartModelList==null)
            return false;
        return true;
    }

    public static boolean checkSize()
    {
        if(cartModelList==null)
            return false;
        if(cartModelList.isEmpty())
            return false;
        return true;
    }

    public CartModel(String name, int count, int price, boolean type) {
        this.name = name;
        this.count = count;
        this.price = price;
        this.type = type;
    }

    public CartModel(String name, String id, int count, boolean type, int price) {
        this.name = name;
        this.id = id;
        this.count = count;
        this.type = type;
        this.price=price;
    }

    public static void setCartModelList(LinkedHashMap<String, CartModel> cartList) {
        cartModelList=cartList;
    }
    public static LinkedHashMap<String, CartModel> getCartModelList() {
        return cartModelList;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean getType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }
}
