package com.rarestar.cosmetics.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rarestar.cosmetics.R;
import com.rarestar.cosmetics.models.Member_Model;
import com.rarestar.cosmetics.retrofit.Retrofit_client;

import java.util.List;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register_Activity extends AppCompatActivity {
    EditText editText_username,EditText_firstName,EditText_lastName,
            EditText_email,EditText_password,EditText_rePassword,EditText_state,
            EditText_city,EditText_address,EditText_phone;
    CheckBox show_pass;
    Button btnRegister;
    TextView textView_login;
    boolean isEmpty = true;
    String UserName,FirstName,LastName,Email,Password,State,City,Address,Phone;

    private static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initViews();
    }
    private void initViews(){
        editText_username = findViewById(R.id.editText_username);
        EditText_firstName = findViewById(R.id.EditText_firstName);
        EditText_lastName = findViewById(R.id.EditText_lastName);
        EditText_email = findViewById(R.id.EditText_email);
        EditText_password = findViewById(R.id.EditText_password);
        EditText_rePassword = findViewById(R.id.EditText_rePassword);
        EditText_state = findViewById(R.id.EditText_state);
        EditText_city = findViewById(R.id.EditText_city);
        EditText_address = findViewById(R.id.EditText_address);
        EditText_phone = findViewById(R.id.EditText_phone);

        ImageView imageView_back = findViewById(R.id.imageView_back);
        imageView_back.setOnClickListener(view -> finish());

        show_pass = findViewById(R.id.show_pass);

        btnRegister = findViewById(R.id.btnRegister);

        textView_login = findViewById(R.id.textView_login);
        onClick();
    }

    private void onClick() {
        textView_login.setOnClickListener(view -> {
            Intent Login = new Intent(Register_Activity.this,Login_Activity.class);
            Login.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(Login);
        });
        btnRegister.setOnClickListener(view -> {
            CheckEditTexts();
            CheckUserPassword();
            if (isEmpty){
                Toast.makeText(Register_Activity.this, "لطفا همه فیلد ها رو پر کنبد !", Toast.LENGTH_SHORT).show();
            }else {
                registerUser(UserName, FirstName, LastName, Email, Password, State, City, Address, Phone);
            }
        });
        show_pass.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b){
                EditText_password.setTransformationMethod(new HideReturnsTransformationMethod());
                EditText_rePassword.setTransformationMethod(new HideReturnsTransformationMethod());
            }else {
                EditText_password.setTransformationMethod(new PasswordTransformationMethod());
                EditText_rePassword.setTransformationMethod(new PasswordTransformationMethod());
            }
        });
    }
    private void CheckEditTexts() {
        if (editText_username.getText().toString().isEmpty() || EditText_firstName.getText().toString().isEmpty() ||
                EditText_lastName.getText().toString().isEmpty() ||
                EditText_email.getText().toString().isEmpty() ||
                EditText_state.getText().toString().isEmpty() ||
                EditText_city.getText().toString().isEmpty() ||
                EditText_address.getText().toString().isEmpty() ||
                EditText_phone.getText().toString().isEmpty()){

            isEmpty = true;
        }else {
            if (editText_username.getText().toString().length() < 8){
                editText_username.setError("نام کاربری شما باید ییشتر از هشت کارکتر باشد!");
            }else {
                UserName = editText_username.getText().toString();
            }
            FirstName = EditText_firstName.getText().toString();
            LastName = EditText_lastName.getText().toString();
            State = EditText_state.getText().toString();
            City = EditText_city.getText().toString();
            Address = EditText_address.getText().toString();
            Phone = EditText_phone.getText().toString();
            Email = EditText_email.getText().toString();

            isEmpty = false;
        }
    }

    private void CheckUserPassword(){
        if (!EditText_password.getText().toString().equals(EditText_rePassword.getText().toString())) {
            EditText_rePassword.setError("گذر واژه همخوانی ندارد!");
        }else {
            if (EditText_password.getText().toString().length() >= 8 && EditText_rePassword.getText().toString().length() >= 8){
                Password = EditText_password.getText().toString();
            }else {
                EditText_rePassword.setError("گذر واژه باید بیشتر از 8 حرف باشد!");
            }
        }
    }

    private void registerUser (String userName, String firstName, String lastName,
                              String email, String password, String state,
                              String city, String address, String phone) {

        Call<List<Member_Model>> call = Retrofit_client
                .getInstance()
                .getApi()
                .RegisteredUser(userName, firstName, lastName, email,
                    password, state, city, address, phone);

        call.enqueue(new Callback<List<Member_Model>>() {
            @Override
            public void onResponse(@NonNull Call<List<Member_Model>> call, @NonNull Response<List<Member_Model>> response) {
                assert response.body() != null;
                if (response.body().get(0).getMessage().equals("Success")){
                    Toast.makeText(Register_Activity.this, "ثبت نام شما با موفقیت انجام شد", Toast.LENGTH_SHORT).show();
                    Intent HomeActivity = new Intent(Register_Activity.this,Login_Activity.class);
                    HomeActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    HomeActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(HomeActivity);
                }else if (response.body().get(0).getMessage().equals("Failed")){
                    Toast.makeText(Register_Activity.this, "یک حساب کاربری با این نام وجود دارد!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Member_Model>> call, @NonNull Throwable t) {
                Log.d("Register","Failed",t);
            }
        });
    }
}