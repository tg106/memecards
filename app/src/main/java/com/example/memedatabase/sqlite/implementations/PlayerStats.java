package com.example.memedatabase.sqlite.implementations;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.memedatabase.dbinterface.PlayerStatsInterface;
import com.example.memedatabase.dbinterface.InsufficientCashException;
import com.example.memedatabase.sqlite.core.DBContract;
import com.example.memedatabase.sqlite.core.DBHelper;

public class PlayerStats implements PlayerStatsInterface {
    private DBHelper dbHelper;
    private Context context;
    private static String CASH_KEY = "cash";

    public PlayerStats(Context context){
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
    public int getPlayerCash(){
        this.ensureInitialized();
        return Integer.parseInt(this.getItem(PlayerStats.CASH_KEY));
    }

    @Override
    public boolean addPlayerCash(int amount){
        int cash = this.getPlayerCash();
        cash += amount;
        return this.updatePlayerCash(cash);
    }

    @Override
    public void subtractPlayerCash(int amount){
        int cash = this.getPlayerCash();
        cash -= amount;
        if (cash >= 0)
            this.updatePlayerCash(cash);
        else
            throw new InsufficientCashException("Insufficient cash : Trying to substract " + amount + " from " + cash);
    }

    private boolean updatePlayerCash(int amount){
        this.ensureInitialized();
        return this.updateItem(PlayerStats.CASH_KEY, Integer.toString(amount));
    }

    private String getItem(String key){
        // Gets the data repository in read mode
        SQLiteDatabase db = this.getDBHelper().getReadableDatabase();

        // Define query projection
        String[] projection = {
                DBContract.PlayerStatsSchema.COLUMN_NAME_VALUE,
        };

        // Result filter
        String selection = DBContract.PlayerStatsSchema.COLUMN_NAME_NAME + " = ?";
        String[] selectionArgs = { key };

        // DB query
        Cursor cursor = db.query(
                DBContract.PlayerStatsSchema.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,          // don't group the rows
                null,           // don't filter by row groups
                null           // don't sort the rows
        );

        // query battle deck
        String val = null;
        if (cursor.moveToNext()){
            val = cursor.getString(
                    cursor.getColumnIndexOrThrow(DBContract.PlayerStatsSchema.COLUMN_NAME_VALUE));
        }
        cursor.close();

        return val;
    }

    private boolean updateItem(String key, String val){
        // Gets the data repository in write mode
        SQLiteDatabase db = this.getDBHelper().getWritableDatabase();

        // The field to update, i.e chg the 'locked' field to 0
        ContentValues values = new ContentValues();
        values.put(DBContract.PlayerStatsSchema.COLUMN_NAME_VALUE, val);

        // Which row to update, based on the cardName
        String selection = DBContract.PlayerStatsSchema.COLUMN_NAME_NAME + " = ?";
        String[] selectionArgs = { key };

        int count = db.update(
                DBContract.PlayerStatsSchema.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        return count > 0;
    }

    private boolean putItem(String key, String val){
        // Gets the data repository in write mode
        SQLiteDatabase db = this.getDBHelper().getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(DBContract.PlayerStatsSchema.COLUMN_NAME_NAME, key);
        values.put(DBContract.PlayerStatsSchema.COLUMN_NAME_VALUE, val);

        // Insert the new row, returning the primary key value of the new row, return -1 if fails
        long newRowId = db.insert(DBContract.PlayerStatsSchema.TABLE_NAME, null, values);

        boolean result = true;
        if (newRowId < 0)
            result = false;

        return result;
    }

    private void ensureInitialized(){
        if (this.getItem(PlayerStats.CASH_KEY) == null)
            this.putItem(PlayerStats.CASH_KEY, "200");
    }
}
