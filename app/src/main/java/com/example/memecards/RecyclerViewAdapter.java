package com.example.memecards;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private ArrayList<String> Names = new ArrayList<>();
    private ArrayList<String> ImageURLs = new ArrayList<>();
    private Context Contexts;

    public RecyclerViewAdapter(Context contexts, ArrayList<String> names, ArrayList<String> imageURLs) {
        Names = names;
        ImageURLs = imageURLs;
        Contexts = contexts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_card_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(Contexts)
                .asBitmap()
                .load(ImageURLs.get(position))
                .into(holder.image);

        holder.title.setText(Names.get(position));
    }

    @Override
    public int getItemCount() {
        return Names.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = (ImageView) itemView.findViewById(R.id.CardImage);
            title = (TextView) itemView.findViewById(R.id.CardName);
        }
    }
}
