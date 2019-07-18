package com.example.memedatabase.dbinterface;

/**
 * An interface to the PlayerStats. The PlayerStats is a database table that stores player info and
 * player progress throughout the game.
 */
public interface PlayerStatsInterface {
    /**
     * Get the amount of in-game currency the player currently has.
     * @return returns an int amount of player cash
     */
    public int getPlayerCash();

    /**
     * Adds more cash to the player's pocket.
     * @param amount amount of cash to add
     * @return returns true if successful, false otherwise
     */
    public boolean addPlayerCash(int amount);

    /**
     * Subtracts cash from the player's pocket. Fails is insufficient cash.
     * @param amount amount of cash to subtract
     * @return returns true if successful, false otherwise
     */
    public void subtractPlayerCash(int amount);
}

