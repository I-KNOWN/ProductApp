package com.example.productapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.productapp.models.CartModel;

import java.util.List;

@Dao
public interface CartDao {

    @Insert
    public long addToCart(CartModel cartModel);

    @Query("SELECT * FROM cart")
    public List<CartModel> getAllCartProduct();

    @Query("SELECT * FROM cart WHERE email =:email AND product_name = :productName")
    public List<CartModel> getUsersProduct(String email, String productName);

    @Query("UPDATE cart SET quantity = quantity + 1 WHERE email =:email AND product_name = :productName")
    public int updateQuantity(String email, String productName);

    @Query("UPDATE cart SET quantity = quantity - 1 WHERE email =:email AND product_name = :productName")
    public int reduceQuantity(String email, String productName);

    @Delete
    public int removeProduct(CartModel cartModel);
}
