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


    public GameEngine(Deck deck, int difficulty) {
        this.deckForHuman = deck;
        this.ai = new AI_Player(difficulty);
        this.eventList = new EventList();
        this.scoreForAI = 0;
        this.scoreForHuman = 0;
        this.turn = 0;
        this.ai.generatingAIDeck();
    }

    public MemeCard moveByAI() {
        return this.ai.makeMoveForAI();
    }

    public void generatingEventList() {
        this.eventList.generatingTestEventList();
    }

    public void generatingAllEventList(ArrayList<Event> db) {
        this.eventList.setEventList(db);
    }

    public EventList generatingNewEvents() {

        ArrayList<Event> list = new ArrayList<Event>();

        for (int i = 0; i < 3; i++) {
            if (this.eventCount < 10) {
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

        for (int i = 0; i < 3; i++) {
            Event temp = currEList.getEventByPos(i);

            if (tag.equals(temp.getTag()) && temp.checkEventPositive())
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
    }

    public boolean checkIfGameisOver() {
        Boolean check = false;

        if (this.scoreForAI > 2)
            check = true;
        if (this.scoreForHuman > 2)
            check = true;
        if (this.turn >= 5)
            check = true;

        return check;
    }

    public EventList getEventList() {
        return this.eventList;
    }

    public boolean checkAImovable() {
        if (this.ai.getCardPosition() < 5)
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
