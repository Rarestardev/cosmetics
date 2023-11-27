package com.rarestar.cosmetics.models;

public class Product_Model {

    int number;
    String product_name, group_name,brands,store,store_location,product_introduction;
    String category,property,age,how_to_use,compounds,warning;
    String image;
    int price;
    String made_by;
    String date;

    public Product_Model(int number, String product_name,
                         String group_name, String brands, String store,
                         String store_location, String product_introduction,
                         String category, String property, String age, String how_to_use,
                         String compounds, String warning, String image, int price, String made_by, String date) {
        this.number = number;
        this.product_name = product_name;
        this.group_name = group_name;
        this.brands = brands;
        this.store = store;
        this.store_location = store_location;
        this.product_introduction = product_introduction;
        this.category = category;
        this.property = property;
        this.age = age;
        this.how_to_use = how_to_use;
        this.compounds = compounds;
        this.warning = warning;
        this.image = image;
        this.price = price;
        this.made_by = made_by;
        this.date = date;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getBrands() {
        return brands;
    }

    public void setBrands(String brands) {
        this.brands = brands;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getStore_location() {
        return store_location;
    }

    public void setStore_location(String store_location) {
        this.store_location = store_location;
    }

    public String getProduct_introduction() {
        return product_introduction;
    }

    public void setProduct_introduction(String product_introduction) {
        this.product_introduction = product_introduction;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getHow_to_use() {
        return how_to_use;
    }

    public void setHow_to_use(String how_to_use) {
        this.how_to_use = how_to_use;
    }

    public String getCompounds() {
        return compounds;
    }

    public void setCompounds(String compounds) {
        this.compounds = compounds;
    }

    public String getWarning() {
        return warning;
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getMade_by() {
        return made_by;
    }

    public void setMade_by(String made_by) {
        this.made_by = made_by;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
