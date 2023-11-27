package com.rarestar.cosmetics.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rarestar.cosmetics.R;
import com.rarestar.cosmetics.models.Member_Model;
import com.rarestar.cosmetics.retrofit.Retrofit_client;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login_Activity extends AppCompatActivity {
    CheckBox show_pass;
    Button btnLogin;
    EditText EditText_password,editText_username;
    private static final String SHARED_PREF_NAME = "UserInfo";
    private static final String KEY_NAME = "username";
    private static final String KEY_PASS = "password";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
    }

    private void initViews() {
        ImageView imageView_back = findViewById(R.id.imageView_back);
        imageView_back.setOnClickListener(view -> finish());

        TextView textView_register = findViewById(R.id.textView_register);
        textView_register.setOnClickListener(view -> {
            Intent RegisterActivity = new Intent(Login_Activity.this,Register_Activity.class);
            RegisterActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(RegisterActivity);
        });

        show_pass = findViewById(R.id.show_pass);
        EditText_password = findViewById(R.id.EditText_password);
        editText_username = findViewById(R.id.editText_username);
        btnLogin = findViewById(R.id.btnLogin);
        onClickViews();
    }

    private void onClickViews() {
        show_pass.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b){
                EditText_password.setTransformationMethod(new HideReturnsTransformationMethod());
            }else {
                EditText_password.setTransformationMethod(new PasswordTransformationMethod());
            }
        });
        btnLogin.setOnClickListener(view -> {
            String UserName = editText_username.getText().toString();
            String Password = EditText_password.getText().toString();
            LoginUserAndCheckInDataBase(UserName,Password);
        });
    }

    private void LoginUserAndCheckInDataBase(String userName, String password) {
        Call<List<Member_Model>> call = Retrofit_client.getInstance().getApi().LoginUser(userName, password);
        call.enqueue(new Callback<List<Member_Model>>() {
            @Override
            public void onResponse(@NonNull Call<List<Member_Model>> call, @NonNull Response<List<Member_Model>> response) {
                assert response.body() != null;
                if (response.body().get(0).getMessage().equals("Success")){
                    SaveUserInfoInSharedPref(userName,password);
                    Toast.makeText(Login_Activity.this, "خوش آمدید !", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login_Activity.this,MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK & Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }else if (response.body().get(0).getMessage().equals("Failed")){
                    Toast.makeText(Login_Activity.this, "نام کاربری یا گذر واژه اشتباه است!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<Member_Model>> call, @NonNull Throwable t) {
                Log.d("Login","Failed",t);
                Toast.makeText(Login_Activity.this, "اتصال اینترنت خود را بررسی کنید", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void SaveUserInfoInSharedPref(String userName, String password) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_NAME,userName);
        editor.putString(KEY_PASS,password);
        editor.apply();
    }
}