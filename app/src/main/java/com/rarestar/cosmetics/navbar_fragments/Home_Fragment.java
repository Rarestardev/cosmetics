package com.rarestar.cosmetics.navbar_fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rarestar.cosmetics.R;
import com.rarestar.cosmetics.adapters.Product_Adapter;
import com.rarestar.cosmetics.models.Product_Model;
import com.rarestar.cosmetics.retrofit.Retrofit_client;
import com.rarestar.cosmetics.views.ProductView_Activity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home_Fragment extends Fragment implements View.OnClickListener {
    CardView cardView_makeUp,cardView_face,cardView_hair,cardView_body;
    TextView more_best_sellers,more_best_brands,more_makeup,more_productBody,more_productFace,more_productHair;
    RecyclerView recyclerView_best_sellers,recyclerView_best_brands,recyclerView_makeup,recyclerView_productBody,
            recyclerView_productFace,recyclerView_productHair;
    View view;
    String NameView;
    public static boolean isFragment = true;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        init();
        RecyclerViewSetLayoutManager();
        ProcessDataBestSellers();
        ProcessDataBestBrands();
        ProcessDataForCosmetics();
        ProcessDataForProductBody();
        ProcessDataForProductHair();
        ProcessDataForProductFace();
        return view;
    }
    private void init(){

        isFragment = true;

        //cardViews
        cardView_makeUp = view.findViewById(R.id.cardView_makeUp);
        cardView_face = view.findViewById(R.id.cardView_face);
        cardView_hair = view.findViewById(R.id.cardView_hair);
        cardView_body = view.findViewById(R.id.cardView_body);

        //textViews
        more_best_sellers= view.findViewById(R.id.more_best_sellers);
        more_best_brands = view.findViewById(R.id.more_best_brands);
        more_makeup      = view.findViewById(R.id.more_makeup);
        more_productBody = view.findViewById(R.id.more_productBody);
        more_productFace = view.findViewById(R.id.more_productFace);
        more_productHair = view.findViewById(R.id.more_productHair);
        more_best_sellers.setOnClickListener(this);
        more_best_brands.setOnClickListener(this);
        more_makeup.setOnClickListener(this);
        more_productBody.setOnClickListener(this);
        more_productFace.setOnClickListener(this);
        more_productHair.setOnClickListener(this);
        CardViewsOnClick();

        //recyclerViews
        recyclerView_best_sellers = view.findViewById(R.id.recyclerView_best_sellers);
        recyclerView_best_brands = view.findViewById(R.id.recyclerView_best_brands);
        recyclerView_makeup = view.findViewById(R.id.recyclerView_makeup);
        recyclerView_productBody = view.findViewById(R.id.recyclerView_productBody);
        recyclerView_productFace = view.findViewById(R.id.recyclerView_productFace);
        recyclerView_productHair = view.findViewById(R.id.recyclerView_productHair);
    }
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getContext(),ProductView_Activity.class);
        if (view == more_best_sellers){
            NameView = "بهترین فروشنگان";
            intent.putExtra("TitleName",NameView);
        }
        if (view == more_best_brands){
            NameView = "بهترین برند ها";
            intent.putExtra("TitleName",NameView);
        }
        if (view == more_makeup){
            NameView = "لوازم آرایشی";
            intent.putExtra("TitleName",NameView);
        }
        if (view == more_productBody){
            NameView = "محصولات بهداشتی بدن";
            intent.putExtra("TitleName",NameView);
        }
        if (view == more_productFace){
            NameView = "محصولات بهداشتی صورت";
            intent.putExtra("TitleName",NameView);
        }
        if (view == more_productHair){
            NameView = "محصولات بهداشتی مو";
            intent.putExtra("TitleName",NameView);
        }
        startActivity(intent);
    }
    private void RecyclerViewSetLayoutManager(){
        recyclerView_best_sellers.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL,false));
        recyclerView_best_brands.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL,false));
        recyclerView_makeup.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL,false));
        recyclerView_productBody.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL,false));
        recyclerView_productFace.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL,false));
        recyclerView_productHair.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL,false));
    }
    private void CardViewsOnClick(){
        cardView_makeUp.setOnClickListener(view -> {
            NameView = "لوازم آرایشی";
            Intent intent = new Intent(getContext(),ProductView_Activity.class);
            intent.putExtra("TitleName",NameView);
            startActivity(intent);
        });
        cardView_face.setOnClickListener(view -> {
            NameView = "محصولات بهداشتی بدن";
            Intent intent = new Intent(getContext(),ProductView_Activity.class);
            intent.putExtra("TitleName",NameView);
            startActivity(intent);
        });
        cardView_hair.setOnClickListener(view -> {
            NameView = "محصولات بهداشتی صورت";
            Intent intent = new Intent(getContext(),ProductView_Activity.class);
            intent.putExtra("TitleName",NameView);
            startActivity(intent);
        });
        cardView_body.setOnClickListener(view -> {
            NameView = "محصولات بهداشتی مو";
            Intent intent = new Intent(getContext(),ProductView_Activity.class);
            intent.putExtra("TitleName",NameView);
            startActivity(intent);
        });
    }
    private void ProcessDataBestSellers(){
        Call<List<Product_Model>> call = Retrofit_client.getInstance().getApi().BestSellers();
        call.enqueue(new Callback<List<Product_Model>>() {
            @Override
            public void onResponse(@NonNull Call<List<Product_Model>> call, @NonNull Response<List<Product_Model>> response) {
                List<Product_Model> list = response.body();
                Product_Adapter adapterBestSellers = new Product_Adapter(getContext(),list);
                recyclerView_best_sellers.setAdapter(adapterBestSellers);
                recyclerView_best_brands.setAdapter(adapterBestSellers);
            }
            @Override
            public void onFailure(@NonNull Call<List<Product_Model>> call, @NonNull Throwable t) {
                Log.d("Client","Error",t);
            }
        });
    }
    private void ProcessDataBestBrands(){
        Call<List<Product_Model>> call = Retrofit_client.getInstance().getApi().BestSellers();
        call.enqueue(new Callback<List<Product_Model>>() {
            @Override
            public void onResponse(@NonNull Call<List<Product_Model>> call, @NonNull Response<List<Product_Model>> response) {
                List<Product_Model> list = response.body();
                Product_Adapter adapterBestBrands = new Product_Adapter(getContext(),list);
                recyclerView_best_brands.setAdapter(adapterBestBrands);
            }

            @Override
            public void onFailure(@NonNull Call<List<Product_Model>> call, @NonNull Throwable t) {
                Log.d("Client","Error",t);
            }
        });
    }
    private void ProcessDataForCosmetics(){
        Call<List<Product_Model>> call = Retrofit_client.getInstance().getApi().CategoryBy("لوازم آرایشی");
        call.enqueue(new Callback<List<Product_Model>>() {
            @Override
            public void onResponse(@NonNull Call<List<Product_Model>> call, @NonNull Response<List<Product_Model>> response) {
                List<Product_Model> list = response.body();
                Product_Adapter cosmetics = new Product_Adapter(getContext(),list);
                recyclerView_makeup.setAdapter(cosmetics);
            }
            @Override
            public void onFailure(@NonNull Call<List<Product_Model>> call, @NonNull Throwable t) {
                Log.d("Category","Error",t);
            }
        });
    }
    private void ProcessDataForProductBody(){
        Call<List<Product_Model>> call = Retrofit_client.getInstance().getApi().CategoryBy("محصولات بهداشتی بدن");
        call.enqueue(new Callback<List<Product_Model>>() {
            @Override
            public void onResponse(@NonNull Call<List<Product_Model>> call, @NonNull Response<List<Product_Model>> response) {
                List<Product_Model> list = response.body();
                Product_Adapter ProductBody = new Product_Adapter(getContext(),list);
                recyclerView_productBody.setAdapter(ProductBody);
            }
            @Override
            public void onFailure(@NonNull Call<List<Product_Model>> call, @NonNull Throwable t) {
                Log.d("Category","Error",t);
            }
        });
    }
    private void ProcessDataForProductHair(){
        Call<List<Product_Model>> call = Retrofit_client.getInstance().getApi().CategoryBy("محصولات بهداشتی مو");
        call.enqueue(new Callback<List<Product_Model>>() {
            @Override
            public void onResponse(@NonNull Call<List<Product_Model>> call, @NonNull Response<List<Product_Model>> response) {
                List<Product_Model> list = response.body();
                Product_Adapter ProductHair = new Product_Adapter(getContext(),list);
                recyclerView_productHair.setAdapter(ProductHair);
            }
            @Override
            public void onFailure(@NonNull Call<List<Product_Model>> call, @NonNull Throwable t) {
                Log.d("Category","Error",t);
            }
        });
    }
    private void ProcessDataForProductFace(){
        Call<List<Product_Model>> call = Retrofit_client.getInstance().getApi().CategoryBy("محصولات بهداشتی صورت");
        call.enqueue(new Callback<List<Product_Model>>() {
            @Override
            public void onResponse(@NonNull Call<List<Product_Model>> call, @NonNull Response<List<Product_Model>> response) {
                List<Product_Model> list = response.body();
                Product_Adapter ProductFace = new Product_Adapter(getContext(),list);
                recyclerView_productFace.setAdapter(ProductFace);
            }
            @Override
            public void onFailure(@NonNull Call<List<Product_Model>> call, @NonNull Throwable t) {
                Log.d("Category","Error",t);
            }
        });
    }
}