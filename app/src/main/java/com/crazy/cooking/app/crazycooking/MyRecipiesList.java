package com.crazy.cooking.app.crazycooking;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.crazy.cooking.app.crazycooking.model.MyUserJson;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;


public class MyRecipiesList extends RecyclerView.Adapter<MyRecipiesList.RecipiesView> {
    Context c;
    ArrayList<MyUserJson> jsonReciList;
    public MyRecipiesList(Context context, ArrayList<MyUserJson> recipieList) {
        this.c=context;
        this.jsonReciList=recipieList;
    }
    @Override
    public MyRecipiesList.RecipiesView onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.recipiecategory,parent,false);
        return new RecipiesView(v);
    }
    @Override
    public void onBindViewHolder(MyRecipiesList.RecipiesView holder, final int position) {
       // URL s=URLResponse.buildURL(neMyUserJson.get(position).getName());
        MyUserJson myUserJson=jsonReciList.get(position);
        Glide.with(c).load("https://openclipart.org/image/800px/svg_to_png/181486/Cake.png").into(holder.imageView);
       holder.textView.setText(myUserJson.getName());
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(c,ItemListActivity.class);
                intent.putExtra("ingredientsInfo",jsonReciList.get(position));
                Log.i("jsonRecilistclic", jsonReciList.get(position).getName());
                c.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return jsonReciList.size();
    }
    public class RecipiesView extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;
        int position = getLayoutPosition();
        public RecipiesView(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.id_main_image);
            textView=itemView.findViewById(R.id.tv_first_reciepiecategory);
        }
    }
}

