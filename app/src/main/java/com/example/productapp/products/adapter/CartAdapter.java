package com.example.productapp.products.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.productapp.R;
import com.example.productapp.database.UserDatabase;
import com.example.productapp.models.CartModel;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.viewHolder> {

    private List<CartModel> cartList;
    private Context context;

    public  CartAdapter(List<CartModel> cartList, Context context){
        executorService = Executors.newSingleThreadExecutor();
        this.cartList = cartList;
        this.context = context;
    }

    public void setChangedItemList(List<CartModel> cartList, int pos){
        this.cartList = cartList;
//        notifyDataSetChanged();
        notifyItemChanged(pos);
    }
    public void setRemovedItemList(List<CartModel> cartList, int pos){
        this.cartList = cartList;
//        notifyDataSetChanged();

        notifyItemRemoved(pos);
        notifyItemRangeChanged(pos, cartList.size());
    }

    ExecutorService executorService;

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_list_adapter, parent, false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        int currentPosition = position;

        Picasso.get()
                .load(cartList.get(position).getProductIcon())
                .into(holder.productIcon);

        String quantity = cartList.get(position).getQuantity()+"";
        holder.tv_Quantity.setText(quantity);
        String price = "$"+(cartList.get(position).getProductPrice() * cartList.get(position).getQuantity());
        holder.tv_Price.setText(price);
        holder.tv_productName.setText(cartList.get(position).getProductName());
        holder.tv_name.setText(cartList.get(position).getUser_name());
        holder.chk_del_all.setChecked(false);
        holder.chk_del_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    holder.tv_del.setText("Delete All");
                } else{
                    holder.tv_del.setText("Single Delete");
                }
            }
        });

        holder.deleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.chk_del_all.isChecked()){
                    removeProduct(cartList.get(currentPosition), holder.getAdapterPosition());
                } else{
                    removeOneQuantity(cartList.get(currentPosition), holder.getAdapterPosition());
                }
            }
        });
    }

    private void removeProduct(CartModel cartModel, int pos) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                int deleted = UserDatabase.getInstance(context).getCartDao().removeProduct(cartModel);
                Log.d("insertedMakesthis", "deleted: " + deleted);
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        cartList.remove(pos);
                        Log.d("insertedMakesthis", "deleted: " + cartList);

                        setRemovedItemList(cartList, pos);
                    }
                });
            }
        });
    }

    private void removeOneQuantity(CartModel cartModel, int pos) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {

                if(cartList.get(pos).getQuantity() == 1){
                    removeProduct(cartList.get(pos), pos);

                }else{
                    int inserted = UserDatabase.getInstance(context).getCartDao().reduceQuantity(cartModel.getEmail(), cartModel.getProductName());
                    Log.d("insertedMakesthis", "inserted: " + inserted);
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            cartList.get(pos).setQuantity(cartList.get(pos).getQuantity() - 1);
                            setChangedItemList(cartList, pos);
                        }
                    });
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        ImageView productIcon;
        CardView deleteIcon;
        TextView tv_name, tv_productName, tv_Price, tv_Quantity, tv_del;
        CheckBox chk_del_all;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            productIcon = itemView.findViewById(R.id.iv_product_icon);
            tv_name = itemView.findViewById(R.id.tv_user);
            tv_Price = itemView.findViewById(R.id.tv_price);
            tv_productName = itemView.findViewById(R.id.tv_name);
            tv_Quantity = itemView.findViewById(R.id.tv_count);
            deleteIcon = itemView.findViewById(R.id.btn_delete);
            chk_del_all = itemView.findViewById(R.id.chk_del_all);
            tv_del = itemView.findViewById(R.id.btn_del_text);
        }
    }
}
