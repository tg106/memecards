package com.example.gamelogic;

import com.example.domainobjects.Deck;
import com.example.domainobjects.MemeCard;

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

    public void generatingAIDeck() {
        if (this.difficulty == 0)
            this.ai_deck.generatingTestDeckforAI();
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
