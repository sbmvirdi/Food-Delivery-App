package com.socialstack.fooddelivery;

public class itemmodel {
    private String name;
    private String image;

    public itemmodel(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public itemmodel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }
}
