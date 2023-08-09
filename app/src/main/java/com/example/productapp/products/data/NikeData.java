package com.example.productapp.products.data;

import com.example.productapp.R;
import com.example.productapp.products.ProductModel;

import java.util.ArrayList;
import java.util.List;

public class NikeData {

    public final List<ProductModel> nikeList = new ArrayList<>();

    public NikeData(){
        nikeList.add(new ProductModel(
                "Nike 1",
                100.0,
                R.drawable.nike1
        ));
        nikeList.add(new ProductModel(
                "Nike 2",
                111.1,
                R.drawable.nike2
        ));
        nikeList.add(new ProductModel(
                "Nike 3",
                55.5,
                R.drawable.nike3
        ));
        nikeList.add(new ProductModel(
                "Nike 4",
                90.0,
                R.drawable.nike4
        ));
        nikeList.add(new ProductModel(
                "Nike 5",
                200.0,
                R.drawable.nike5
        ));

    }

}
