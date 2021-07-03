package com.example.ktmeats.ViewHolder;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.ktmeats.FoodList;
import com.example.ktmeats.Interface.ItemClickListener;
import com.example.ktmeats.Model.Category;
import com.example.ktmeats.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;

public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtMenuName;
    public ImageView imageView;
    private static final String TAG = "MenuViewHolder";
    public TextView item;
    FirebaseRecyclerAdapter<Category,MenuViewHolder> adapter;




    private ItemClickListener itemClickListener;


    public MenuViewHolder(View itemView) {
        super(itemView);

        item = (TextView) itemView.findViewById(R.id.menu_item);
        txtMenuName = (TextView) itemView.findViewById(R.id.menu_name);
        imageView = (ImageView) itemView.findViewById(R.id.menu_image);

        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent(v.getContext().getApplicationContext(), FoodList.class);
        intent.putExtra("CategoryId", item.getText().toString());
        //Log.d(TAG ,  "task id : " +  item.getText().toString());
        v.getContext().startActivity(intent);

        //itemClickListener.onClick(v,getAdapterPosition(),false);

    }

}
