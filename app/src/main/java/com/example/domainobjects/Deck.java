package com.example.domainobjects;

import java.util.ArrayList;
import java.util.List;

public class Deck {

    private List<MemeCard> deck;

    public Deck(List<MemeCard> deck) {
        this.deck = deck;
    }

    public List<MemeCard> getDeck() {
        return deck;
    }

    public Deck() {
        this.deck = new ArrayList<MemeCard>();
    }

    public MemeCard getCardinDeck(int pos) {
        MemeCard temp = deck.get(pos);
        return temp;
    }

    public void setDeck(ArrayList<MemeCard> newdeck) {
        this.deck = newdeck;
    }




}
