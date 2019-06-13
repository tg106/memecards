package com.example.domainobjects;

import java.util.ArrayList;
import java.util.List;

public class Deck {

    private List<MemeCard> deck;

    public Deck(List<MemeCard> deck) {
        this.deck = deck;
    }

    public List<MemeCard> getDeck() {
        return deck;
    }

    public Deck() {
        this.deck = new ArrayList<MemeCard>();
    }

    //Testing function, scuffed deck for AI, will be changed later
    public void generatingTestDeckforAI() {
        deck.add(new MemeCard(
                "AYAYA",
                "Represents all WEEBS in twitch chat AYAYA Clap",
                "card_img_ayaya",
                3000,
                "Anime",
                false
        ));
        deck.add(new MemeCard(
                "Gachi",
                "Manliest man on earth, SPAM GachiBASS for ANIKI",
                "card_img_gachi",
                2000,
                "IRL",
                false
        ));
        deck.add(new MemeCard(
                "Thanos",
                "The most powerfull creature on the universe",
                "card_img_thanos",
                5000,
                "Movies",
                false
        ));
        deck.add(new MemeCard(
                "Tom Cat",
                "God father of cartoon",
                "card_img_tomcat",
                2000,
                "Cartoon",
                false));
        deck.add(new MemeCard(
                "Pepe Frog",
                "PepeHands ...",
                "card_img_pepe",
                1000,
                "IRL",
                false
        ));
    }

    public MemeCard getCardinDeck(int pos) {
        MemeCard temp = deck.get(pos);
        return temp;
    }






}
