package com.example.memedatabase.sqlite.implementations;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.domainobjects.Event;
import com.example.memedatabase.dbinterface.EventListInterface;
import com.example.memedatabase.sqlite.core.DBContract;
import com.example.memedatabase.sqlite.core.DBHelper;

import java.util.ArrayList;

public class EventList implements EventListInterface {
    private DBHelper dbHelper;
    private Context context;

    public EventList(Context context){
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
    public boolean insertEvent(String name, String desc, String tag, boolean positiveEvent) {
        // Gets the data repository in write mode
        SQLiteDatabase db = this.getDBHelper().getWritableDatabase();

        // convert boolean to int
        int positiveInt;
        if (positiveEvent)
            positiveInt = 1;
        else
            positiveInt = 0;

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(DBContract.EventsSchema.COLUMN_NAME_NAME, name);
        values.put(DBContract.EventsSchema.COLUMN_NAME_TAG, tag);
        values.put(DBContract.EventsSchema.COLUMN_NAME_DESCRIPTION, desc);
        values.put(DBContract.EventsSchema.COLUMN_NAME_POSITIVE, positiveInt);

        // Insert the new row, returning the primary key value of the new row, return -1 if fails
        long newRowId = db.insert(DBContract.EventsSchema.TABLE_NAME, null, values);

        boolean result = true;
        if (newRowId < 0)
            result = false;

        return result;
    }

    @Override
    public Event retrieveEvent(String name) {
        // Gets the data repository in read mode
        SQLiteDatabase db = this.getDBHelper().getReadableDatabase();

        // Define query projection
        String[] projection = {
                DBContract.EventsSchema.COLUMN_NAME_NAME,
                DBContract.EventsSchema.COLUMN_NAME_DESCRIPTION,
                DBContract.EventsSchema.COLUMN_NAME_TAG,
                DBContract.EventsSchema.COLUMN_NAME_POSITIVE,

        };

        // Result filter
        String selection = DBContract.EventsSchema.COLUMN_NAME_NAME + " = ?";
        String[] selectionArgs = { name };

        // DB query
        Cursor cursor = db.query(
                DBContract.EventsSchema.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,          // don't group the rows
                null,           // don't filter by row groups
                null           // don't sort the rows
        );

        // create a card object
        Event event = null;
        if (cursor.moveToNext()){
            String eventName = cursor.getString(
                    cursor.getColumnIndexOrThrow(DBContract.EventsSchema.COLUMN_NAME_NAME));
            String description = cursor.getString(
                    cursor.getColumnIndexOrThrow(DBContract.EventsSchema.COLUMN_NAME_DESCRIPTION));
            String tag = cursor.getString(
                    cursor.getColumnIndexOrThrow(DBContract.EventsSchema.COLUMN_NAME_TAG));
            int positiveInt = cursor.getInt(
                    cursor.getColumnIndexOrThrow(DBContract.EventsSchema.COLUMN_NAME_POSITIVE));


            boolean positive = positiveInt == 1;

            event = new Event(name, description, tag, positive);

        }
        cursor.close();

        return event;
    }

    @Override
    public ArrayList<String> retrieveAllEventNames() {
        ArrayList<String> events = new ArrayList<>();
        String name;

        // Gets the data repository in read mode
        SQLiteDatabase db = this.getDBHelper().getReadableDatabase();

        // make query
        String table = DBContract.EventsSchema.TABLE_NAME;
        String field = DBContract.EventsSchema.COLUMN_NAME_NAME;
        Cursor  cursor = db.rawQuery(
                "select " + field + " from " + table,null);

        // exhaust cursor and build list
        while (cursor.moveToNext()){
            name = cursor.getString(
                    cursor.getColumnIndexOrThrow(DBContract.EventsSchema.COLUMN_NAME_NAME));

            events.add(name);
        }
        return events;
    }
}
