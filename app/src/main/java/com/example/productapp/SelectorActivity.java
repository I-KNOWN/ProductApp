package com.example.productapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.productapp.authentication.LoginActivity;
import com.example.productapp.authentication.SignupActivity;
import com.example.productapp.database.UserDatabase;
import com.example.productapp.models.UserModel;
import com.example.productapp.databinding.ActivitySelectorBinding;
import com.example.productapp.utils.HelperMethods;
import com.google.gson.Gson;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SelectorActivity extends AppCompatActivity {


    ActivitySelectorBinding binding;
    private HelperMethods helperMethods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectorActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        binding.btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectorActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();

        helperMethods = new HelperMethods(getApplicationContext());

        String email = helperMethods.loadURI();

        if(!email.equals("")){
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    UserModel userModel = UserDatabase.getInstance(getApplicationContext()).getUserDao().preLogin(email);
                    Gson gson = new Gson();
                    Intent intent = new Intent(SelectorActivity.this, MainActivity.class);
                    intent.putExtra("UserData", gson.toJson(userModel));
                    startActivity(intent);
                    finish();
                }
            });
        }

    }
}