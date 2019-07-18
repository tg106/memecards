package com.example.gamelogic;

import com.example.domainobjects.Deck;
import com.example.domainobjects.MemeCard;

import java.util.ArrayList;

public class AI_Player {

    private Deck ai_deck;
    private int difficulty;
    private int cardPosition;

    public AI_Player(int difficulty) {
        this.difficulty = difficulty;
        this.ai_deck = new Deck();
        this.cardPosition = 0;
    }

    public Deck getAi_deck() {
        return this.ai_deck;
    }

    public int getDifficulty() {
        return this.difficulty;
    }

    public void generatingAIDeck(ArrayList<MemeCard> masterDeck) {
        ArrayList<MemeCard> newDeck = new ArrayList<MemeCard>();

        for (int i = 0; i < 5; i++)     //5 cards for AI
        {
            newDeck.add(masterDeck.get(masterDeck.size()-i-1));
        }

        ai_deck.setDeck(newDeck);
    }

    public MemeCard makeMoveForAI() {
        MemeCard card_played = ai_deck.getCardinDeck(cardPosition);
        cardPosition++;
        return card_played;
    }

    public int getCardPosition() {
        return cardPosition;
    }
}
