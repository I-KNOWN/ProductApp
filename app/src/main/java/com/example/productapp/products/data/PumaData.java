package com.example.productapp.products.data;

import com.example.productapp.R;
import com.example.productapp.products.ProductModel;

import java.util.ArrayList;
import java.util.List;

public class PumaData {

    public final List<ProductModel> pumaList = new ArrayList<>();

    public PumaData(){
        pumaList.add(new ProductModel(
                "Puma 1",
                100.0,
                R.drawable.puma1
        ));
        pumaList.add(new ProductModel(
                "Puma 2",
                111.1,
                R.drawable.puma2
        ));
        pumaList.add(new ProductModel(
                "Puma 3",
                55.5,
                R.drawable.puma3
        ));
        pumaList.add(new ProductModel(
                "Puma 4",
                90.0,
                R.drawable.puma4
        ));
        pumaList.add(new ProductModel(
                "Puma 5",
                200.0,
                R.drawable.puma5
        ));

    }
}
