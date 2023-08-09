package com.example.productapp.products.data;

import com.example.productapp.R;
import com.example.productapp.products.ProductModel;

import java.util.ArrayList;
import java.util.List;

public class AdidasData {

    public final List<ProductModel> adidasList = new ArrayList<>();

    public AdidasData(){
        adidasList.add(new ProductModel(
                "Adidas 1",
                100.0,
                R.drawable.adidas1
        ));
        adidasList.add(new ProductModel(
                "Adidas 2",
                111.1,
                R.drawable.adidas2
        ));
        adidasList.add(new ProductModel(
                "Adidas 3",
                55.5,
                R.drawable.adidas3
        ));
        adidasList.add(new ProductModel(
                "Adidas 4",
                90.0,
                R.drawable.adidas4
        ));
        adidasList.add(new ProductModel(
                "Adidas 5",
                200.0,
                R.drawable.adidas5
        ));

    }

}
