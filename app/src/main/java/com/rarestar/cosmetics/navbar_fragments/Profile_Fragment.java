package com.rarestar.cosmetics.navbar_fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rarestar.cosmetics.R;
import com.rarestar.cosmetics.models.Member_Model;
import com.rarestar.cosmetics.retrofit.Retrofit_client;
import com.rarestar.cosmetics.views.Login_Activity;
import com.rarestar.cosmetics.views.Register_Activity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profile_Fragment extends Fragment {
    Button btn_register,btn_login;
    LinearLayout layout_registered,layout_unRegistered;
    View view;
    SharedPreferences sharedPreferences;
    TextView view_username,view_firstName,view_lastName,view_email,view_state,view_city,view_address,view_phone;
    CardView cardView_UserInfo,cardView_forgetPass,cardView_logout;
    Dialog userInfoDialog;
    private static final String SHARED_PREF_NAME = "UserInfo";
    private static final String KEY_NAME = "username";
    private static final String KEY_PASS = "password";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        initViews();
        CheckUserInSharedPref();

        return view;
    }

    private void initViews(){
        btn_register = view.findViewById(R.id.btn_register);
        btn_login = view.findViewById(R.id.btn_login);
        layout_registered = view.findViewById(R.id.layout_registered);
        layout_unRegistered = view.findViewById(R.id.layout_unRegistered);
    }
    private void CheckUserInSharedPref() {
        sharedPreferences = getContext().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String Name = sharedPreferences.getString(KEY_NAME,null);
        String Pass = sharedPreferences.getString(KEY_PASS,null);
        if (Name != null || Pass != null){
            layout_unRegistered.setVisibility(View.GONE);
            layout_registered.setVisibility(View.VISIBLE);
            UserInfoShow(Name,Pass);
        }else{
            layout_unRegistered.setVisibility(View.VISIBLE);
            layout_registered.setVisibility(View.GONE);
            onClickForUnRegisteredUser();
        }
    }
    private void onClickForUnRegisteredUser() {
        btn_login.setOnClickListener(view -> {
            Intent login_page = new Intent(getContext(), Login_Activity.class);
            login_page.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(login_page);
        });
        btn_register.setOnClickListener(view -> {
            Intent register_page = new Intent(getContext(), Register_Activity.class);
            register_page.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(register_page);
        });
    }

    private void UserInfoShow(String Username,String Password) {
        TextView username = view.findViewById(R.id.username);
        username.setText(Username);
        onClickForRegisteredUser(Username,Password);

    }
    private void onClickForRegisteredUser(String username,String password) {
        cardView_UserInfo = view.findViewById(R.id.cardView_UserInfo);
        cardView_forgetPass = view.findViewById(R.id.cardView_forgetPass);
        cardView_logout = view.findViewById(R.id.cardView_logout);

        cardView_UserInfo.setOnClickListener(view -> {
            userInfoDialog = new Dialog(getContext());
            userInfoDialog.setContentView(R.layout.layout_user_info);
            userInfoDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            userInfoDialog.getWindow().setGravity(Gravity.CENTER);
            userInfoDialog.show();
            view_username = userInfoDialog.findViewById(R.id.view_username);
            view_firstName = userInfoDialog.findViewById(R.id.view_firstName);
            view_lastName = userInfoDialog.findViewById(R.id.view_lastName);
            view_email = userInfoDialog.findViewById(R.id.view_email);
            view_state = userInfoDialog.findViewById(R.id.view_state);
            view_city = userInfoDialog.findViewById(R.id.view_city);
            view_address = userInfoDialog.findViewById(R.id.view_address);
            view_phone = userInfoDialog.findViewById(R.id.view_phone);
            SelectInfoUserInDatabaseAndShowViews(username,password);
        });
        cardView_forgetPass.setOnClickListener(view ->
                Toast.makeText(getContext(), "demo app!", Toast.LENGTH_SHORT).show());

        cardView_logout.setOnClickListener(view -> {
            Dialog dialog = new Dialog(getContext());
            dialog.setContentView(R.layout.delete_account);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setGravity(Gravity.CENTER);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.show();
        });
    }
    private void SelectInfoUserInDatabaseAndShowViews(String username, String password) {
        Call<List<Member_Model>> call = Retrofit_client.getInstance().getApi().UserInfo(username, password);
        call.enqueue(new Callback<List<Member_Model>>() {
            @Override
            public void onResponse(@NonNull Call<List<Member_Model>> call, @NonNull Response<List<Member_Model>> response) {
                if (response.body() != null){
                    List<Member_Model> list = response.body();
                    view_username.setText(list.get(0).getUsername());
                    view_firstName.setText(list.get(0).getFirst_name());
                    view_lastName.setText(list.get(0).getLast_name());
                    view_email.setText(list.get(0).getEmail());
                    view_state.setText(list.get(0).getState());
                    view_city.setText(list.get(0).getCity());
                    view_address.setText(list.get(0).getAddress());
                    view_phone.setText(list.get(0).getPhone_number());
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<Member_Model>> call, @NonNull Throwable t) {
                Log.d("Profile_Fragment","Failed!",t);
            }
        });
    }
}