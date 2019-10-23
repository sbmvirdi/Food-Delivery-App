package com.socialstack.fooddelivery;

import android.graphics.drawable.Drawable;

public class FoodItemModel {

    private Drawable image;
    private String name;
    private int price;
    private int Quantity;

    public FoodItemModel(Drawable image, String name, int price, int quantity) {
        this.image = image;
        this.name = name;
        this.price = price;
        Quantity = quantity;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
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

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }
}
