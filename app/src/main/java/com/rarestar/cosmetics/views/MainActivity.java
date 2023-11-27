package com.rarestar.cosmetics.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.rarestar.cosmetics.R;
import com.rarestar.cosmetics.adapters.Navigation_Adapter;
import com.rarestar.cosmetics.adapters.Product_Adapter;
import com.rarestar.cosmetics.models.Product_Model;
import com.rarestar.cosmetics.navbar_fragments.Cart_Fragment;
import com.rarestar.cosmetics.navbar_fragments.Category_Fragment;
import com.rarestar.cosmetics.navbar_fragments.Home_Fragment;
import com.rarestar.cosmetics.navbar_fragments.Profile_Fragment;
import com.rarestar.cosmetics.retrofit.Retrofit_client;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    TabLayout navigation_bottom;
    ViewPager viewPager;
    ImageView ic_open_drawer,ic_search;
    DrawerLayout drawer_layout;
    NavigationView navigationView;
    RecyclerView recyclerView_search;
    Dialog SearchDialog;
    Intent ProductViews;
    public static boolean ThisActivity = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Home_Fragment.isFragment = false;
        ThisActivity = true;
        init();
        onClick();
        viewPagerSet();
    }
    private void init(){
        navigation_bottom = findViewById(R.id.navigation_bottom);
        viewPager = findViewById(R.id.viewPager);
        ic_open_drawer = findViewById(R.id.ic_open_drawer);
        drawer_layout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);
        ic_search = findViewById(R.id.ic_search);
        navigationView.bringToFront();
        ProductViews = new Intent(MainActivity.this,ProductView_Activity.class);
    }
    @SuppressLint({"RtlHardcoded", "NonConstantResourceId"})
    private void onClick() {

        ic_search.setOnClickListener(view -> OpenDialogSearch());

        ic_open_drawer.setOnClickListener(view -> drawer_layout.openDrawer(Gravity.RIGHT));
        navigationView.setNavigationItemSelectedListener(item -> {
            String title;
            switch (item.getItemId()){
                case R.id.menu_favorite:
                    drawer_layout.closeDrawers();
                    Intent FavoritePage = new Intent(MainActivity.this,Favorite_Activity.class);
                    startActivity(FavoritePage);
                    break;
                case R.id.menu_brands:
                    drawer_layout.closeDrawers();
                    title = "بهترین برند ها";
                    ProductViews.putExtra("Main_TitleName",title);
                    Home_Fragment.isFragment = false;
                    startActivity(ProductViews);
                    break;
                case R.id.menu_sellers:
                    drawer_layout.closeDrawers();
                    title = "بهترین فروشنگان";
                    Home_Fragment.isFragment = false;
                    ProductViews.putExtra("Main_TitleName",title);
                    startActivity(ProductViews);
                    break;
                case R.id.menu_cosmetics:
                    drawer_layout.closeDrawers();
                    title = "لوازم آرایشی";
                    Home_Fragment.isFragment = false;
                    ProductViews.putExtra("Main_TitleName",title);
                    startActivity(ProductViews);
                    break;
                case R.id.menu_face:
                    drawer_layout.closeDrawers();
                    title = "محصولات بهداشتی صورت";
                    Home_Fragment.isFragment = false;
                    ProductViews.putExtra("Main_TitleName",title);
                    startActivity(ProductViews);
                    break;
                case R.id.menu_hair:
                    drawer_layout.closeDrawers();
                    title = "محصولات بهداشتی مو";
                    Home_Fragment.isFragment = false;
                    ProductViews.putExtra("Main_TitleName",title);
                    startActivity(ProductViews);
                    break;
                case R.id.menu_body:
                    drawer_layout.closeDrawers();
                    title = "محصولات بهداشتی بدن";
                    Home_Fragment.isFragment = false;
                    ProductViews.putExtra("Main_TitleName",title);
                    startActivity(ProductViews);
                    break;
                case R.id.menu_setting:
                    drawer_layout.closeDrawers();
                 //   Intent Setting = new Intent(MainActivity.this,Settings_Activity.class);
                  //  startActivity(Setting);
                    break;
                case R.id.menu_contactUs:
                    drawer_layout.closeDrawers();
                    // show dialog
                    break;
                case R.id.menu_exit:
                    drawer_layout.closeDrawers();
                    finish();
                    break;
            }
            return true;
        });
    }
    private void viewPagerSet(){
        navigation_bottom.setupWithViewPager(viewPager);
        navigation_bottom.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        Navigation_Adapter vpAdapter = new Navigation_Adapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vpAdapter.addFragment(new Home_Fragment(),"خانه");
        vpAdapter.addFragment(new Category_Fragment(),"دسته بندی");
        vpAdapter.addFragment(new Cart_Fragment(),"لیست خرید");
        vpAdapter.addFragment(new Profile_Fragment(),"پروفایل");
        vpAdapter.notifyDataSetChanged();
        viewPager.setAdapter(vpAdapter);
        viewPager.setLayoutDirection(View.LAYOUT_DIRECTION_INHERIT);
        navigation_bottom.getTabAt(0).setIcon(R.drawable.ic_home);
        navigation_bottom.getTabAt(1).setIcon(R.drawable.baseline_category_24);
        navigation_bottom.getTabAt(2).setIcon(R.drawable.outline_shopping_cart_24);
        navigation_bottom.getTabAt(3).setIcon(R.drawable.baseline_supervised_user_circle_24);
    }
    private void OpenDialogSearch() {
        SearchDialog = new Dialog(this);
        SearchDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        SearchDialog.setContentView(R.layout.dialog_search);
        SearchDialog.show();
        SearchDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.MATCH_PARENT);
        SearchDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        SearchDialog.getWindow().getAttributes().windowAnimations= R.style.DialogAnimation;
        SearchDialog.getWindow().setGravity(Gravity.BOTTOM);

        ImageView ic_close_dialog = SearchDialog.findViewById(R.id.ic_close_dialog);
        ic_close_dialog.setOnClickListener(view -> SearchDialog.hide());

        EditText editText_search = SearchDialog.findViewById(R.id.editText_search);
        editText_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (!s.toString().isEmpty()){
                    String KeySearch = s.toString();
                    SearchInDataBase(KeySearch);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        recyclerView_search = SearchDialog.findViewById(R.id.recyclerView_search);
        recyclerView_search.setLayoutManager(new GridLayoutManager(MainActivity.this,2));
        ProcessData();
    }

    private void ProcessData(){
        Call<List<Product_Model>> call = Retrofit_client.getInstance().getApi().BestSellers();
        call.enqueue(new Callback<List<Product_Model>>() {
            @Override
            public void onResponse(@NonNull Call<List<Product_Model>> call, @NonNull Response<List<Product_Model>> response) {
                List<Product_Model> list = response.body();
                Product_Adapter adapter = new Product_Adapter(MainActivity.this,list);
                recyclerView_search.setAdapter(adapter);
                recyclerView_search.refreshDrawableState();
                recyclerView_search.hasFixedSize();
            }

            @Override
            public void onFailure(@NonNull Call<List<Product_Model>> call, @NonNull Throwable t) {
                Log.d("Client","Error",t);
            }
        });
    }
    private void SearchInDataBase(String keySearch) {
        Call<List<Product_Model>> call = Retrofit_client.getInstance().getApi().Search(keySearch);
        call.enqueue(new Callback<List<Product_Model>>() {
            @Override
            public void onResponse(Call<List<Product_Model>> call, Response<List<Product_Model>> response) {
                List<Product_Model> SearchList = response.body();
                Product_Adapter adapter = new Product_Adapter(MainActivity.this,SearchList);
                adapter.notifyDataSetChanged();
                recyclerView_search.setAdapter(adapter);
                recyclerView_search.refreshDrawableState();
                recyclerView_search.hasFixedSize();
            }

            @Override
            public void onFailure(Call<List<Product_Model>> call, Throwable t) {

            }
        });
    }

    @SuppressLint("RtlHardcoded")
    @Override
    public void onBackPressed() {
        if (drawer_layout.isDrawerOpen(Gravity.RIGHT)){
            drawer_layout.closeDrawers();
        }else {
            super.onBackPressed();
        }
    }
}