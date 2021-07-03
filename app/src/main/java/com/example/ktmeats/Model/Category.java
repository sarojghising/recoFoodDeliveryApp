package com.example.ktmeats.Model;

public class Category {
    private String name;
    private String image;
    private String CategoryId;


    public Category(String name, String image, String CategoryId) {
        this.name = name;
        this.image = image;
        this.CategoryId = CategoryId;
    }

    public  String getName() {
        return name;
    }

    public void setName(String name) {
        name = name;
    }

    public  String getImage() {
        return image;
    }

    public void setImage(String image) {
        image = image;
    }

    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String CategoryId) {
        CategoryId = CategoryId;
    }
}
