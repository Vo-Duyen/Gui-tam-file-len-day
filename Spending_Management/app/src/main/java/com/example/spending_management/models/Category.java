package com.example.spending_management.models;
public class Category {
    private String category_name;
    private int category_image;
    private int category_color;
    public int getCategory_color() {
        return category_color;
    }
    public void setCategory_color(int category_color) {
        this.category_color = category_color;
    }
    public Category() {
    }
    public Category(String category_name, int category_image, int category_color) {
        this.category_name = category_name;
        this.category_image = category_image;
        this.category_color = category_color;
    }
    public String getCategoryName() {
        return category_name;
    }
    public String getCategory_name() {
        return category_name;
    }
    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }
    public int getCategory_image() {
        return category_image;
    }
    public void setCategory_image(int category_image) {
        this.category_image = category_image;
    }
}
