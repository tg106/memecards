package com.example.presentation;

import android.app.Activity;
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
import com.example.memecards.R;
import com.example.memedatabase.sqlite.implementations.BattleDeck;
import com.example.memedatabase.dbinterface.BattleDeckInterface;
import com.example.memedatabase.sqlite.implementations.MasterDeck;
import com.example.memedatabase.dbinterface.MasterDeckInterface;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context myContext;
    private ArrayList<MemeCard> cards;
    private static MasterDeckInterface masterDeck;
    private static BattleDeckInterface battleDeck;

    public RecyclerViewAdapter(Context myContext, ArrayList<MemeCard> cards){
        this.myContext = myContext;
        this.cards = cards;
        this.masterDeck = new MasterDeck(myContext);
        this.battleDeck = new BattleDeck(myContext);
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
        MemeCard card = masterDeck.retrieveCard(this.cards.get(position).getName());

        final String cardDesc;
        final String name;
        final int price;
        final int resID;
        final boolean locked;
        final String upv;
        final String tag;

        if (card.isLocked()) {
            resID = R.drawable.mystery;
            cardDesc = "Unlock The Card To Find Out!";
            price = card.getPrice();
            locked = true;
            name = card.getName();
            upv = "";
            tag = "";
        }
        else {
            resID = card.getResId();
            cardDesc = card.getDescription();
            price = 0;
            locked = false;
            name = card.getName();
            upv = card.getUpvotesStr();
            tag = card.getTag();
        }

        holder.myName.setText(card.getName());
        holder.myImage.setImageResource(resID);

        holder.myCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(myContext, LibraryPopupCardActivity.class);
                intent.putExtra("Description", cardDesc);
                intent.putExtra("ImageID", resID);
                intent.putExtra("Price", price);
                intent.putExtra("Locked", locked);
                intent.putExtra("Name", name);
                intent.putExtra("Position", position);
                intent.putExtra("Upv", upv);
                intent.putExtra("Tag", tag);
                ((Activity)myContext).startActivityForResult(intent, 1);
            }
        });

        if(!CardLibraryActivity.showDeck){
            holder.bind(cards.get(position));
        }
        else{
            holder.showDeck(cards.get(position));
            CardLibraryActivity.showDeck = false;
        }
    }

    // return a list of MemeCard the user has selected
    public ArrayList<MemeCard> getSelected() {
        ArrayList<MemeCard> selected = new ArrayList<>();
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).isChecked()) {
                selected.add(cards.get(i));
            }
        }
        return selected;
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
//            if (battleDeck.retrieveCard(card.getName()) != null) {
//                card.setChecked(true);
//                myStar.setVisibility(View.VISIBLE);
//            } else {
//                myStar.setVisibility(View.INVISIBLE);
//            }
            myStar.setVisibility(View.INVISIBLE);
            myName.setText(card.getName());
            // listener for selecting card
            if(CardLibraryActivity.isStart){
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!masterDeck.retrieveCard(card.getName()).isLocked()) {
                            card.setChecked(!card.isChecked());
                            myStar.setVisibility(card.isChecked() ? View.VISIBLE : View.INVISIBLE);
                        }
                    }
                });
            }
            // reset the check box for the card
            if(CardLibraryActivity.isCancel){
                if(!masterDeck.retrieveCard(card.getName()).isLocked()) {
                    if(card.isChecked()) card.setChecked(false);
                    myStar.setVisibility(card.isChecked() ? View.VISIBLE : View.INVISIBLE);
                }
            }
        }

        void showDeck(final MemeCard card) {
            myStar.setVisibility(View.INVISIBLE);
            myName.setText(card.getName());
        }
    }
}
