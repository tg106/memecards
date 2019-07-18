package com.example.memedatabase.stubs;

import com.example.domainobjects.MemeCard;
import com.example.memedatabase.dbinterface.BattleDeckInterface;

import java.util.ArrayList;

public class BattleDeckStub implements BattleDeckInterface {
    private static ArrayList<String> mock = new ArrayList<>();
    private MasterDeckStub masterDeck;

    public BattleDeckStub(MasterDeckStub masterDeck) {
        this.masterDeck = masterDeck;
    }

    @Override
    public boolean insertCard(String cardName) {
        boolean result = false;
        if (!mock.contains(cardName) && masterDeck.retrieveCard(cardName) !=
                null && !this.isFull()
        ) {
            mock.add(cardName);
            result = true;
        }
        return result;
    }

    @Override
    public MemeCard retrieveCard(String cardName) {
        MemeCard result = null;
        for (String n : mock) {
            if (n.equals(cardName))
                result = masterDeck.retrieveCard(cardName);
        }
        return result;
    }

    @Override
    public ArrayList<String> retrieveAllCardNames() {
        ArrayList<String> result = new ArrayList<>();
        for (String n : mock) {
            result.add(n);
        }
        return result;
    }

    @Override
    public boolean removeCard(String cardName) {
        return mock.remove(cardName);
    }

    @Override
    public boolean isFull() {
        return mock.size() >= BattleDeckInterface.DECK_SIZE;
    }

    @Override
    public int numCards() {
        return mock.size();
    }

    // helper method to reset stub db for tests
    public void resetStub(){
        BattleDeckStub.mock = new ArrayList<>();
    }
}
