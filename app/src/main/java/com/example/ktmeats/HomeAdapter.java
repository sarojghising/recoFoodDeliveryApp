package com.example.ktmeats;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ktmeats.Model.Category;
import com.example.ktmeats.ViewHolder.MenuViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<MenuViewHolder> {

    private Context context;
    private List<Category> CategoryList;

    public HomeAdapter (List<Category> CategoryList, Context context){
        this.CategoryList = CategoryList;
        this.context = context;
    }

    @NonNull

    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item, null, false);
        RecyclerView.LayoutParams rlp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(rlp);
        MenuViewHolder jdv = new MenuViewHolder((view));

        return jdv;
    }

    @Override
    public void onBindViewHolder(@NonNull  MenuViewHolder holder, int position) {

        holder.txtMenuName.setText(CategoryList.get(position).getName());
        Picasso.get().load(CategoryList.get(position).getImage()).into(holder.imageView);
        holder.item.setText(CategoryList.get(position).getCategoryId());
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent foodList = new Intent(context, FoodList.class);
//                //categoryId is the key, just get key of item at this position
//                foodList.putExtra("CategoryId",position);
//                v.getContext().startActivity(foodList);
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return CategoryList.size();
    }
}
