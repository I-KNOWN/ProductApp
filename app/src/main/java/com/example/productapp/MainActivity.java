package com.example.productapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.productapp.database.UserDatabase;
import com.example.productapp.models.CartModel;
import com.example.productapp.models.UserModel;
import com.example.productapp.databinding.ActivityMainBinding;
import com.example.productapp.products.ProductModel;
import com.example.productapp.products.adapter.ProductAdapter;
import com.example.productapp.products.data.AdidasData;
import com.example.productapp.products.data.NikeData;
import com.example.productapp.products.data.PumaData;
import com.example.productapp.products.data.ReebokData;
import com.example.productapp.products.data.VansData;
import com.example.productapp.utils.HelperMethods;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;


    private UserModel userData;
    private List<ProductModel> currentList;
    private ProductAdapter productAdapter;
    private AdidasData adidasData;
    private NikeData nikeData;
    private PumaData pumaData;
    private ReebokData reebokData;
    private VansData vansData;

    String[] category_product = {
        "Adidas",
        "Nike",
        "Puma",
        "Reebok",
        "Vans"
    };
    ExecutorService executorService;
    Thread thread;
    private HelperMethods helperMethods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Gson gson = new Gson();
        userData = gson.fromJson(getIntent().getStringExtra("UserData"), UserModel.class);
        helperMethods = new HelperMethods(this);
//        binding.ivProfileIcon.setImageBitmap(userData.getProfileIcon());
        executorService = Executors.newSingleThreadExecutor();
        initData();
        initSearch();
        setUpRecyclerView();
        setUpSpinner();
        initSubmitBtn();
        initButton();

    }

    private void initButton() {
        binding.btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(MainActivity.this);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.logout_dialog_layout);

                CardView btnCancel = dialog.findViewById(R.id.btn_cancel);
                CardView btnLogout = dialog.findViewById(R.id.btn_card_logout);
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                btnLogout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this, SelectorActivity.class);
                        helperMethods.saveURI("");
                        startActivity(intent);
                        finish();
                    }
                });

                dialog.show();
            }
        });
    }

    private void initSubmitBtn() {
        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ProductAdapter.selectedPosition == -1){
                    Toast.makeText(MainActivity.this, "No Product is Selected", Toast.LENGTH_SHORT).show();

                }else{
                    ProductModel productModel = currentList.get(ProductAdapter.selectedPosition);
                    insertOrUpdate(userData.getEmail(), productModel.getName(), productModel);
                }
            }
        });
    }

    public void insertOrUpdate(String email, String productName, ProductModel productModel){
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                List<CartModel> cartModelList = UserDatabase.getInstance(getApplicationContext()).getCartDao().getUsersProduct(email,productName);
                Log.d("listhassomevalue", cartModelList.toString());
                if(cartModelList.isEmpty()){
                    long insert =  UserDatabase.getInstance(getApplicationContext()).getCartDao().addToCart(new CartModel(
                            productModel.getName(),
                            productModel.getPrice(),
                            productModel.getProductIcon(),
                            userData.getName(),
                            userData.getEmail(),
                            1
                    ));

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "Added To Cart", Toast.LENGTH_SHORT).show();
                        }
                    });

                }else{
                    int insert = UserDatabase.getInstance(getApplicationContext()).getCartDao().updateQuantity(email,productName);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "Added To Cart", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        thread.start();
    }

    private void initSearch() {
        binding.serachProduct.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filteredList(newText);
                return true;
            }
        });
    }

    private void filteredList(String newText) {

        List<ProductModel> filteredList = new ArrayList<>();
        for(ProductModel productModel : currentList){
            Log.d("insidethemind", "Count: "+productModel.getName());
            if(productModel.getName().toLowerCase().contains(newText.toLowerCase())){
                Log.d("insidethemind", "count");
                filteredList.add(productModel);
            }
        }

            productAdapter.setProductList(filteredList);
            currentList = filteredList;

            if(currentList.isEmpty() && newText.isEmpty()){
                setCategoryData(binding.spinnerProduct.getSelectedItemPosition());
            }

    }


    private void initData() {
        adidasData = new AdidasData();
        nikeData = new NikeData();
        pumaData = new PumaData();
        reebokData = new ReebokData();
        vansData = new VansData();
    }

    private void setUpSpinner() {
        binding.spinnerProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                setCategoryData(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setCategoryData(int i) {
        switch (category_product[i]){
            case "Adidas":
                productAdapter.setProductList(adidasData.adidasList);
                currentList = adidasData.adidasList;
                binding.serachProduct.clearFocus();
                binding.serachProduct.setQuery("", false);
                break;
            case "Nike":
                productAdapter.setProductList(nikeData.nikeList);
                currentList = nikeData.nikeList;
                binding.serachProduct.clearFocus();
                binding.serachProduct.setQuery("", false);
                break;
            case "Puma":
                productAdapter.setProductList(pumaData.pumaList);
                currentList = pumaData.pumaList;
                binding.serachProduct.clearFocus();
                binding.serachProduct.setQuery("", false);
                break;
            case "Reebok":
                productAdapter.setProductList(reebokData.reebokList);
                currentList = reebokData.reebokList;
                binding.serachProduct.clearFocus();
                binding.serachProduct.setQuery("", false);
                break;
            case "Vans":
                productAdapter.setProductList(vansData.vansList);
                currentList = vansData.vansList;
                binding.serachProduct.clearFocus();
                binding.serachProduct.setQuery("", false);
                break;
        }

    }

    private void setUpRecyclerView() {
        productAdapter = new ProductAdapter(adidasData.adidasList);
        currentList = adidasData.adidasList;
        binding.rvProduct.setAdapter(productAdapter);
        binding.rvProduct.setLayoutManager(new GridLayoutManager(this, 2));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(binding != null){
            binding = null;
        }
    }
}