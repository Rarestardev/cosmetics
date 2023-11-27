package com.rarestar.cosmetics.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rarestar.cosmetics.R;
import com.rarestar.cosmetics.adapters.Product_Adapter;
import com.rarestar.cosmetics.models.Product_Model;
import com.rarestar.cosmetics.retrofit.Retrofit_client;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupViews_Activity extends AppCompatActivity {
    TextView textView_title;
    RecyclerView recyclerView;
    String Group;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_views);
        Group = getIntent().getStringExtra("GroupName");
        initViews();
        showProduct();
    }

    private void initViews(){
        ImageView ic_back = findViewById(R.id.ic_back);
        textView_title = findViewById(R.id.textView_title);
        recyclerView = findViewById(R.id.recyclerView);
        ic_back.setOnClickListener(view -> finish());
        textView_title.setText(Group);

        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
    }
    private void showProduct(){
        Call<List<Product_Model>> call = Retrofit_client.getInstance().getApi().SelectGroupProduct(Group);
        call.enqueue(new Callback<List<Product_Model>>() {
            @Override
            public void onResponse(@NonNull Call<List<Product_Model>> call, @NonNull Response<List<Product_Model>> response) {
                List<Product_Model> list = response.body();
                Product_Adapter adapter = new Product_Adapter(GroupViews_Activity.this,list);
                recyclerView.setAdapter(adapter);
                recyclerView.refreshDrawableState();
                recyclerView.setHasFixedSize(true);
            }
            @Override
            public void onFailure(@NonNull Call<List<Product_Model>> call, @NonNull Throwable t) {
                Log.d("GroupViewsActivity","Warning",t);
            }
        });
    }
}