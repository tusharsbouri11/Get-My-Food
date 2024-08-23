package com.tsb.getmyfood.models;

public class HomeNewOfferingModel {

    private String name, desc, imgUrl;
    private boolean type;
    private int price;

    public HomeNewOfferingModel() {
    }

    public HomeNewOfferingModel(String name, String desc, String imgUrl, boolean type, int price) {
        this.name = name;
        this.desc = desc;
        this.imgUrl = imgUrl;
        this.type = type;
        this.price = price;
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
}
