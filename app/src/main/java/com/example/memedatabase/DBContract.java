package com.example.memedatabase;

import android.provider.BaseColumns;

public final class DBContract {
    // This must be incremented if DB schema changes
    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "MemeCards.db";

    // Make the constructor private to prevent accidental instantiation.
    private DBContract() {}

    /* Inner class that defines the CardsTable contents */
    public static class CardsSchema implements BaseColumns {
        public static final String TABLE_NAME = "cards";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_IMGPATH = "imgpath";
        public static final String COLUMN_NAME_UPVOTES = "upvotes";
        public static final String COLUMN_NAME_LOCKED = "locked";
    }

    /* Inner class that defines the BattleDeckTable contents */
    public static class BattleDeckSchema implements BaseColumns {
        public static final String TABLE_NAME = "battledeck";
        public static final String COLUMN_NAME_NAME = "name";
    }

    /* Inner class that defines the PlayerStatsTable contents */
    public static class PlayerStatsSchema implements BaseColumns {
        public static final String TABLE_NAME = "playerstats";
        public static final String COLUMN_NAME_CASH = "cash";
    }
}
