package com.example.productapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.example.productapp.database.UserDatabase;
import com.example.productapp.databinding.ActivityCartBinding;
import com.example.productapp.models.CartModel;
import com.example.productapp.products.adapter.CartAdapter;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CartActivity extends AppCompatActivity {

    private ActivityCartBinding binding;

    private CartAdapter cartAdapter;
    ExecutorService executorService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        executorService = Executors.newSingleThreadExecutor();
        setupRecyclerView();
        initButton();
    }

    private void initButton() {

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getOnBackPressedDispatcher().onBackPressed();
                finish();
            }
        });

    }

    private void setupRecyclerView() {

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                List<CartModel> cartList = UserDatabase.getInstance(getApplicationContext()).getCartDao().getAllCartProduct();

                cartAdapter = new CartAdapter(cartList, getApplicationContext());
                binding.rvCart.setAdapter(cartAdapter);
                binding.rvCart.setLayoutManager(new LinearLayoutManager(CartActivity.this));
            }
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cartAdapter != null) {
            cartAdapter = null;
        }
        if (binding != null) {
            binding = null;
        }
        if (executorService != null) {
            executorService.shutdown();
        }
    }
}