package com.example.quizapp;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Adapter_category extends RecyclerView.Adapter<Adapter_category.viewholder> {
    private List<category_Modal> categoryModelList;

    public Adapter_category(List<category_Modal> categoryModelList) {
        this.categoryModelList = categoryModelList;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.items_category,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        holder.setData(categoryModelList.get(position).getUrl(),categoryModelList.get(position).getName(),categoryModelList.get(position).getSets());

    }

    @Override
    public int getItemCount() {
        return categoryModelList.size();
    }

    class viewholder extends RecyclerView.ViewHolder
    {
        private CircleImageView imgview;
        private TextView title;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            imgview=itemView.findViewById(R.id.imageView);
            title=itemView.findViewById(R.id.title);
        }
        private void setData(String url,final String title,final int sets)
        {
            Glide.with(itemView.getContext()).load(url).into(imgview);
            this.title.setText(title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent setIntent=new Intent(itemView.getContext(),sets_Activity.class);
                    setIntent.putExtra("title", title);
                    setIntent.putExtra("sets", sets);
                    itemView.getContext().startActivity(setIntent);
                }
            });
        }
    }
}
