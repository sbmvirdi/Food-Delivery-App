package com.socialstack.fooddelivery;

public class itemmodel {
    private String name;
    private String image;
    private boolean clicked;
    private long price;
    private String id;

    public itemmodel(String name, String image,boolean clicked,long price,String id) {
        this.name = name;
        this.image = image;
        this.clicked = clicked;
        this.price = price;
        this.id = id;
    }

    public long getPrice() {
        return price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isClicked() {
        return clicked;
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
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
