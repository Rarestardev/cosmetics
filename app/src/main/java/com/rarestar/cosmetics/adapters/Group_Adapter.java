package com.rarestar.cosmetics.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.rarestar.cosmetics.R;
import com.rarestar.cosmetics.models.group_models;
import com.rarestar.cosmetics.views.GroupViews_Activity;

import java.util.List;

public class Group_Adapter extends RecyclerView.Adapter<Group_Adapter.MyViewHolder> {
    List<group_models> groupModelsList;
    Context context;

    public Group_Adapter(List<group_models> groupModelsList , Context context) {
        this.groupModelsList = groupModelsList;
        this.context=context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textView_group.setText(groupModelsList.get(position).getNameGroup());
    }

    @Override
    public int getItemCount() {
        return groupModelsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textView_group;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textView_group = itemView.findViewById(R.id.textView_group);

            CardView group_layout = itemView.findViewById(R.id.group_layout);
            group_layout.setOnClickListener(view -> {
                String GroupName = textView_group.getText().toString();
                Intent intent = new Intent(context, GroupViews_Activity.class);
                intent.putExtra("GroupName",GroupName);
                context.startActivity(intent);
            });
        }
    }
}
