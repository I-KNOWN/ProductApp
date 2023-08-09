package com.example.productapp.utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

import java.net.URI;

public class HelperMethods {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public HelperMethods(Context context){
        preferences = context.getSharedPreferences("userPref", Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void saveURI(String email){
        editor.putString("email", email);
        editor.commit();
    }

    public String loadURI(){
        return preferences.getString("email", "") ;
    }

}
