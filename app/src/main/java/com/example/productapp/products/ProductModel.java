package com.example.productapp.products;

public class ProductModel {

    String Name;
    double price;
    int productIcon;

    public ProductModel(String name, double price, int productIcon) {
        Name = name;
        this.price = price;
        this.productIcon = productIcon;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getProductIcon() {
        return productIcon;
    }

    public void setProductIcon(int productIcon) {
        this.productIcon = productIcon;
    }
}
