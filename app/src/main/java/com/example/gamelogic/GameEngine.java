package com.example.gamelogic;

import com.example.domainobjects.Deck;
import com.example.domainobjects.Event;
import com.example.domainobjects.EventList;
import com.example.domainobjects.MemeCard;

import java.util.ArrayList;

public class GameEngine {

    private Deck deckForHuman;
    private AI_Player ai;
    private EventList eventList;
    private int scoreForHuman;
    private int scoreForAI;
    private int turn;
    private int eventCount;
    private EventList currEList;
    private boolean over;


    public GameEngine(Deck deck, int difficulty, ArrayList<MemeCard> masterDeck) {
        this.deckForHuman = deck;
        this.ai = new AI_Player(difficulty);
        this.eventList = new EventList();
        this.scoreForAI = 0;
        this.scoreForHuman = 0;
        this.turn = 0;
        this.ai.generatingAIDeck(masterDeck);
        this.over = false;
    }

    public GameEngine(Deck deck, int difficulty, AI_Player ai) {
        this.deckForHuman = deck;
        this.ai = ai;
        this.eventList = new EventList();
        this.scoreForAI = 0;
        this.scoreForHuman = 0;
        this.turn = 0;
        this.over = false;
    }

    public MemeCard moveByAI() {
        return this.ai.makeMoveForAI();
    }

    public void generatingAllEventList(ArrayList<Event> db) {
        this.eventList.setEventList(db);
    }

    public EventList generatingNewEvents() {

        ArrayList<Event> list = new ArrayList<Event>();

        for (int i = 0; i < 3; i++) {                   //These are 3 events that will be displayed in the game play
            if (this.eventCount < eventList.getEventListLength()) {
                list.add(this.eventList.getEventByPos(this.eventCount));
                this.eventCount++;
            } else {
                this.eventCount = 0;
                list.add(this.eventList.getEventByPos(this.eventCount));
                this.eventCount++;
            }
        }

        EventList newEvl = new EventList(list);
        currEList = newEvl;

        return newEvl;
    }

    public int calculateNewUpv(int upv, String tag) {
        int newUpvote = upv;

        for (int i = 0; i < 3; i++) {       //Calculate new upvotes base on 3 current events
            Event temp = currEList.getEventByPos(i);

            if (tag.equals(temp.getTag()) && temp.checkEventPositive())    //Calculation mechanic
                newUpvote += 200*(temp.getPrio()+1);
            if (tag.equals(temp.getTag()) && !temp.checkEventPositive())
                newUpvote -= 200*(temp.getPrio()+1);
        }

        return newUpvote;
    }

    public void increaseScoreForHuman() {
        this.scoreForHuman++;
    }

    public void increaseScoreForAI() {
        this.scoreForAI++;
    }

    public void nextTurn() {
        this.turn++;

        if (this.turn < 5) {
            if (scoreForHuman > 2 || scoreForAI > 2)   // BO5 game
                this.over = true;
        } else
            this.over = true;
    }

    public boolean checkIfGameisOver() {
        return this.over;
    }

    public void gameEnd() {
        this.over = true;
    }

    public EventList getEventList() {
        return this.eventList;
    }

    public boolean checkAImovable() {
        if (this.ai.getCardPosition() < 5)  //5 cards in a deck
            return true;
        else
            return false;
    }

    public int getScoreForAI() {
        return this.scoreForAI;
    }

    public int getScoreForHuman() {
        return this.scoreForHuman;
    }

    public int getTurn() {
        return turn;
    }
}
