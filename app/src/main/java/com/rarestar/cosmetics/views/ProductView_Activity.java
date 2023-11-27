package com.rarestar.cosmetics.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rarestar.cosmetics.R;
import com.rarestar.cosmetics.adapters.Product_Adapter;
import com.rarestar.cosmetics.models.Product_Model;
import com.rarestar.cosmetics.navbar_fragments.Home_Fragment;
import com.rarestar.cosmetics.retrofit.Retrofit_client;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductView_Activity extends AppCompatActivity {
    RecyclerView recyclerView;
    private String Title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);
        if (Home_Fragment.isFragment){
            Title = getIntent().getStringExtra("TitleName");
        }else{
            Title = getIntent().getStringExtra("Main_TitleName");
        }
        init();
    }
    private void init(){
        TextView textView_title = findViewById(R.id.textView_title);
        ImageView ic_back = findViewById(R.id.ic_back);
        ic_back.setOnClickListener(view -> finish());
        textView_title.setText(Title);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(ProductView_Activity.this,2));

        if (Title.equals("بهترین برند ها")){
            ProcessDataBestBrands();
        }else if (Title.equals("بهترین فروشنگان")){
            ProcessDataBestSellers();
        }else{
            ProcessDataFromDataBaseShowRecyclerView(Title);
        }
    }

    private void ProcessDataFromDataBaseShowRecyclerView(String title) {
        Call<List<Product_Model>> call = Retrofit_client.getInstance().getApi().CategoryBy(title);
        call.enqueue(new Callback<List<Product_Model>>() {
            @Override
            public void onResponse(@NonNull Call<List<Product_Model>> call, @NonNull Response<List<Product_Model>> response) {
                List<Product_Model> list = response.body();
                Product_Adapter ProductBody = new Product_Adapter(ProductView_Activity.this,list);
                recyclerView.setAdapter(ProductBody);
            }
            @Override
            public void onFailure(@NonNull Call<List<Product_Model>> call, @NonNull Throwable t) {
                Log.d("Category","Error",t);
            }
        });
    }
    private void ProcessDataBestBrands(){
        Call<List<Product_Model>> call = Retrofit_client.getInstance().getApi().BestSellers();
        call.enqueue(new Callback<List<Product_Model>>() {
            @Override
            public void onResponse(@NonNull Call<List<Product_Model>> call, @NonNull Response<List<Product_Model>> response) {
                List<Product_Model> list = response.body();
                Product_Adapter adapterBestBrands = new Product_Adapter(ProductView_Activity.this,list);
                recyclerView.setAdapter(adapterBestBrands);
            }

            @Override
            public void onFailure(@NonNull Call<List<Product_Model>> call, @NonNull Throwable t) {
                Log.d("Client","Error",t);
            }
        });
    }
    private void ProcessDataBestSellers(){
        Call<List<Product_Model>> call = Retrofit_client.getInstance().getApi().BestSellers();
        call.enqueue(new Callback<List<Product_Model>>() {
            @Override
            public void onResponse(@NonNull Call<List<Product_Model>> call, @NonNull Response<List<Product_Model>> response) {
                List<Product_Model> list = response.body();
                Product_Adapter adapterBestSellers = new Product_Adapter(ProductView_Activity.this,list);
                recyclerView.setAdapter(adapterBestSellers);
            }
            @Override
            public void onFailure(@NonNull Call<List<Product_Model>> call, @NonNull Throwable t) {
                Log.d("Client","Error",t);
            }
        });
    }
}