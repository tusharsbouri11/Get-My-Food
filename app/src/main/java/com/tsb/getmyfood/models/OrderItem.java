package com.tsb.getmyfood.models;

public class OrderItem {
    private int count;
    private String name;
    private int price;
    private boolean type; // true for veg, false for non-veg

    // Default constructor required for calls to DataSnapshot.getValue(OrderItem.class)
    public OrderItem() {}

    public OrderItem(int count, String name, int price, boolean type) {
        this.count = count;
        this.name = name;
        this.price = price;
        this.type = type;
    }

    // Getters and setters
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean getType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }
}
