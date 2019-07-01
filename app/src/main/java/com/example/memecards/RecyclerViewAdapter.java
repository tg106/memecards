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

import com.example.domainobjects.MemeCard;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context myContext;
    private ArrayList<MemeCard> cards;

    public RecyclerViewAdapter(Context myContext, ArrayList<MemeCard> cards){
        this.myContext = myContext;
        this.cards = cards;
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
        MemeCard card = this.cards.get(position);
        final int resID;
        final String cardDesc;
        final int price;
        if (card.isLocked()) {
            resID = R.drawable.mystery;
            cardDesc = "Unlock The Card To Find Out!";
            price = card.getPrice();
        }
        else {
            resID = card.getResId();
            cardDesc = card.getDescription();
        }

        holder.myName.setText(card.getName());
        holder.myImage.setImageResource(resID);

        holder.myCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(myContext, LibraryPopupCardActivity.class);
                intent.putExtra("Description", cardDesc);
                intent.putExtra("ImageID", resID);
                myContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.cards.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView myName;
        ImageView myImage;
        CardView myCardView;

        public MyViewHolder(View view){
            super(view);
            this.myName = (TextView)itemView.findViewById(R.id.CardName);
            this.myImage = (ImageView)itemView.findViewById(R.id.CardImage);
            this.myCardView = (CardView)itemView.findViewById(R.id.CardView);
        }
    }
}
