/*
* adapter for recycler view
* */
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
        final int resID;
        MemeCard card = this.cards.get(position);
        final String cardDesc;
        if (card.isLocked()) {
            resID = R.drawable.mystery;
            cardDesc = "Unlock The Card To Find Out!";
        }
        else {
            resID = card.getResId();
            cardDesc = card.getDescription();
        }

        holder.myName.setText(card.getName());
        holder.myImage.setImageResource(resID);

        // click for pop-up page of card information
        if(!CardLibraryActivity.isStart){
            holder.myCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(myContext, CardInformationActivity.class);
                    intent.putExtra("Description", cardDesc);
                    intent.putExtra("ImageID", resID);
                    myContext.startActivity(intent);
                }
            });
        }

        holder.bind(cards.get(position));
    }

    @Override
    public int getItemCount() {
        return this.cards.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView myName;
        ImageView myImage;
        CardView myCardView;
        ImageView myStar;

        public MyViewHolder(View view){
            super(view);
            this.myName = (TextView)itemView.findViewById(R.id.CardName);
            this.myImage = (ImageView)itemView.findViewById(R.id.CardImage);
            this.myCardView = (CardView)itemView.findViewById(R.id.CardView);
            this.myStar = (ImageView)itemView.findViewById(R.id.Selected);
            myStar.bringToFront();
        }

        void bind(final MemeCard card) {
            myStar.setVisibility(card.isChecked() ? View.VISIBLE : View.INVISIBLE);
            myName.setText(card.getName());
            // listener for selecting card
            if(CardLibraryActivity.isStart){
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!card.isLocked()) {
                            card.setChecked(!card.isChecked());
                            myStar.setVisibility(card.isChecked() ? View.VISIBLE : View.INVISIBLE);
                        }
                    }
                });
            }
            // reset the check box for the card
            if(CardLibraryActivity.isCancel){
                if(!card.isLocked()) {
                    if(card.isChecked()) card.setChecked(false);
                    myStar.setVisibility(card.isChecked() ? View.VISIBLE : View.INVISIBLE);
                }
            }
        }
    }

    // return a list of MemeCard the user has selected
    public ArrayList<MemeCard> getSelected() {
        ArrayList<MemeCard> selected = new ArrayList<>();
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).isChecked()) {
                selected.add(cards.get(i));
                //=========================================================================
                // add 5 cards to the battle deck
                // you can add code here
                //=========================================================================
            }
        }
        return selected;
    }
}
