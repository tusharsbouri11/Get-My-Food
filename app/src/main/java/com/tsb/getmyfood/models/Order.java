package com.tsb.getmyfood.models;

import java.util.Map;

public class Order {
    private String date;
    private String orderid;
    private int total;
    private Map<String, OrderItem> items; // Map of item names to OrderItem objects

    // Default constructor required for calls to DataSnapshot.getValue(Order.class)
    public Order() {}

    public Order(String date, String orderid, int total, Map<String, OrderItem> items) {
        this.date = date;
        this.orderid = orderid;
        this.total = total;
        this.items = items;
    }

    // Getters and setters
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Map<String, OrderItem> getItems() {
        return items;
    }

    public void setItems(Map<String, OrderItem> items) {
        this.items = items;
    }
}

