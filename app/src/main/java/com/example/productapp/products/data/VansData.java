package com.example.productapp.products.data;

import com.example.productapp.R;
import com.example.productapp.products.ProductModel;

import java.util.ArrayList;
import java.util.List;

public class VansData {

    public final List<ProductModel> vansList = new ArrayList<>();

    public VansData(){
        vansList.add(new ProductModel(
                "Vans 1",
                100.0,
                R.drawable.vans1
        ));
        vansList.add(new ProductModel(
                "Vans 2",
                111.1,
                R.drawable.vans2
        ));
        vansList.add(new ProductModel(
                "Vans 3",
                55.5,
                R.drawable.vans3
        ));
        vansList.add(new ProductModel(
                "Vans 4",
                90.0,
                R.drawable.vans4
        ));
        vansList.add(new ProductModel(
                "Vans 5",
                200.0,
                R.drawable.vans5
        ));

    }

}
