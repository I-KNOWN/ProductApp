package com.example.productapp.database;



import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.productapp.utils.Converter;
import com.example.productapp.models.CartModel;
import com.example.productapp.models.UserModel;

@Database(
        entities = {UserModel.class, CartModel.class},
        version = 1
)
@TypeConverters(Converter.class)
public abstract class UserDatabase extends RoomDatabase {

    public abstract UserDao getUserDao();
    public abstract CartDao getCartDao();
    private static UserDatabase instance;

    public static UserDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context, UserDatabase.class, "UserDB2").build();
        }
        return instance;
    }

}
