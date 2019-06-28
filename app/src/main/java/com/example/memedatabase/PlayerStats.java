package com.example.memedatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.domainobjects.MemeCard;

import java.util.ArrayList;

public class PlayerStats implements PlayerStatsInterface {
    private DBHelper dbHelper;
    private Context context;

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
        int cash;

        // Gets the data repository in read mode
        SQLiteDatabase db = this.getDBHelper().getReadableDatabase();

        // make query
        String table = DBContract.PlayerStatsSchema.TABLE_NAME;
        String field = DBContract.PlayerStatsSchema.COLUMN_NAME_CASH;
        Cursor cursor = db.rawQuery(
                "select " + field + " from " + table,null);

        // get db entry
        cash = cursor.getInt(
                cursor.getColumnIndexOrThrow(DBContract.PlayerStatsSchema.COLUMN_NAME_CASH));

        return cash;
    }

    @Override
    public boolean addPlayerCash(int amount){
        int cash = this.getPlayerCash();
        cash += amount;
        return this.updatePlayerCash(cash);
    }

    @Override
    public boolean subtractPlayerCash(int amount){
        int cash = this.getPlayerCash();
        cash -= amount;
        if (cash >= 0)
            return this.updatePlayerCash(cash);
        else
            return false;
    }

    private boolean updatePlayerCash(int amount){
        // Gets the data repository in write mode
        SQLiteDatabase db = this.getDBHelper().getWritableDatabase();

        // the field to update
        ContentValues values = new ContentValues();
        values.put(DBContract.PlayerStatsSchema.COLUMN_NAME_CASH, amount);

        int count = db.update(
                DBContract.PlayerStatsSchema.TABLE_NAME,
                values,
                null,
                null);

        return count > 0;
    }
}
