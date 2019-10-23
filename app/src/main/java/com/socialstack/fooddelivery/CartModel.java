package com.socialstack.fooddelivery;

public class CartModel {
    private String name,image;
    private long price;

    public CartModel(String name, String image, long price) {
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public CartModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }
}
