package com.example.memedatabase.sqlite.implementations;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.domainobjects.MemeCard;
import com.example.memedatabase.dbinterface.BattleDeckInterface;
import com.example.memedatabase.sqlite.core.DBContract;
import com.example.memedatabase.sqlite.core.DBHelper;

import java.util.ArrayList;

public class BattleDeck implements BattleDeckInterface {
    private DBHelper dbHelper;
    private Context context;
    private MasterDeck masterDeck;

    public BattleDeck(Context context){
        this.dbHelper = null;
        this.context = context;
        this.masterDeck = new MasterDeck(context);
    }

    private DBHelper getDBHelper(){
        if (this.dbHelper == null) {
            this.dbHelper = new DBHelper(this.context);
        }
        return this.dbHelper;
    }

    // This returns a boolean instead of throwing an Exception because:
    // It is not an error when inserting an already existing card into the deck, we do this a lot
    // and we want the deck to work like a set object and simply return a boolean value.
    @Override
    public boolean insertCard(String cardName){
        // first check if the card exists in the master deck
        MemeCard card = this.masterDeck.retrieveCard(cardName);
        ArrayList<String> currentDeck = this.retrieveAllCardNames();
        int size = currentDeck.size();
        int maxSize = BattleDeckInterface.DECK_SIZE;
        long newRowId = -1;
        if (size < maxSize && card != null && !card.isLocked() && !currentDeck.contains(cardName)){
            // Gets the data repository in write mode
            SQLiteDatabase db = this.getDBHelper().getWritableDatabase();

            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            values.put(DBContract.BattleDeckSchema.COLUMN_NAME_NAME, cardName);

            // Insert the new row, returning the primary key value of the new row, return -1 if fails
            newRowId = db.insert(DBContract.BattleDeckSchema.TABLE_NAME, null, values);
        }

        boolean result = true;
        if (card == null || newRowId < 0){
            result = false;
        }
        return result;
    }

    @Override
    public MemeCard retrieveCard(String cardName){
        // Gets the data repository in read mode
        SQLiteDatabase db = this.getDBHelper().getReadableDatabase();

        // Define query projection
        String[] projection = {
            DBContract.BattleDeckSchema.COLUMN_NAME_NAME,
        };

        // Result filter
        String selection = DBContract.BattleDeckSchema.COLUMN_NAME_NAME + " = ?";
        String[] selectionArgs = { cardName };

        // DB query
        Cursor cursor = db.query(
            DBContract.BattleDeckSchema.TABLE_NAME,   // The table to query
            projection,             // The array of columns to return (pass null to get all)
            selection,              // The columns for the WHERE clause
            selectionArgs,          // The values for the WHERE clause
            null,          // don't group the rows
            null,           // don't filter by row groups
            null           // don't sort the rows
        );

        // query battle deck
        String name = null;
        if (cursor.moveToNext()){
            name = cursor.getString(
                    cursor.getColumnIndexOrThrow(DBContract.BattleDeckSchema.COLUMN_NAME_NAME));
        }
        cursor.close();

        // query master deck and create a card object
        MemeCard card = null;
        if (name != null)
            card = this.masterDeck.retrieveCard(name);

        return card;
    }

    @Override
    public ArrayList<String> retrieveAllCardNames(){
        ArrayList<String> cards = new ArrayList<>();
        String name;

        // Gets the data repository in read mode
        SQLiteDatabase db = this.getDBHelper().getReadableDatabase();

        // make query
        String table = DBContract.BattleDeckSchema.TABLE_NAME;
        String field = DBContract.BattleDeckSchema.COLUMN_NAME_NAME;
        Cursor  cursor = db.rawQuery(
                "select " + field + " from " + table,null);

        // exhaust cursor and build list
        while (cursor.moveToNext()){
            name = cursor.getString(
                    cursor.getColumnIndexOrThrow(DBContract.BattleDeckSchema.COLUMN_NAME_NAME));

            cards.add(name);
        }
        return cards;
    }

    @Override
    public boolean removeCard(String cardName){
        // get writable db
        SQLiteDatabase db = this.getDBHelper().getWritableDatabase();
        // Define 'where' part of query.
        String selection = DBContract.BattleDeckSchema.COLUMN_NAME_NAME + " LIKE ?";
        String[] selectionArgs = { cardName };
        // Issue SQL statement.
        int deletedRows = db.delete(DBContract.BattleDeckSchema.TABLE_NAME, selection, selectionArgs);
        return (deletedRows > 0);
    }

    @Override
    public boolean isFull(){
        return this.numCards() >= this.DECK_SIZE;
    }

    @Override
    public int numCards(){
        ArrayList<String> cards = this.retrieveAllCardNames();
        return cards.size();
    }

    public ArrayList<MemeCard> retrieveAllCards(){
        ArrayList<MemeCard> cards = new ArrayList<>();
        MemeCard card = null;
        ArrayList<String> cardNames = this.retrieveAllCardNames();
        for (String name : cardNames){
            card = this.retrieveCard(name);
            if (card != null)
                cards.add(card);
        }
        return cards;
    }

    public void clearDeck(){
        ArrayList<String> cards = this.retrieveAllCardNames();
        for (String cardName : cards)
            this.removeCard(cardName);
    }
}
