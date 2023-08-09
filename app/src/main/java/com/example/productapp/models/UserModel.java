package com.example.productapp.models;

import android.graphics.Bitmap;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "UserCred")
public class UserModel {

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "name")
    String name;
    @ColumnInfo(name = "contact")
    long contact;
    @ColumnInfo(name = "email")
    String email;
    @ColumnInfo(name = "password")
    String password;
    @ColumnInfo(name = "profileIcon")
    Bitmap profileIcon;

    @Ignore
    public UserModel(){

    }

    public UserModel(String name, long contact, String email, String password, Bitmap profileIcon) {
        this.id = 0;
        this.name = name;
        this.contact = contact;
        this.email = email;
        this.password = password;
        this.profileIcon = profileIcon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getContact() {
        return contact;
    }

    public void setContact(long contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Bitmap getProfileIcon() {
        return profileIcon;
    }

    public void setProfileIcon(Bitmap profileIcon) {
        this.profileIcon = profileIcon;
    }
}
