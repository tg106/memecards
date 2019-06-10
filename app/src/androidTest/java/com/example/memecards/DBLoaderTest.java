package com.example.memecards;

import android.content.Context;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import com.example.memedatabase.DBLoader;
import com.example.memedatabase.EventListInterface;
import com.example.memedatabase.EventListStub;
import com.example.memedatabase.MasterDeckInterface;
import com.example.memedatabase.MasterDeckStub;

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

        // load master deck test
        MasterDeckInterface masterDB = new MasterDeckStub();
        DBLoader.loadMasterDeck(masterDB, appContext);
        ArrayList<String> cardNames = masterDB.retrieveAllCardNames();
        assertTrue(cardNames.size() > 0);

        // load events list test
        EventListInterface eventDB = new EventListStub();
        DBLoader.loadEventsList(eventDB, appContext);
        ArrayList<String> eventNames = eventDB.retrieveAllEventNames();
        assertTrue(eventNames.size() > 0);
    }
}
