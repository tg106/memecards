package com.example.memedatabase.stubs;

import com.example.memedatabase.dbinterface.InsufficientCashException;
import com.example.memedatabase.dbinterface.PlayerStatsInterface;

public class PlayerStatsStub implements PlayerStatsInterface {
    private static int cash = 0;
    @Override
    public int getPlayerCash() {
        return PlayerStatsStub.cash;
    }

    @Override
    public boolean addPlayerCash(int amount) {
        PlayerStatsStub.cash += amount;
        return true;
    }

    @Override
    public void subtractPlayerCash(int amount){
        if (PlayerStatsStub.cash >= amount)
            PlayerStatsStub.cash -= amount;
        else
            throw new InsufficientCashException("Insufficient cash : Trying to substract " + amount + " from " + PlayerStatsStub.cash);
    }

    // helper method to reset stub db for tests
    public void resetStub(){
        PlayerStatsStub.cash = 0;
    }
}

