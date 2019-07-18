package com.example.gamelogic;

import com.example.domainobjects.Deck;
import com.example.domainobjects.Event;
import com.example.domainobjects.EventList;
import com.example.domainobjects.MemeCard;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class UpvotesCalculationTest {
    private final int testEventPriority = 2;

    @Mock
    private EventList currEList;

    @Mock
    private Deck deckForHuman;

    @Mock
    ArrayList<MemeCard> masterDeck;

    @Mock
    private AI_Player ai;

    @InjectMocks
    private GameEngine gameEngine = new GameEngine(deckForHuman, 0, ai);

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
        when(currEList.getEventByPos(any(int.class))).thenReturn(new Event(
                "testName",
                "testDesc",
                "testTag",
                testEventPriority,
                true
        ));
    }
    @Test
    public void CalculationTest() {
        int upvotes = 2000;
        int calculated = gameEngine.calculateNewUpv(upvotes, "testTag");
        int expected = upvotes + (200 * (testEventPriority+1)) * 3;
        assertEquals(expected, calculated);

        upvotes = 1500;
        calculated = gameEngine.calculateNewUpv(upvotes, "testTag");
        expected = upvotes + (200 * (testEventPriority+1)) * 3;
        assertEquals(expected, calculated);

        upvotes = 1000;
        calculated = gameEngine.calculateNewUpv(upvotes, "testTag");
        expected = upvotes + (200 * (testEventPriority+1)) * 3;
        assertEquals(expected, calculated);

        upvotes = 5000;
        calculated = gameEngine.calculateNewUpv(upvotes, "testTag");
        expected = upvotes + (200 * (testEventPriority+1)) * 3;
        assertEquals(expected, calculated);

    }
}
