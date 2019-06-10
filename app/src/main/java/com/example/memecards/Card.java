package com.example.memecards;

import com.example.domainobjects.MemeCard;

public class Card {
    MemeCard myCard;

    public Card() { }

    public Card(MemeCard myCard) {
        this.myCard = myCard;
    }

    public String getCardName(){
        return myCard.getName();
    }

    public int getCardImage(){
        return myCard.getResId();
    }

    public MemeCard getMyCard() {
        return myCard;
    }

    public void setMyCard(MemeCard myCard) {
        this.myCard = myCard;
    }
}

