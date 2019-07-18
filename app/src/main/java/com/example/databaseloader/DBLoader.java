package com.example.databaseloader;

import android.content.Context;

import com.example.memedatabase.sqlite.implementations.BattleDeck;
import com.example.memedatabase.dbinterface.BattleDeckInterface;
import com.example.memedatabase.sqlite.implementations.EventList;
import com.example.memedatabase.dbinterface.EventListInterface;
import com.example.memedatabase.sqlite.implementations.MasterDeck;
import com.example.memedatabase.dbinterface.MasterDeckInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.InputStream;
import java.util.ArrayList;


public class DBLoader {
    // loads the "cards.json" file into a java string.
    private static String loadJSONFromAsset(Context context, String filename) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    // Takes a json encoded string of card objects and adds it to the master deck db.
    // Ony inserts a card if it doesn't exist in the db.
    private static void loadMasterDeck(MasterDeckInterface db, String jsonString){
        try {
            // load json encoded string
            JSONObject json_obj = new JSONObject(jsonString);
            JSONArray json_cards = json_obj.getJSONArray("cards");
            String name, desc, fileName, tag;
            boolean locked;
            int upvotes, price;
            JSONObject json_card;
            ArrayList<String> cardNames = db.retrieveAllCardNames();

            for (int i = 0; i < json_cards.length(); i++) {
                // extract json data
                json_card = json_cards.getJSONObject(i);
                name = json_card.getString("name");
                // only continue processing if card does not exist in db
                if (!cardNames.contains(name)) {
                    desc = json_card.getString("description");
                    upvotes = json_card.getInt("upvotes");
                    fileName = json_card.getString("fileName");
                    tag = json_card.getString("tag");
                    locked = json_card.getBoolean("locked");
                    price = json_card.getInt("price");

                    // insert into db
                    db.insertCard(name, desc, fileName, upvotes, tag, locked, price);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // convenience method
    public static void loadMasterDeck(MasterDeckInterface db, Context context) {
        String json = DBLoader.loadJSONFromAsset(context, "cards.json");
        DBLoader.loadMasterDeck(db, json);
    }

    // Takes a json encoded string of card objects and adds it to the eventslist db.
    // Ony inserts an event if it doesn't exist in the db.
    private static void loadEventsList(EventListInterface db, String jsonString){
        try {
            // load json encoded string
            JSONObject json_obj = new JSONObject(jsonString);
            JSONArray json_events = json_obj.getJSONArray("events");
            String name, desc, tag;
            boolean positive;
            JSONObject json_event;
            ArrayList<String> events = db.retrieveAllEventNames();

            for (int i = 0; i < json_events.length(); i++) {
                // extract json data
                json_event = json_events.getJSONObject(i);
                name = json_event.getString("name");
                // only continue if events doesn't exist
                if (!events.contains(name)) {
                    desc = json_event.getString("description");
                    positive = json_event.getBoolean("positive");
                    tag = json_event.getString("tag");

                    // insert into db
                    db.insertEvent(name, desc, tag, positive);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // convenience method
    public static void loadEventsList(EventListInterface db, Context context) {
        String json = DBLoader.loadJSONFromAsset(context, "events.json");
        DBLoader.loadEventsList(db, json);
    }

    public static void loadBattleDeck(BattleDeckInterface db, Context context){
        String jsonString = DBLoader.loadJSONFromAsset(context, "cards.json");
        // only loads battle deck for the first time
        if (!db.isFull()){
            try {
                // load json encoded string
                JSONObject json_obj = new JSONObject(jsonString);
                JSONArray json_cards = json_obj.getJSONArray("cards");
                String name, desc, fileName, tag;
                boolean locked;
                int upvotes, price;
                JSONObject json_card;

                for (int i = 0; i < json_cards.length(); i++) {
                    // extract json data
                    json_card = json_cards.getJSONObject(i);
                    name = json_card.getString("name");
                    locked = json_card.getBoolean("locked");
                    // only continue processing if card does not exist in db
                    if (!locked) {
                        // insert into db
                        db.insertCard(name);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static void loadDB(Context context){
        // load master deck test
        DBLoader.loadMasterDeck(new MasterDeck(context), context);

        // load events list test
        DBLoader.loadEventsList(new EventList(context), context);

        //load battle deck
        DBLoader.loadBattleDeck(new BattleDeck(context), context);
    }
}
