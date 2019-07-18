package com.example.memedatabase.stubs;

import com.example.domainobjects.MemeCard;
import com.example.memedatabase.dbinterface.MasterDeckInterface;

import java.util.ArrayList;

public class MasterDeckStub implements MasterDeckInterface {
    private static ArrayList<MemeCard> mock = new ArrayList<>();

    @Override
    public boolean insertCard(
            String name,
            String description,
            String filename,
            int upvotes,
            String tag,
            boolean locked,
            int price
    ) {
        if (this.retrieveCard(name) == null)
            return mock.add(new MemeCard(name, description, filename, upvotes, tag, locked, price));
        else
            return false;
    }

    @Override
    public MemeCard retrieveCard(String cardName) {
        MemeCard result = null;
        for (MemeCard card : mock) {
            if (card.getName().equals(cardName))
                result = card;
        }
        return result;
    }

    @Override
    public ArrayList<String> retrieveAllCardNames() {
        ArrayList<String> result = new ArrayList<>();
        for (MemeCard card : mock)
            result.add(card.getName());
        return result;
    }

    @Override
    public ArrayList<String> retrieveLockedCardNames() {
        ArrayList<String> result = new ArrayList<>();
        for (MemeCard card : mock)
            if (card.isLocked())
                result.add(card.getName());
        return result;
    }

    @Override
    public ArrayList<String> retrieveUnlockedCardNames() {
        ArrayList<String> result = new ArrayList<>();
        for (MemeCard card : mock)
            if (!card.isLocked())
                result.add(card.getName());
        return result;
    }

    @Override
    public boolean unlockCard(String cardName) {
        MemeCard card = null;
        boolean result = true;
        for (MemeCard c : mock) {
            if (c.getName().equals(cardName))
                card = c;
        }
        if (card != null && card.isLocked())
            card.setLocked(false);
        else
            result = false;
        return result;
    }

    @Override
    public int deckSize() {
        return mock.size();
    }

    // helper method to reset stub db for tests
    public void resetStub(){
        MasterDeckStub.mock = new ArrayList<>();
    }
}
