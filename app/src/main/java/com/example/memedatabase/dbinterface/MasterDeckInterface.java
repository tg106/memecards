package com.example.memedatabase.dbinterface;

import com.example.domainobjects.MemeCard;

import java.util.ArrayList;

/**
 * An interface to the MasterDeck. The Master Deck is a database table that keeps track of
 * every card in the game. Cards are unique and each card has a 'locked' attribute associated to it.
 * Players canunlock more and more cards over time.
 */
public interface MasterDeckInterface {
    /**
     * Inserts a new card to the MasterDeck table. Fails if card already exists in the table.
     * @param name : name of the card
     * @param description : description of the card
     * @param filename : resource path to the card's image
     * @param upvotes : number of upvotes this card has
     * @param tag : an attribute or category associated with the card
     * @param locked : indicator if the card should be locked or unlocked
     * @return returns true if insertion is successful, false otherwise.
     */
    public boolean insertCard(
            String name,
            String description,
            String filename,
            int upvotes,
            String tag,
            boolean locked,
            int price
    );

    /**
     * Retrieves a card from the MasterDeck table given the name of the card.
     * @param cardName : name of the card.
     * @return return a Card object if card exists, returns null otherwise.
     */
    public MemeCard retrieveCard(String cardName);

    /**
     * Retrieves a list of card names of all cards in the MasterDeck table.
     * @return returns a list of card names as Strings.
     */
    public ArrayList<String> retrieveAllCardNames();

    /**
     * Retrieves a list of card names of all locked cards in the MasterDeck table.
     * @return returns a list of locked card names as Strings.
     */
    public ArrayList<String> retrieveLockedCardNames();

    /**
     * Retrieves a list of card names of all unlocked cards in the MasterDeck table.
     * @return returns a list of unlocked card names as Strings.
     */
    public ArrayList<String> retrieveUnlockedCardNames();

    /**
     * Unlocks an existing card in the master deck,
     * i.e sets the 'locked' column in the card's row to 0 (false).
     * @param cardName : name of the card
     * @return returns true if update is successful, false if card does not exist or if
     * card is already unlocked.
     */
    public boolean unlockCard(String cardName);

    /**
     * Returns the size of the deck, i.e. total number of cards.
     * @return Returns the size of the deck, i.e. total number of cards.
     */
    public int deckSize();
}
