package com.example.memecards;

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

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context myContext;
    private List<Card> myCard;

    public RecyclerViewAdapter(Context myContext, List<Card> myCard){
        this.myContext = myContext;
        this.myCard =myCard;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myView;
        LayoutInflater myInflater = LayoutInflater.from(myContext);
        myView = myInflater.inflate(R.layout.cardview_layout, parent, false);
        return new MyViewHolder(myView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.myName.setText(myCard.get(position).getCardName());
        //holder.myImage.setImageResource(myCard.get(position).getCardImage());
        //=========================================================

        holder.myCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(myContext, CardInformationActivity.class);
                //=========================================================
                intent.putExtra("Description", myCard.get(position).getCardName()); //temporary
                //intent.putExtra("ImageID", myCard.get(position).getCardImage());
                myContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myCard.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView myName;
        ImageView myImage;
        CardView myCardView;

        public MyViewHolder(View view){
            super(view);
            myName = (TextView)itemView.findViewById(R.id.CardName);
            myImage = (ImageView)itemView.findViewById(R.id.CardImage);
            myCardView = (CardView)itemView.findViewById(R.id.CardView);
        }
    }
}
