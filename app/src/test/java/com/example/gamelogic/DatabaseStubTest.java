package com.example.gamelogic;

import com.example.domainobjects.Event;
import com.example.domainobjects.MemeCard;
import com.example.memedatabase.stubs.BattleDeckStub;
import com.example.memedatabase.stubs.EventListStub;
import com.example.memedatabase.dbinterface.InsufficientCashException;
import com.example.memedatabase.stubs.MasterDeckStub;
import com.example.memedatabase.stubs.PlayerStatsStub;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class DatabaseStubTest {
    @Test
    public void test_master_deck() {
        // instantiated stub db, starts out empty
        MasterDeckStub stub = new MasterDeckStub();
        stub.resetStub();

        // test vals
        ArrayList<String> testArray;
        MemeCard testCard;
        boolean testBool;
        int testInt;

        // tests on empty DB
        testCard = stub.retrieveCard("invalidCard");
        assertNull(testCard);
        testArray = stub.retrieveAllCardNames();
        assertEquals(testArray.size(), 0);
        testBool = stub.unlockCard("invalidCard");
        assertFalse(testBool);
        testInt = stub.deckSize();
        assertEquals(testInt, 0);

        // test insert card
        testBool = stub.insertCard("sampleA", "blah", "path", 99, "tag", true, 0
        );
        assertTrue(testBool);
        testBool = stub.insertCard("sampleB", "blah", "path", 99, "tag", false, 0
        );
        assertTrue(testBool);

        // test retrieve card
        testCard = stub.retrieveCard("sampleA");
        assertNotNull(testCard);
        assertEquals(testCard.getName(), "sampleA");
        testCard = stub.retrieveCard("sampleB");
        assertNotNull(testCard);
        assertEquals(testCard.getName(), "sampleB");
        testCard = stub.retrieveCard("sampleK");
        assertNull(testCard);

        // test retrieve all card names
        testArray = stub.retrieveAllCardNames();
        assertEquals(testArray.size(), 2);

        // test get deck size
        testInt = stub.deckSize();
        assertEquals(testInt, 2);

        // test unlock card
        testBool = stub.unlockCard("sampleB");
        assertFalse(testBool);
        testBool = stub.unlockCard("sampleA");
        assertTrue(testBool);
    }

    @Test
    public void test_battle_deck() {
        // battle deck requires a master deck, so instantiate a master deck first
        MasterDeckStub master = new MasterDeckStub();
        master.resetStub();
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
        BattleDeckStub stub = new BattleDeckStub(master);
        stub.resetStub();

        // test vals
        ArrayList<String> testArray;
        MemeCard testCard;
        boolean testBool;
        int testInt;

        // tests on empty DB
        testCard = stub.retrieveCard("invalidCard");
        assertNull(testCard);
        testArray = stub.retrieveAllCardNames();
        assertEquals(testArray.size(), 0);
        testBool = stub.removeCard("invalidCard");
        assertFalse(testBool);
        testInt = stub.numCards();
        assertEquals(testInt, 0);
        testBool = stub.isFull();
        assertFalse(testBool);

        // test insert card
        testBool = stub.insertCard("sampleA");
        assertTrue(testBool); // should succeed as card exists in master deck
        testBool = stub.insertCard("sampleZ");
        assertFalse(testBool); // should fail as card does not exist in master deck

        // test retrieve card
        stub.insertCard("sampleB");
        testCard = stub.retrieveCard("sampleB");
        assertNotNull(testCard);
        assertEquals(testCard.getName(), "sampleB");
        testCard = stub.retrieveCard("sampleK");
        assertNull(testCard);

        // test retrieve all card names
        stub.insertCard("sampleC");
        testArray = stub.retrieveAllCardNames();
        assertEquals(testArray.size(), 3);

        // test get numCards() and isFull()
        testInt = stub.numCards();
        assertEquals(testInt, 3);
        testBool = stub.isFull();
        assertFalse(testBool);
        stub.insertCard("sampleD");
        stub.insertCard("sampleE");
        testInt = stub.numCards();
        assertEquals(testInt, 5);
        testBool = stub.isFull();
        assertTrue(testBool);

        // test insert when deck is full
        testBool = stub.insertCard("sampleF");
        assertFalse(testBool);

        // test remove
        testBool = stub.isFull();
        assertTrue(testBool);
        testBool = stub.removeCard("sampleE");
        assertTrue(testBool);
        testBool = stub.isFull();
        assertFalse(testBool);
    }

    @Test
    public void test_player_stats(){
        PlayerStatsStub stub = new PlayerStatsStub();
        stub.resetStub();

        // test vals
        boolean testBool = false;
        int testInt;

        testInt = stub.getPlayerCash();
        assertEquals(testInt, 0);

        try {
            stub.subtractPlayerCash(1);
        } catch (InsufficientCashException e) {
            testBool = true;
        }
        assertTrue(testBool);

        stub.addPlayerCash(10);
        testInt = stub.getPlayerCash();
        assertEquals(testInt, 10);

        stub.addPlayerCash(15);
        stub.subtractPlayerCash(3);
        try {
            stub.subtractPlayerCash(99);
        } catch (InsufficientCashException e) {
            testBool = true;
        }
        assertTrue(testBool);

        testInt = stub.getPlayerCash();
        assertEquals(testInt, 10 + 15 - 3);

    }

    @Test
    public void test_event_list() {
        // instantiate DB
        EventListStub stub = new EventListStub();
        stub.resetStub();

        // test vals
        ArrayList<String> testArray;
        Event testEvent;
        boolean testBool;

        // tests on empty DB
        testEvent = stub.retrieveEvent("invalidEvent");
        assertNull(testEvent);
        testArray = stub.retrieveAllEventNames();
        assertEquals(testArray.size(), 0);

        // test insert event
        testBool = stub.insertEvent("eventA", "descA", "tagA", true);
        assertTrue(testBool);
        testBool = stub.insertEvent("eventB", "descB", "tagB", false);
        assertTrue(testBool);
        testBool = stub.insertEvent("eventB", "descB", "tagB", false);
        assertFalse(testBool); // should be false as event already exist

        // test retrieve event
        testEvent = stub.retrieveEvent("eventA");
        assertNotNull(testEvent);
        assertEquals(testEvent.getName(), "eventA");

        // test retrieve all card names
        stub.insertEvent("eventC", "descC", "tagC", false);
        testArray = stub.retrieveAllEventNames();
        assertEquals(testArray.size(), 3);

    }

}
