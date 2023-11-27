package com.rarestar.cosmetics.retrofit;

import com.rarestar.cosmetics.models.Cart_Model;
import com.rarestar.cosmetics.models.Member_Model;
import com.rarestar.cosmetics.models.Product_Model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Retrofit_interface {

    @GET("BestSellers.php")
    Call<List<Product_Model>> BestSellers();

    @POST("Category.php")
    Call<List<Product_Model>> CategoryBy(@Query("Category") String Category);


    @POST("Product_Search.php")
    Call<List<Product_Model>> Search(@Query("product_name") String product_name);

    @POST("GroupData.php")
    Call<List<Product_Model>> SelectGroupProduct (@Query("group_name") String group_name);


    @POST("Register.php")
    Call<List<Member_Model>> RegisteredUser (@Query("username") String username , @Query("first_name") String first_name ,
                                             @Query("last_name") String last_name, @Query("email") String email,
                                             @Query("password") String password, @Query("state") String state,
                                             @Query("city") String city, @Query("address") String address,
                                             @Query("phone_number")String phone_number);


    @POST("Login.php")
    Call<List<Member_Model>> LoginUser (@Query("username") String username , @Query("password") String password);

    @POST("UserInfo.php")
    Call<List<Member_Model>> UserInfo (@Query("username") String username , @Query("password") String password);

    @POST("DeleteCart.php")
    Call<List<Cart_Model>> DeleteCart (@Query("username") String username , @Query("product_name") String product_name);

    @POST("InsertCartData.php")
    Call<List<Cart_Model>> SaveCart (@Query("username") String username , @Query("product_name") String product_name,
                                     @Query("product_image") String product_image,@Query("price") String price);

    @POST("CheckBuyList.php")
    Call<List<Cart_Model>> CheckBuyList (@Query("username") String username , @Query("product_name") String product_name);

    @POST("ShowBuyList.php")
    Call<List<Cart_Model>> ShowBuyList (@Query("username") String username);

    @POST("Product_Data.php")
    Call<List<Product_Model>> ProductData(@Query("product_name") String product_name);
}
