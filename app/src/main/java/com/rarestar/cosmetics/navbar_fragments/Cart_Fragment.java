package com.rarestar.cosmetics.navbar_fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rarestar.cosmetics.R;
import com.rarestar.cosmetics.adapters.CartView_Adapter;
import com.rarestar.cosmetics.models.Cart_Model;
import com.rarestar.cosmetics.retrofit.Retrofit_client;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Cart_Fragment extends Fragment {
    List<Cart_Model> list;
    RecyclerView recyclerView;
    TextView textView_message;

    private static final String SHARED_PREF_NAME = "UserInfo";
    private static final String KEY_NAME = "username";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view =  inflater.inflate(R.layout.fragment_cart, container, false);
       recyclerView = view.findViewById(R.id.recyclerView);
       textView_message = view.findViewById(R.id.textView_message);
       recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
       ShowDataBuyList();

       return view;
    }

    private void ShowDataBuyList() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String username = sharedPreferences.getString(KEY_NAME,null);
        if (username != null){
            Call<List<Cart_Model>> listCall = Retrofit_client.getInstance().getApi().ShowBuyList(username);
            listCall.enqueue(new Callback<List<Cart_Model>>() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onResponse(@NonNull Call<List<Cart_Model>> call, @NonNull Response<List<Cart_Model>> response) {
                    if (response.body() != null){
                        recyclerView.setVisibility(View.VISIBLE);
                        textView_message.setVisibility(View.GONE);
                        list = response.body();
                        CartView_Adapter cartView_adapter = new CartView_Adapter(list,getContext());
                        recyclerView.setAdapter(cartView_adapter);
                        recyclerView.refreshDrawableState();
                    }else {
                        if (recyclerView.getItemDecorationCount() == 0){
                            recyclerView.setVisibility(View.GONE);
                            textView_message.setVisibility(View.VISIBLE);
                        }
                    }
                }
                @Override
                public void onFailure(@NonNull Call<List<Cart_Model>> call, @NonNull Throwable t) {
                    Log.d("Cart_Fragment","Error",t);
                }
            });
        }else{
            recyclerView.setVisibility(View.GONE);
            textView_message.setVisibility(View.VISIBLE);
        }
    }
}