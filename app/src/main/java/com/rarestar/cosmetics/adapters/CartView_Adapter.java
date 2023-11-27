package com.rarestar.cosmetics.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rarestar.cosmetics.R;
import com.rarestar.cosmetics.models.Cart_Model;
import com.rarestar.cosmetics.retrofit.Retrofit_client;
import com.rarestar.cosmetics.views.MainActivity;
import com.rarestar.cosmetics.views.Product_Activity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartView_Adapter extends RecyclerView.Adapter<CartView_Adapter.ViewHolder> {

    List<Cart_Model> Cart_Models;
    Context context;
    private static final String SHARED_PREF_NAME = "UserInfo";
    private static final String KEY_NAME = "username";
    public static boolean isDeleted = false;
    public CartView_Adapter(List<Cart_Model> Cart_Models, Context context) {
        this.Cart_Models = Cart_Models;
        this.context = context;
    }

    @NonNull
    @Override
    public CartView_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_adapter_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartView_Adapter.ViewHolder holder, int position) {
        holder.textView_productName.setText(Cart_Models.get(position).getProduct_name());
        String price = String.valueOf(Cart_Models.get(position).getPrice());
        holder.textview_price.setText(price);
        Glide.with(holder.image.getContext()).load(Cart_Models.get(position).getProduct_image()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return Cart_Models.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        CardView parent;
        ImageView image,imageView_delete;
        TextView textview_price,textView_productName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            image = itemView.findViewById(R.id.image);
            imageView_delete = itemView.findViewById(R.id.imageView_delete);
            textview_price = itemView.findViewById(R.id.textview_price);
            textView_productName = itemView.findViewById(R.id.textView_productName);

            parent.setOnClickListener(view -> {
                MainActivity.ThisActivity = false;
                Intent intent = new Intent(context, Product_Activity.class);
                intent.putExtra("ProductName",textView_productName.getText().toString());
                context.startActivity(intent);
            });
            imageView_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
                    String username = sharedPreferences.getString(KEY_NAME,null);
                    String ProductName = textView_productName.getText().toString();
                    if (username != null){
                        isDeleted = true;
                        DeleteProductInUserProfileData(username,ProductName);
                    }else {
                        isDeleted = false;
                    }
                }
            });
        }
        public void DeleteProductInUserProfileData(String name, String productName) {
            Call<List<Cart_Model>> call= Retrofit_client.getInstance().getApi().DeleteCart(name, productName);
            call.enqueue(new Callback<List<Cart_Model>>() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onResponse(@NonNull Call<List<Cart_Model>> call, @NonNull Response<List<Cart_Model>> response) {
                    assert response.body() != null;
                    if (response.body().get(0).getMessage().equals("Success")){
                        Toast.makeText(context, "از لیست حذف شد", Toast.LENGTH_SHORT).show();
                        notifyDataSetChanged();
                    }else if (response.body().get(0).getMessage().equals("Failed")){
                        Toast.makeText(context, "محصول وجود ندارد!", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(@NonNull Call<List<Cart_Model>> call, @NonNull Throwable t) {
                    Log.d("CartView_Adapter","Error",t);
                }
            });
        }
    }
}
