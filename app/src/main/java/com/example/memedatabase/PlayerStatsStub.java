package com.example.memedatabase;

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
    public boolean subtractPlayerCash(int amount) {
        if (PlayerStatsStub.cash >= amount)
            PlayerStatsStub.cash -= amount;
        else
            return false;
        return true;
    }

    // helper method to reset stub db for tests
    public void resetStub(){
        PlayerStatsStub.cash = 0;
    }
}
