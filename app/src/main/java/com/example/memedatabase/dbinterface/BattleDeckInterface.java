package com.example.memedatabase.dbinterface;

import com.example.domainobjects.MemeCard;

import java.util.ArrayList;

/**
 * An interface to the BattleDeck. The BattleDeck is a database table that keeps track of
 * current set of card in the player's BattleDeck. Cards in the BattleDeck are unique.
 */
public interface BattleDeckInterface {
    final int DECK_SIZE = 5;
    /**
     * Inserts a new card to the BattleDeck table. Fails if the BattleDeck is already full, or, if
     * the card already exists in the BattleDeck, or, if the card is locked / doesn't exist in the
     * MasterDeck.
     * @param cardName : name of the card (card must exist in the MasterDeck)
     * @return returns true if insertion is successful, false otherwise.
     */
    public boolean insertCard(String cardName);

    /**
     * Retrieves a card from the BattleDeck table given the name of the card.
     * @param cardName : name of the card.
     * @return return a MemeCard object if card exists, returns null otherwise.
     */
    public MemeCard retrieveCard(String cardName);

    /**
     * Retrieves a list of card names of all cards in the MasterDeck table.
     * @return returns a list of card names as Strings.
     */
    public ArrayList<String> retrieveAllCardNames();

    /**
     * Removes a card from the BattleDeck.
     * @param cardName : name of the card
     * @return return true on success, false otherwise
     */
    public boolean removeCard(String cardName);

    /**
     * Checks if the deck is full.
     * @return returns true if the deck is full, false otherwise
     */
    public boolean isFull();

    /**
     * Returns the number of cards in the deck.
     * @return Returns the number of cards in the deck.
     */
    public int numCards();
}
