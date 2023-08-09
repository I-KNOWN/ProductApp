package com.example.productapp.products.data;

import com.example.productapp.R;
import com.example.productapp.products.ProductModel;

import java.util.ArrayList;
import java.util.List;

public class ReebokData {

    public final List<ProductModel> reebokList = new ArrayList<>();

    public ReebokData(){
        reebokList.add(new ProductModel(
                "Reebok 1",
                100.0,
                R.drawable.reebok1
        ));
        reebokList.add(new ProductModel(
                "Reebok 2",
                111.1,
                R.drawable.reebok2
        ));
        reebokList.add(new ProductModel(
                "Reebok 3",
                55.5,
                R.drawable.reebok3
        ));
        reebokList.add(new ProductModel(
                "Reebok 4",
                90.0,
                R.drawable.reebok4
        ));
        reebokList.add(new ProductModel(
                "Reebok 5",
                200.0,
                R.drawable.reebok5
        ));

    }

}
