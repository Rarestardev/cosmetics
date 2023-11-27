package com.rarestar.cosmetics.navbar_fragments;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.rarestar.cosmetics.R;
import com.rarestar.cosmetics.adapters.Group_Adapter;
import com.rarestar.cosmetics.models.group_models;

import java.util.ArrayList;
import java.util.List;

public class Category_Fragment extends Fragment {
    CardView cardView_makeUp,cardView_face,cardView_hair,cardView_body;
    RecyclerView recyclerView_makeUp,recyclerView_face,recyclerView_hair,recyclerView_body;
    View view;

    List<group_models> group_models;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_category, container, false);
        initViews();
        cardViewsOnClick();
        setLayoutManagerRecyclerViews();
        return view;
    }

    private void initViews(){
        // cardViews:
        cardView_makeUp = view.findViewById(R.id.cardView_makeUp);
        cardView_face = view.findViewById(R.id.cardView_face);
        cardView_hair = view.findViewById(R.id.cardView_hair);
        cardView_body = view.findViewById(R.id.cardView_body);

        //recyclerViews:
        recyclerView_makeUp = view.findViewById(R.id.recyclerView_makeUp);
        recyclerView_face = view.findViewById(R.id.recyclerView_face);
        recyclerView_hair = view.findViewById(R.id.recyclerView_hair);
        recyclerView_body = view.findViewById(R.id.recyclerView_body);
    }

    private void setLayoutManagerRecyclerViews(){
        recyclerView_makeUp.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL,false));
        recyclerView_face.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL,false));
        recyclerView_hair.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL,false));
        recyclerView_body.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL,false));
    }

    private void cardViewsOnClick(){
        cardView_makeUp.setOnClickListener(view -> {
            if (recyclerView_makeUp.getVisibility() == View.GONE){
                setGroupMakeUp();
                recyclerView_makeUp.setVisibility(View.VISIBLE);
                recyclerView_body.setVisibility(View.GONE);
                recyclerView_hair.setVisibility(View.GONE);
                recyclerView_face.setVisibility(View.GONE);
                recyclerView_makeUp.setHasFixedSize(true);
                recyclerView_makeUp.refreshDrawableState();
                recyclerView_makeUp.focusableViewAvailable(cardView_makeUp);
                recyclerView_makeUp.setItemAnimator(new DefaultItemAnimator());
            }else{
                recyclerView_makeUp.setVisibility(View.GONE);
            }
        });
        cardView_face.setOnClickListener(view -> {
            if (recyclerView_face.getVisibility() == View.GONE){
                recyclerView_face.setVisibility(View.VISIBLE);
                recyclerView_makeUp.setVisibility(View.GONE);
                recyclerView_body.setVisibility(View.GONE);
                recyclerView_hair.setVisibility(View.GONE);
                setGroupProductFace();
                recyclerView_face.refreshDrawableState();
                recyclerView_face.focusableViewAvailable(cardView_face);
                recyclerView_face.setItemAnimator(new DefaultItemAnimator());
            }else{
                recyclerView_face.setVisibility(View.GONE);
            }
        });
        cardView_hair.setOnClickListener(view -> {
            if (recyclerView_hair.getVisibility() == View.GONE){
                recyclerView_hair.setVisibility(View.VISIBLE);
                recyclerView_face.setVisibility(View.GONE);
                recyclerView_makeUp.setVisibility(View.GONE);
                recyclerView_body.setVisibility(View.GONE);
                setGroupProductHair();
                recyclerView_hair.setHasFixedSize(true);
                recyclerView_hair.refreshDrawableState();
                recyclerView_hair.focusableViewAvailable(cardView_hair);
                recyclerView_hair.setItemAnimator(new DefaultItemAnimator());
            }else{
                recyclerView_hair.setVisibility(View.GONE);
            }
        });
        cardView_body.setOnClickListener(view -> {
            if (recyclerView_body.getVisibility() == View.GONE){
                recyclerView_body.setVisibility(View.VISIBLE);
                recyclerView_hair.setVisibility(View.GONE);
                recyclerView_face.setVisibility(View.GONE);
                recyclerView_makeUp.setVisibility(View.GONE);
                setGroupProductBody();
                recyclerView_body.setHasFixedSize(true);
                recyclerView_body.refreshDrawableState();
                recyclerView_body.focusableViewAvailable(cardView_body);
                recyclerView_body.setItemAnimator(new DefaultItemAnimator());
            }else{
                recyclerView_body.setVisibility(View.GONE);
            }
        });
    }
    private void setGroupMakeUp(){
        if (group_models == null){
            group_models=new ArrayList<>();
        }else {
            group_models.clear();
        }
        // list group product for cosmetics(Make Up) create list manual
        group_models.add(new group_models("رژ لب"));
        group_models.add(new group_models("پنکیک"));
        group_models.add(new group_models("سایه چشم"));
        group_models.add(new group_models("خط چشم"));
        group_models.add(new group_models("ریمل"));
        group_models.add(new group_models("رژ گونه"));
        group_models.add(new group_models("پرایمر"));
        group_models.add(new group_models("کانسیلر"));
        group_models.add(new group_models("کرم پودر"));

        Group_Adapter group_adapterMakeUp = new Group_Adapter(group_models,getContext());
        recyclerView_makeUp.setAdapter(group_adapterMakeUp);
    }
    private void setGroupProductFace(){
        if (group_models == null){
            group_models=new ArrayList<>();
        }else {
            group_models.clear();
        }
        // list group product // create list manual
        group_models.add(new group_models("ضد آفتاب"));
        group_models.add(new group_models("آبرسان و مرطوب کننده"));
        group_models.add(new group_models("ژل شستشو"));
        group_models.add(new group_models("کرم و سرم ویتامین سی"));
        group_models.add(new group_models("ضد چروک"));
        group_models.add(new group_models("ضد جوش"));
        group_models.add(new group_models("دور چشم"));
        group_models.add(new group_models("ترمیم کننده"));
        group_models.add(new group_models("میسلار واتر"));
        group_models.add(new group_models("تونر"));
        group_models.add(new group_models("کرم روشن کننده و لایه بردار"));

        Group_Adapter group_adapter= new Group_Adapter(group_models,getContext());
        recyclerView_face.setAdapter(group_adapter);
    }
    private void setGroupProductHair(){
        if (group_models == null){
            group_models=new ArrayList<>();
        }else {
            group_models.clear();
        }
        // list group product // create list manual
        group_models.add(new group_models("َشامپو"));
        group_models.add(new group_models("لوسیون"));
        group_models.add(new group_models("ماسک مو"));
        group_models.add(new group_models("نرم کننده"));
        group_models.add(new group_models("سرم مو"));

        Group_Adapter group_adapterMakeUp = new Group_Adapter(group_models,getContext());
        recyclerView_hair.setAdapter(group_adapterMakeUp);
    }
    private void setGroupProductBody(){
        if (group_models == null){
            group_models=new ArrayList<>();
        }else {
            group_models.clear();
        }
        // list group product // create list manual
        group_models.add(new group_models("مام"));
        group_models.add(new group_models("دئودرانت"));
        group_models.add(new group_models("بادی اسپلش"));
        group_models.add(new group_models("کرم روشن گننده"));
        group_models.add(new group_models("کرم ضد جوش"));
        group_models.add(new group_models("شامپو"));
        group_models.add(new group_models("لوسیون"));

        Group_Adapter group_adapterMakeUp = new Group_Adapter(group_models,getContext());
        recyclerView_body.setAdapter(group_adapterMakeUp);
    }
}