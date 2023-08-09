package com.example.productapp.products.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.productapp.R;
import com.example.productapp.products.ProductModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.viewHolder> {


    List<ProductModel> productList;

    public static int selectedPosition = -1;

    public ProductAdapter(List<ProductModel> productList){
        this.productList = productList;
    }

    public void setProductList(List<ProductModel> productList){
        this.productList = productList;
        notifyDataSetChanged();
        selectedPosition = -1;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_adapter, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Picasso.get()
                .load(productList.get(position).getProductIcon())
                .into(holder.productIcon);

        holder.productName.setText(productList.get(position).getName());
        String price = "$"+productList.get(position).getPrice();
        holder.productPrice.setText(price);

        if (selectedPosition == position){
            holder.productCheck.setImageResource(R.drawable.ic_check_dark);
        } else {
            holder.productCheck.setImageResource(R.drawable.ic_check_light);
        }

        holder.productCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedPosition == holder.getAdapterPosition()){
                    selectedPosition =-1;
                } else {
                    selectedPosition = holder.getAdapterPosition();
                }

                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        ImageView productIcon, productCheck;
        TextView productName, productPrice;
        CardView productCard;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            productIcon = itemView.findViewById(R.id.iv_product_icon);
            productCheck = itemView.findViewById(R.id.iv_check);
            productName = itemView.findViewById(R.id.tv_name);
            productPrice = itemView.findViewById(R.id.tv_price);
            productCard = itemView.findViewById(R.id.card_product);
        }
    }
}
