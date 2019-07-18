package com.example.memecards;

import android.content.Context;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import com.example.domainobjects.Event;
import com.example.domainobjects.MemeCard;
import com.example.memedatabase.sqlite.implementations.BattleDeck;
import com.example.memedatabase.sqlite.core.DBHelper;
import com.example.memedatabase.sqlite.implementations.EventList;
import com.example.memedatabase.dbinterface.InsufficientCashException;
import com.example.memedatabase.sqlite.implementations.MasterDeck;
import com.example.memedatabase.sqlite.implementations.PlayerStats;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class DatabaseTest {
    @Test
    public void test_master_deck() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        DBHelper dbHelper = new DBHelper(appContext);
        dbHelper.resetDB();

        // instantiated db, starts out empty
        MasterDeck deck = new MasterDeck(appContext);

        // test vals
        ArrayList<String> testArray;
        MemeCard testCard;
        boolean testBool;
        int testInt;

        // tests on empty DB
        testCard = deck.retrieveCard("invalidCard");
        assertNull(testCard);
        testArray = deck.retrieveAllCardNames();
        assertEquals(testArray.size(), 0);
        testBool = deck.unlockCard("invalidCard");
        assertFalse(testBool);
        testInt = deck.deckSize();
        assertEquals(testInt, 0);

        // test insert card
        testBool = deck.insertCard("sampleA", "blah", "path", 99, "tag", true, 0
        );
        assertTrue(testBool);
        testBool = deck.insertCard("sampleB", "blah", "path", 99, "tag", true, 0
        );
        assertTrue(testBool);

        // test retrieve card
        testCard = deck.retrieveCard("sampleA");
        assertNotNull(testCard);
        assertEquals(testCard.getName(), "sampleA");
        testCard = deck.retrieveCard("sampleB");
        assertNotNull(testCard);
        assertEquals(testCard.getName(), "sampleB");
        testCard = deck.retrieveCard("sampleK");
        assertNull(testCard);

        // test retrieve all card names
        testArray = deck.retrieveAllCardNames();
        assertEquals(testArray.size(), 2);

        // test get deck size
        testInt = deck.deckSize();
        assertEquals(testInt, 2);

        // test unlock card
        testBool = deck.unlockCard("sampleB");
        assertTrue(testBool);
        testBool = deck.unlockCard("sampleA");
        assertTrue(testBool);

        // clear db after test
        dbHelper.resetDB();
    }

    @Test
    public void test_battle_deck() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        DBHelper dbHelper = new DBHelper(appContext);
        dbHelper.resetDB();

        // battle deck requires a master deck, so instantiate a master deck first
        MasterDeck master = new MasterDeck(appContext);

        master.insertCard(
                "sampleA", "blah", "path", 99, "tag", true, 0
        );
        master.insertCard(
                "sampleB", "blah", "path", 99, "tag", false, 0
        );
        master.insertCard(
                "sampleC", "blah", "path", 99, "tag", false, 0
        );
        master.insertCard(
                "sampleD", "blah", "path", 99, "tag", false, 0
        );
        master.insertCard(
                "sampleE", "blah", "path", 99, "tag", false, 0
        );
        master.insertCard(
                "sampleF", "blah", "path", 99, "tag", false, 0
        );

        // instantiate battle deck stub db
        BattleDeck deck = new BattleDeck(appContext);

        // test vals
        ArrayList<String> testArray;
        MemeCard testCard;
        boolean testBool;
        int testInt;

        // tests on empty DB
        testCard = deck.retrieveCard("invalidCard");
        assertNull(testCard);
        testArray = deck.retrieveAllCardNames();
        assertEquals(testArray.size(), 0);
        testBool = deck.removeCard("invalidCard");
        assertFalse(testBool);
        testInt = deck.numCards();
        assertEquals(testInt, 0);
        testBool = deck.isFull();
        assertFalse(testBool);

        // test insert card
        testBool = deck.insertCard("sampleA");
        assertFalse(testBool); // should fail as card exists in master deck but is locked
        testBool = deck.insertCard("sampleB");
        assertTrue(testBool); // should succeed as card exists in master deck and is unlocked
        testBool = deck.insertCard("sampleZ");
        assertFalse(testBool); // should fail as card does not exist in master deck

        // test retrieve card
        testCard = deck.retrieveCard("sampleB");
        assertNotNull(testCard);
        assertEquals(testCard.getName(), "sampleB");
        testCard = deck.retrieveCard("sampleK");
        assertNull(testCard);

        // test retrieve all card names
        deck.insertCard("sampleC");
        testArray = deck.retrieveAllCardNames();
        assertEquals(testArray.size(), 2);

        // test get numCards() and isFull()
        deck.insertCard("sampleF");
        testInt = deck.numCards();
        assertEquals(testInt, 3);
        testBool = deck.isFull();
        assertFalse(testBool);
        deck.insertCard("sampleD");
        deck.insertCard("sampleE");
        testInt = deck.numCards();
        assertEquals(testInt, 5);
        testBool = deck.isFull();
        assertTrue(testBool);

        // test insert when deck is full
        testBool = deck.insertCard("sampleF");
        assertFalse(testBool);

        // test remove
        testBool = deck.isFull();
        assertTrue(testBool);
        testBool = deck.removeCard("sampleE");
        assertTrue(testBool);
        testBool = deck.isFull();
        assertFalse(testBool);

        // clear db after testing
        dbHelper.resetDB();
    }

    @Test
    public void test_player_stats(){
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        DBHelper dbHelper = new DBHelper(appContext);
        dbHelper.resetDB();

        PlayerStats stats = new PlayerStats(appContext);

        // test vals
        boolean testBool = false;
        int testInt;

        testInt = stats.getPlayerCash();
        assertEquals(testInt, 200);

        try {
            stats.subtractPlayerCash(201);
        } catch (InsufficientCashException e) {
            testBool = true;
        }
        assertTrue(testBool);

        stats.addPlayerCash(10);
        testInt = stats.getPlayerCash();
        assertEquals(testInt, 210);

        stats.addPlayerCash(15);
        stats.subtractPlayerCash(3);
        try {
            stats.subtractPlayerCash(299);
        } catch (InsufficientCashException e) {
            testBool = true;
        }
        assertTrue(testBool);

        testInt = stats.getPlayerCash();
        assertEquals(testInt, 210 + 15 - 3);

        // clear db after tests
        dbHelper.resetDB();

    }

    @Test
    public void test_event_list() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        DBHelper dbHelper = new DBHelper(appContext);
        dbHelper.resetDB();

        // instantiate DB
        EventList list = new EventList(appContext);

        // test vals
        ArrayList<String> testArray;
        Event testEvent;
        boolean testBool;

        // tests on empty DB
        testEvent = list.retrieveEvent("invalidEvent");
        assertNull(testEvent);
        testArray = list.retrieveAllEventNames();
        assertEquals(testArray.size(), 0);

        // test insert event
        testBool = list.insertEvent("eventA", "descA", "tagA", true);
        assertTrue(testBool);
        testBool = list.insertEvent("eventB", "descB", "tagB", false);
        assertTrue(testBool);
        testBool = list.insertEvent("eventB", "descB", "tagB", false);
        assertFalse(testBool); // should be false as event already exist

        // test retrieve event
        testEvent = list.retrieveEvent("eventA");
        assertNotNull(testEvent);
        assertEquals(testEvent.getName(), "eventA");

        // test retrieve all card names
        list.insertEvent("eventC", "descC", "tagC", false);
        testArray = list.retrieveAllEventNames();
        assertEquals(testArray.size(), 3);

        // clear db after tests
        dbHelper.resetDB();

    }

}
