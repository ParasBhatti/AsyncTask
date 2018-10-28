package com.example.ricky.parsingdemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHloder> {
    Context context;
    List<PojoClass> list;

    public MyAdapter(Context context, List<PojoClass> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHloder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom,viewGroup,false);
       return new MyViewHloder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHloder myViewHloder, int i) {
        myViewHloder.name.setText(list.get(i).getName());
        myViewHloder.Author.setText(list.get(i).getAuthor());
        if (list.get(i).getImageToUrl()!=null){
            Picasso.get().load(list.get(i).getImageToUrl()).into(myViewHloder.img);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHloder extends RecyclerView.ViewHolder {
        TextView name,Author;
        ImageView img;

        public MyViewHloder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            Author=itemView.findViewById(R.id.Auhtor);
            img=itemView.findViewById(R.id.img);
        }
    }
}
