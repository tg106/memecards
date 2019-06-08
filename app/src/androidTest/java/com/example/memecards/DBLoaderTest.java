package com.example.memecards;

import android.content.Context;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import com.example.memedatabase.DBLoader;
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

        // instantiate DB
        MasterDeckInterface db = new MasterDeckStub();

        //load json content
        DBLoader.loadMasterDeck(db, appContext);
        ArrayList<String> cardNames = db.retrieveAllCardNames();

        // db should be filled
        assertTrue(cardNames.size() > 0);
    }
}
