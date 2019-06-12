package com.example.gamelogic;

import com.example.domainobjects.Deck;
import com.example.domainobjects.MemeCard;

public class AI_Player {

    private Deck ai_deck;
    private int difficulty;
    private int cardPosition;

    public AI_Player(int difficulty) {
        this.difficulty = difficulty;
        ai_deck = new Deck();
        cardPosition = 0;
    }

    public Deck getAi_deck() {
        return ai_deck;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void generatingAIDeck() {
        if (difficulty == 0)
        {
            ai_deck.generatingTestDeckforAI();

        }
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
