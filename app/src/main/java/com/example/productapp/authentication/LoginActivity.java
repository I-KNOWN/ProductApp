package com.example.productapp.authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.productapp.MainActivity;
import com.example.productapp.database.UserDatabase;
import com.example.productapp.models.UserModel;
import com.example.productapp.databinding.ActivityLoginBinding;
import com.example.productapp.utils.HelperMethods;
import com.google.gson.Gson;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private HelperMethods helperMethods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        helperMethods = new HelperMethods(getApplicationContext());
        initButtonClick();

    }

    private void initButtonClick() {

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });
        binding.buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getOnBackPressedDispatcher().onBackPressed();
                finish();
            }
        });
    }

    private void userLogin() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                UserModel userModel = UserDatabase.getInstance(getApplicationContext()).getUserDao().userLogin(
                        binding.etEmil.getText().toString(),
                        binding.etPassword.getText().toString()
                );

                if(userModel != null){
                    helperMethods.saveURI(binding.etEmil.getText().toString());
                    Gson gson = new Gson();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("UserData", gson.toJson(userModel));
                    startActivity(intent);
                    finish();
                } else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(binding != null){
            binding = null;
        }
    }
}