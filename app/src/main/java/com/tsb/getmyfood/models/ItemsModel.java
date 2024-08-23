package com.tsb.getmyfood.models;

import java.util.List;

public class ItemsModel {

    private String name, desc, imgUrl;
    private boolean type;
    private int price;
    private List<Object> arr;

    public ItemsModel() {
    }

    public ItemsModel(String name, String desc, String imgUrl, boolean type, int price, List<Object> arr) {
        this.name = name;
        this.desc = desc;
        this.imgUrl = imgUrl;
        this.type = type;
        this.price = price;
        this.arr = arr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public boolean getType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public List<Object> getArr() {
        return arr;
    }

    public void setArr(List<Object> categories) {
        this.arr = arr;
    }
}
