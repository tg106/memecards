package com.example.memecards;

import com.example.domainobjects.Deck;
import com.example.domainobjects.Event;
import com.example.domainobjects.EventList;
import com.example.domainobjects.MemeCard;
import com.example.gamelogic.AI_Player;
import com.example.gamelogic.GameEngine;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class GameLogicTest {

    @Test
    public void GameEngineTest() {
        //Generating test deck
        Deck testDeck = new Deck();
        testDeck.generatingTestDeckforAI();

        //Generating GameEngine
        GameEngine gameEngine = new GameEngine(testDeck, 0);

        //Check if the game is initialized
        assertTrue(gameEngine.getScoreForAI() == 0);
        assertTrue(gameEngine.getScoreForHuman() == 0);

        //Check if AI works
        assertTrue(gameEngine.checkAImovable());

        //Check if AI moves
        MemeCard tempCard = gameEngine.moveByAI();
        assertTrue( tempCard != null);

        //Check if score can be increase for AI
        gameEngine.increaseScoreForAI();
        assertTrue(gameEngine.getScoreForAI() == 1);

        //Check if score can be increase for human
        gameEngine.increaseScoreForHuman();
        assertTrue(gameEngine.getScoreForHuman() == 1);

        //Check if generating eventlist work
        gameEngine.generatingEventList();
        assertTrue(gameEngine.getEventList() != null);

        //Check if gameEngine go to next turn and end the game
        assertTrue(!gameEngine.checkIfGameisOver());
        for (int i = 0; i < 5; i++)
            gameEngine.nextTurn();
        assertTrue(gameEngine.checkIfGameisOver());

        //Check if gameEngine can create and cycle through events
        ArrayList<Event> testList = new ArrayList<Event>();
        for (int i = 0; i < 10; i++)
            testList.add(new Event(
                    "test",
                    "test",
                    "test",
                    2,
                    true
            ));
        gameEngine.generatingAllEventList(testList);
        EventList eventListTest = gameEngine.generatingNewEvents();
        assertTrue(eventListTest != null);
        assertTrue(eventListTest.getEventList() != null);
        assertTrue(eventListTest.getEventByPos(0).getPrio() == 2);
        gameEngine.generatingAllEventList(testList);
        eventListTest = gameEngine.generatingNewEvents();
        assertTrue(eventListTest.getEventByPos(0).getPrio() == 2);

        //Check if gameEngine calculate the correct score
        assertTrue(gameEngine.calculateNewUpv(1000, "test") == 2800);


    }

    @Test
    public void AI_PlayerTest() {
        //Generating AI player
        AI_Player aiTest = new AI_Player(0);

        //Check if AI deck is valid
        aiTest.generatingAIDeck();
        assertTrue(aiTest.getAi_deck() != null);

        //Check if AI deck has valid cards
        assertTrue(aiTest.getAi_deck().getCardinDeck(0) != null);
        assertTrue(aiTest.getAi_deck().getCardinDeck(1) != null);
        assertTrue(aiTest.getAi_deck().getCardinDeck(2) != null);
        assertTrue(aiTest.getAi_deck().getCardinDeck(3) != null);
        assertTrue(aiTest.getAi_deck().getCardinDeck(4) != null);

        //Check if AI next move is valid, and the card itself is valid
        assertTrue(aiTest.getCardPosition() == 0);
        MemeCard temp = aiTest.makeMoveForAI();
        assertTrue(aiTest.getCardPosition() == 1);
        assertTrue( temp != null);

    }
}
