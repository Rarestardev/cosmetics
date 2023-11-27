package com.rarestar.cosmetics.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.rarestar.cosmetics.R;
import com.rarestar.cosmetics.models.Cart_Model;
import com.rarestar.cosmetics.models.Product_Model;
import com.rarestar.cosmetics.retrofit.Retrofit_client;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Product_Activity extends AppCompatActivity {
    TextView txt_name_product,txt_category,txt_introduction,txt_property,txt_suitable,
            txt_how_to_use,txt_compounds,txt_warning,txt_price,txt_country;
    ImageView product_image,ic_favorite,imageView_buy;
    TextView title_introduction,title_property,title_suitable,title_how_to_use,title_compounds,title_warning;
    CardView cardView_cart,ic_back;
    String Image;
    private static final String SHARED_PREF_NAME = "UserInfo";
    private static final String KEY_NAME = "username";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        init();
        onClick();
        CheckUserBuyList();

    }
    private void init(){
        txt_name_product = findViewById(R.id.txt_name_product);
        txt_category = findViewById(R.id.txt_category);
        txt_introduction = findViewById(R.id.txt_introduction);
        txt_property = findViewById(R.id.txt_property);
        txt_suitable = findViewById(R.id.txt_suitable);
        txt_how_to_use = findViewById(R.id.txt_how_to_use);
        txt_compounds = findViewById(R.id.txt_compounds);
        txt_warning = findViewById(R.id.txt_warning);
        txt_price = findViewById(R.id.txt_price);
        txt_country = findViewById(R.id.txt_country);
        product_image = findViewById(R.id.product_image);
        ic_back = findViewById(R.id.ic_back);
        ic_favorite = findViewById(R.id.ic_favorite);
        imageView_buy = findViewById(R.id.imageView_buy);
        cardView_cart = findViewById(R.id.cardView_cart);
        if (MainActivity.ThisActivity){
            setFields();
        }else{
            String Product = getIntent().getStringExtra("ProductName");
            ProcessDateFromDataBase(Product);
        }

        title_introduction = findViewById(R.id.title_introduction);
        title_property = findViewById(R.id.title_property);
        title_suitable = findViewById(R.id.title_suitable);
        title_how_to_use = findViewById(R.id.title_how_to_use);
        title_compounds = findViewById(R.id.title_compounds);
        title_warning = findViewById(R.id.title_warning);
        checkViews();
    }

    private void ProcessDateFromDataBase(String product) {
        Call<List<Product_Model>> call = Retrofit_client.getInstance().getApi().ProductData(product);
        call.enqueue(new Callback<List<Product_Model>>() {
            @Override
            public void onResponse(@NonNull Call<List<Product_Model>> call, @NonNull Response<List<Product_Model>> response) {
                if (response.body() != null){
                    List<Product_Model> list = response.body();
                    txt_name_product.setText(list.get(0).getProduct_name());
                    txt_category.setText(list.get(0).getCategory());
                    txt_introduction.setText(list.get(0).getProduct_introduction());
                    title_property.setText(list.get(0).getProperty());
                    txt_suitable.setText(list.get(0).getAge());
                    txt_how_to_use.setText(list.get(0).getHow_to_use());
                    txt_compounds.setText(list.get(0).getCompounds());
                    txt_warning.setText(list.get(0).getWarning());
                    String price = String.valueOf(list.get(0).getPrice());
                    txt_price.setText(price);
                    txt_country.setText(list.get(0).getMade_by());
                    String link_image = list.get(0).getImage();
                    Glide.with(Product_Activity.this).load(link_image).into(product_image);
                    checkViews();
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<Product_Model>> call, @NonNull Throwable t) {
                Log.d("ProcessData","error",t);
            }
        });
    }
    @SuppressLint("SetTextI18n")
    private void setFields(){
        txt_name_product.setText(getIntent().getStringExtra("ProductName"));
        txt_category.setText(getIntent().getStringExtra("Group"));
        txt_introduction.setText(getIntent().getStringExtra("Product_Introduction"));
        txt_property.setText(getIntent().getStringExtra("Property"));
        txt_suitable.setText(getIntent().getStringExtra("Age"));
        txt_how_to_use.setText(getIntent().getStringExtra("How_to_use"));
        txt_compounds.setText(getIntent().getStringExtra("Compounds"));
        txt_warning.setText(getIntent().getStringExtra("Warning"));
        String price = String.valueOf(getIntent().getIntExtra("Price",0));
        txt_price.setText(price + " " + "تومان");
        txt_country.setText("ساخت کشور :" + " " + getIntent().getStringExtra("MadeBy"));
        Image = getIntent().getStringExtra("Image");
        Glide.with(this).load(Image).into(product_image);
    }

    private void onClick(){
        ic_favorite.setOnClickListener(firstListener);

        ic_back.setOnClickListener(view -> finish());

        cardView_cart.setOnClickListener(view -> {
            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            String Name = sharedPreferences.getString(KEY_NAME,null);
            String Product = txt_name_product.getText().toString();
            if (Name != null){
                String ProductPrice = txt_price.getText().toString();
                String ProductImageLink = Image;
                SaveProductInUserProfileData(Name,Product,ProductImageLink,ProductPrice);
            }else {
                imageView_buy.setImageResource(R.drawable.ic_go);
                Toast.makeText(Product_Activity.this, "برای خرید محصول باید حساب کاربری داشته باشید", Toast.LENGTH_SHORT).show();
            }
        });
    }
    View.OnClickListener firstListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ic_favorite.setImageResource(R.drawable.baseline_favorite_24);
            Toast.makeText(Product_Activity.this, "به لیست ذخیره اضافه شد...", Toast.LENGTH_SHORT).show();
            ic_favorite.setOnClickListener(secondListener);
        }
    };
    View.OnClickListener secondListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ic_favorite.setImageResource(R.drawable.ic_favorite);
            Toast.makeText(Product_Activity.this, "حذف شد!", Toast.LENGTH_SHORT).show();
            ic_favorite.setOnClickListener(firstListener);
        }
    };
    private void SaveProductInUserProfileData(String username,String productName, String productImageLink, String productPrice) {
        Call<List<Cart_Model>> call=Retrofit_client.getInstance().getApi().SaveCart(username, productName, productImageLink, productPrice);
        call.enqueue(new Callback<List<Cart_Model>>() {
            @Override
            public void onResponse(@NonNull Call<List<Cart_Model>> call, @NonNull Response<List<Cart_Model>> response) {
                assert response.body() != null;
                if (response.body().get(0).getMessage().equals("Success")){
                    imageView_buy.setImageResource(R.drawable.add_task);
                    Toast.makeText(Product_Activity.this, "به لیست خرید اضافه شد...", Toast.LENGTH_SHORT).show();
                }else if (response.body().get(0).getMessage().equals("Failed")){
                    DeleteProductInUserProfileData(username, productName);
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<Cart_Model>> call, @NonNull Throwable t) {
                Log.d("ProductActivity","Error",t);
            }
        });
    }
    private void DeleteProductInUserProfileData(String name, String productName) {
        Call<List<Cart_Model>> call=Retrofit_client.getInstance().getApi().DeleteCart(name, productName);
        call.enqueue(new Callback<List<Cart_Model>>() {
            @Override
            public void onResponse(@NonNull Call<List<Cart_Model>> call, @NonNull Response<List<Cart_Model>> response) {
                assert response.body() != null;
                if (response.body().get(0).getMessage().equals("Success")){
                    imageView_buy.setImageResource(R.drawable.ic_go);
                    Toast.makeText(Product_Activity.this, "از لیست حذف شد", Toast.LENGTH_SHORT).show();
                }else if (response.body().get(0).getMessage().equals("Failed")){
                    Toast.makeText(Product_Activity.this, "محصول وجود ندارد!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<Cart_Model>> call, @NonNull Throwable t) {
                Log.d("ProductActivity","Error",t);
            }
        });
    }
    private void checkViews(){
        if (txt_introduction.getText().toString().isEmpty()){
            title_introduction.setVisibility(View.GONE);
            txt_introduction.setVisibility(View.GONE);
        }else{
            title_introduction.setVisibility(View.VISIBLE);
            txt_introduction.setVisibility(View.VISIBLE);
        }
        if (txt_category.getText().toString().isEmpty()){
            txt_category.setVisibility(View.GONE);
        }else{
            txt_category.setVisibility(View.VISIBLE);
        }
        if (txt_property.getText().toString().isEmpty()){
            title_property.setVisibility(View.GONE);
            txt_property.setVisibility(View.GONE);
        }else{
            title_property.setVisibility(View.VISIBLE);
            txt_property.setVisibility(View.VISIBLE);
        }
        if (txt_suitable.getText().toString().isEmpty()){
            title_suitable.setVisibility(View.GONE);
            txt_suitable.setVisibility(View.GONE);
        }else{
            title_suitable.setVisibility(View.VISIBLE);
            txt_suitable.setVisibility(View.VISIBLE);
        }
        if (txt_how_to_use.getText().toString().isEmpty()){
            title_how_to_use.setVisibility(View.GONE);
            txt_how_to_use.setVisibility(View.GONE);
        }else{
            title_how_to_use.setVisibility(View.VISIBLE);
            txt_how_to_use.setVisibility(View.VISIBLE);
        }
        if (txt_compounds.getText().toString().isEmpty()){
            title_compounds.setVisibility(View.GONE);
            txt_compounds.setVisibility(View.GONE);
        }else{
            title_compounds.setVisibility(View.VISIBLE);
            txt_compounds.setVisibility(View.VISIBLE);
        }
        if (txt_warning.getText().toString().isEmpty()){
            title_warning.setVisibility(View.GONE);
            txt_warning.setVisibility(View.GONE);
        }else{
            title_warning.setVisibility(View.VISIBLE);
            txt_warning.setVisibility(View.VISIBLE);
        }
        if (txt_country.getText().toString().isEmpty()){
            txt_country.setVisibility(View.GONE);
        }else{
            txt_country.setVisibility(View.VISIBLE);
        }
    }

    private void CheckUserBuyList() {
        String Product = txt_name_product.getText().toString();
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String Name = sharedPreferences.getString(KEY_NAME,null);
        if (Name != null){
            Call<List<Cart_Model>> call = Retrofit_client.getInstance().getApi().CheckBuyList(Name,Product);
            call.enqueue(new Callback<List<Cart_Model>>() {
                @Override
                public void onResponse(@NonNull Call<List<Cart_Model>> call, @NonNull Response<List<Cart_Model>> response) {
                    assert response.body() != null;
                    if (response.body().get(0).getMessage().equals("Success")){
                        imageView_buy.setImageResource(R.drawable.add_task);
                    }else if (response.body().get(0).getMessage().equals("Failed")){
                        imageView_buy.setImageResource(R.drawable.ic_go);
                    }
                }
                @Override
                public void onFailure(@NonNull Call<List<Cart_Model>> call, @NonNull Throwable t) {
                    Log.d("ProductActivity","Error",t);
                }
            });
        }
    }
}