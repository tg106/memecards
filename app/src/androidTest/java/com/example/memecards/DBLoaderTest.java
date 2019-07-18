package com.example.memecards;

import android.content.Context;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import com.example.memedatabase.sqlite.core.DBHelper;
import com.example.databaseloader.DBLoader;
import com.example.memedatabase.sqlite.implementations.EventList;
import com.example.memedatabase.dbinterface.EventListInterface;
import com.example.memedatabase.sqlite.implementations.MasterDeck;
import com.example.memedatabase.dbinterface.MasterDeckInterface;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class DBLoaderTest {
    @Test
    public void testDBLoad() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        DBHelper dbHelper = new DBHelper(appContext);
        dbHelper.resetDB();

        // load master deck test
        MasterDeckInterface masterDB = new MasterDeck(appContext);
        ArrayList<String> cardNamess = masterDB.retrieveAllCardNames();
        assertTrue(cardNamess.size() == 0);
        DBLoader.loadMasterDeck(masterDB, appContext);
        ArrayList<String> cardNames = masterDB.retrieveAllCardNames();
        assertTrue(cardNames.size() > 0);

        // load events list test
        EventListInterface eventDB = new EventList(appContext);
        ArrayList<String> eventNamess = eventDB.retrieveAllEventNames();
        assertTrue(eventNamess.size() == 0);
        DBLoader.loadEventsList(eventDB, appContext);
        ArrayList<String> eventNames = eventDB.retrieveAllEventNames();
        assertTrue(eventNames.size() > 0);

        // clear db after testing
        dbHelper.resetDB();
    }
}
