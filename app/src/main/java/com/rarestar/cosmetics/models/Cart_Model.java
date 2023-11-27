package com.rarestar.cosmetics.models;

public class Cart_Model {
    String username,product_name,product_image,message;
    int price;

    public Cart_Model(String username, String product_name, String product_image, int price,String message) {
        this.username = username;
        this.product_name = product_name;
        this.product_image = product_image;
        this.price = price;
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
