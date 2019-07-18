package com.example.memedatabase.sqlite.implementations;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.domainobjects.MemeCard;
import com.example.memedatabase.dbinterface.MasterDeckInterface;
import com.example.memedatabase.sqlite.core.DBContract;
import com.example.memedatabase.sqlite.core.DBHelper;

import java.util.ArrayList;

public class MasterDeck implements MasterDeckInterface {
    private DBHelper dbHelper;
    private Context context;

    public MasterDeck(Context context){
        this.dbHelper = null;
        this.context = context;
    }

    private DBHelper getDBHelper(){
        if (this.dbHelper == null) {
            this.dbHelper = new DBHelper(this.context);
        }
        return this.dbHelper;
    }

    @Override
    public boolean insertCard(String name, String description, String filename, int upvotes, String tag, boolean locked, int price){
        // Gets the data repository in write mode
        SQLiteDatabase db = this.getDBHelper().getWritableDatabase();

        // convert boolean to int
        int lockedInt;
        if (locked)
            lockedInt = 1;
        else
            lockedInt = 0;

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(DBContract.CardsSchema.COLUMN_NAME_NAME, name);
        values.put(DBContract.CardsSchema.COLUMN_NAME_TAG, tag);
        values.put(DBContract.CardsSchema.COLUMN_NAME_DESCRIPTION, description);
        values.put(DBContract.CardsSchema.COLUMN_NAME_FILENAME, filename);
        values.put(DBContract.CardsSchema.COLUMN_NAME_UPVOTES, upvotes);
        values.put(DBContract.CardsSchema.COLUMN_NAME_LOCKED, lockedInt);
        values.put(DBContract.CardsSchema.COLUMN_NAME_PRICE, price);

        // Insert the new row, returning the primary key value of the new row, return -1 if fails
        long newRowId = db.insert(DBContract.CardsSchema.TABLE_NAME, null, values);

        boolean result = true;
        if (newRowId < 0)
            result = false;

        return result;
    }

    @Override
    public MemeCard retrieveCard(String cardName){
        // Gets the data repository in read mode
        SQLiteDatabase db = this.getDBHelper().getReadableDatabase();

        // Define query projection
        String[] projection = {
                DBContract.CardsSchema.COLUMN_NAME_NAME,
                DBContract.CardsSchema.COLUMN_NAME_DESCRIPTION,
                DBContract.CardsSchema.COLUMN_NAME_FILENAME,
                DBContract.CardsSchema.COLUMN_NAME_UPVOTES,
                DBContract.CardsSchema.COLUMN_NAME_TAG,
                DBContract.CardsSchema.COLUMN_NAME_LOCKED,
                DBContract.CardsSchema.COLUMN_NAME_PRICE,
        };

        // Result filter
        String selection = DBContract.CardsSchema.COLUMN_NAME_NAME + " = ?";
        String[] selectionArgs = { cardName };

        // DB query
        Cursor cursor = db.query(
                DBContract.CardsSchema.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,          // don't group the rows
                null,           // don't filter by row groups
                null           // don't sort the rows
        );

        // create a card object
        MemeCard card = null;
        if (cursor.moveToNext()){
            String name = cursor.getString(
                    cursor.getColumnIndexOrThrow(DBContract.CardsSchema.COLUMN_NAME_NAME));
            String description = cursor.getString(
                    cursor.getColumnIndexOrThrow(DBContract.CardsSchema.COLUMN_NAME_DESCRIPTION));
            String filename = cursor.getString(
                    cursor.getColumnIndexOrThrow(DBContract.CardsSchema.COLUMN_NAME_FILENAME));
            int upvotes = cursor.getInt(
                    cursor.getColumnIndexOrThrow(DBContract.CardsSchema.COLUMN_NAME_UPVOTES));
            String tag = cursor.getString(
                    cursor.getColumnIndexOrThrow(DBContract.CardsSchema.COLUMN_NAME_TAG));
            int lockedInt = cursor.getInt(
                    cursor.getColumnIndexOrThrow(DBContract.CardsSchema.COLUMN_NAME_LOCKED));
            int price = cursor.getInt(
                    cursor.getColumnIndexOrThrow(DBContract.CardsSchema.COLUMN_NAME_PRICE));

            boolean locked = lockedInt == 1;

            card = new MemeCard(name, description, filename, upvotes, tag, locked, price);

        }
        cursor.close();

        return card;
    }

    @Override
    public ArrayList<String> retrieveAllCardNames(){
        ArrayList<String> cards = new ArrayList<>();
        String name;

        // Gets the data repository in read mode
        SQLiteDatabase db = this.getDBHelper().getReadableDatabase();

        // make query
        String table = DBContract.CardsSchema.TABLE_NAME;
        String field = DBContract.CardsSchema.COLUMN_NAME_NAME;
        Cursor  cursor = db.rawQuery(
                "select " + field + " from " + table,null);

        // exhaust cursor and build list
        while (cursor.moveToNext()){
            name = cursor.getString(
                    cursor.getColumnIndexOrThrow(DBContract.CardsSchema.COLUMN_NAME_NAME));

            cards.add(name);
        }
        return cards;
    }


    @Override
    public ArrayList<String> retrieveLockedCardNames() {
        ArrayList<String> cards = new ArrayList<>();
        String name;

        // Gets the data repository in read mode
        SQLiteDatabase db = this.getDBHelper().getReadableDatabase();

        // Define query projection
        String[] projection = {
                DBContract.CardsSchema.COLUMN_NAME_NAME,
        };

        // Result filter
        String selection = DBContract.CardsSchema.COLUMN_NAME_LOCKED + " = ?";
        String[] selectionArgs = { "1" };

        // DB query
        Cursor cursor = db.query(
                DBContract.CardsSchema.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,          // don't group the rows
                null,           // don't filter by row groups
                null           // don't sort the rows
        );

        // exhaust cursor and build list
        while (cursor.moveToNext()){
            name = cursor.getString(
                    cursor.getColumnIndexOrThrow(DBContract.CardsSchema.COLUMN_NAME_NAME));

            cards.add(name);
        }
        return cards;
    }

    @Override
    public ArrayList<String> retrieveUnlockedCardNames() {
        ArrayList<String> cards = new ArrayList<>();
        String name;

        // Gets the data repository in read mode
        SQLiteDatabase db = this.getDBHelper().getReadableDatabase();

        // Define query projection
        String[] projection = {
                DBContract.CardsSchema.COLUMN_NAME_NAME,
        };

        // Result filter
        String selection = DBContract.CardsSchema.COLUMN_NAME_LOCKED + " = ?";
        String[] selectionArgs = { "0" };

        // DB query
        Cursor cursor = db.query(
                DBContract.CardsSchema.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,          // don't group the rows
                null,           // don't filter by row groups
                null           // don't sort the rows
        );

        // exhaust cursor and build list
        while (cursor.moveToNext()){
            name = cursor.getString(
                    cursor.getColumnIndexOrThrow(DBContract.CardsSchema.COLUMN_NAME_NAME));

            cards.add(name);
        }
        return cards;
    }

    @Override
    public boolean unlockCard(String cardName){
        // Gets the data repository in write mode
        SQLiteDatabase db = this.getDBHelper().getWritableDatabase();

        // The field to update, i.e chg the 'locked' field to 0
        ContentValues values = new ContentValues();
        values.put(DBContract.CardsSchema.COLUMN_NAME_LOCKED, 0);

        // Which row to update, based on the cardName
        String selection = DBContract.CardsSchema.COLUMN_NAME_NAME + " = ?";
        String[] selectionArgs = { cardName };

        int count = db.update(
                DBContract.CardsSchema.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        boolean result = count > 0;

        return result;
    }

    @Override
    public int deckSize(){
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

}
