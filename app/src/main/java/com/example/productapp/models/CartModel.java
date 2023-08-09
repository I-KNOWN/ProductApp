package com.example.productapp.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Cart")
public class CartModel {

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "product_name")
    String productName;
    @ColumnInfo(name = "product_price")
    double productPrice;
    @ColumnInfo(name = "product_icon")
    int productIcon;
    @ColumnInfo(name = "user_name")
    String user_name;
    @ColumnInfo(name = "email")
    String email;
    @ColumnInfo(name = "quantity")
    int quantity;

    @Ignore
    public CartModel(){}

    public CartModel(String productName, double productPrice, int productIcon, String user_name, String email, int quantity) {
        this.id = 0;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productIcon = productIcon;
        this.user_name = user_name;
        this.email = email;
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductIcon() {
        return productIcon;
    }

    public void setProductIcon(int productIcon) {
        this.productIcon = productIcon;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
