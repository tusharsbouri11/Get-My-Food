package com.tsb.getmyfood.models;

public class MostlyBoughtModel {

    private String name, desc, imgUrl;
    private int price;
    private boolean type;

    public MostlyBoughtModel() {
    }

    public MostlyBoughtModel(String name, int price, String desc, String imgUrl, boolean type) {
        this.name = name;
        this.price = price;
        this.desc = desc;
        this.imgUrl = imgUrl;
        this.type = type;
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
}
