package com.rarestar.cosmetics.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rarestar.cosmetics.R;
import com.rarestar.cosmetics.models.Product_Model;
import com.rarestar.cosmetics.views.MainActivity;
import com.rarestar.cosmetics.views.Product_Activity;
import java.util.List;

public class Product_Adapter extends RecyclerView.Adapter<Product_Adapter.ViewHolder> {
    Context context;
    List<Product_Model> product_models;
    public Product_Adapter(Context context, List<Product_Model> product_models) {
        this.context = context;
        this.product_models = product_models;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_view,parent,false);
        return new ViewHolder(view);
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.product_name.setText(product_models.get(position).getProduct_name());
        String price = String.valueOf(product_models.get(position).getPrice());
        Glide.with(holder.product_image.getContext()).load(product_models.get(position).getImage()).into(holder.product_image);
        holder.product_price.setText(price + " " + "تومان");
       // System.out.println(product_models.get(position).getImage().toString());
    }
    @Override
    public int getItemCount() {
        return product_models.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView product_layout_view;
        ImageView product_image;
        TextView product_name,product_price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            init();
            onClick();
            MainActivity.ThisActivity = true;
        }
        private void init() {
            product_layout_view = itemView.findViewById(R.id.product_layout_view);
            product_image = itemView.findViewById(R.id.product_image);
            //textViews
            product_name = itemView.findViewById(R.id.product_name);
            product_price = itemView.findViewById(R.id.product_price);
        }
        private void onClick(){
            product_layout_view.setOnClickListener(view -> {
                Intent moveToProductActivity = new Intent(context, Product_Activity.class);
                moveToProductActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                moveToProductActivity.putExtra("ProductName",product_models.get(getAdapterPosition()).getProduct_name());
                moveToProductActivity.putExtra("Group",product_models.get(getAdapterPosition()).getGroup_name());
                moveToProductActivity.putExtra("Product_Introduction",product_models.get(getAdapterPosition()).getProduct_introduction());
                moveToProductActivity.putExtra("Property",product_models.get(getAdapterPosition()).getProperty());
                moveToProductActivity.putExtra("Age",product_models.get(getAdapterPosition()).getAge());
                moveToProductActivity.putExtra("How_to_use",product_models.get(getAdapterPosition()).getHow_to_use());
                moveToProductActivity.putExtra("Compounds",product_models.get(getAdapterPosition()).getCompounds());
                moveToProductActivity.putExtra("Warning",product_models.get(getAdapterPosition()).getWarning());
                moveToProductActivity.putExtra("Image",product_models.get(getAdapterPosition()).getImage());
                moveToProductActivity.putExtra("Price",product_models.get(getAdapterPosition()).getPrice());
                moveToProductActivity.putExtra("MadeBy",product_models.get(getAdapterPosition()).getMade_by());
                context.startActivity(moveToProductActivity);
            });
        }
    }
}
