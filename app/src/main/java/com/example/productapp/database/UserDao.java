package com.example.productapp.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.productapp.models.UserModel;

@Dao
public interface UserDao {

    @Insert
    public long createUser(UserModel userModel);

    @Query("SELECT * FROM UserCred WHERE email = :email AND password = :password")
    public UserModel userLogin(String email, String password);

    @Query("SELECT * FROM UserCred WHERE email = :email")
    public UserModel preLogin(String email);


}
