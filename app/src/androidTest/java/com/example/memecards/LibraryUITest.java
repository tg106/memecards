package com.example.memecards;

import android.content.Context;

import androidx.test.InstrumentationRegistry;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.memedatabase.sqlite.core.DBHelper;
import com.example.databaseloader.DBLoader;
import com.example.presentation.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LibraryUITest {
    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setupDatabase(){
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        DBHelper dbHelper = new DBHelper(appContext);
        // reset DB
        dbHelper.resetDB();

        // load DB
        DBLoader.loadDB(appContext);

    }

    @After
    public void dropDatabase(){
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        DBHelper dbHelper = new DBHelper(appContext);
        // reset DB
        dbHelper.resetDB();
    }


    @Test
    public void viewUnlockedCards() {
        // go to library
        onView(withId(R.id.library)).perform(click());
        // filter unlocked cards
        onView(withId(R.id.ShowUnlocked)).perform(click());
        // view cards one at a time
        onView(withId(R.id.RecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(isRoot()).perform(ViewActions.pressBack());
        onView(withId(R.id.RecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(isRoot()).perform(ViewActions.pressBack());
        onView(withId(R.id.RecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));
        onView(isRoot()).perform(ViewActions.pressBack());
        onView(withId(R.id.RecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));
        onView(isRoot()).perform(ViewActions.pressBack());
        onView(withId(R.id.RecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(4, click()));
    }

    @Test
    public void viewLockedCards() {
        // go to library
        onView(withId(R.id.library)).perform(click());
        //filter locked cards
        onView(withId(R.id.ShowLocked)).perform(click());
        // view cards one at a time
        onView(withId(R.id.RecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(isRoot()).perform(ViewActions.pressBack());
        onView(withId(R.id.RecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(4, click()));
        onView(isRoot()).perform(ViewActions.pressBack());
        onView(withId(R.id.RecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(8, click()));
        onView(isRoot()).perform(ViewActions.pressBack());
        onView(withId(R.id.RecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(12, click()));
    }

    @Test
    public void purchaseCards() {
        // go to library
        onView(withId(R.id.library)).perform(click());
        // show locked cards
        onView(withId(R.id.ShowLocked)).perform(click());
        //purchase one card
        onView(withId(R.id.RecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.libraryPopupCardUnlockButton)).perform(click());
        //purchase another card
        onView(withId(R.id.RecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withId(R.id.libraryPopupCardUnlockButton)).perform(click());
        // view unlocked cards
        onView(withId(R.id.ShowUnlocked)).perform(click());
        onView(withId(R.id.RecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(6, scrollTo()));
    }

    @Test
    public void editDeckAllowed() {
        /* When the player edits the deck successfully, i.e. 5 cards are selected before saving.
         */
        // go to library
        onView(withId(R.id.library)).perform(click());
        // show locked cards
        onView(withId(R.id.ShowLocked)).perform(click());
        // purchase 2 cards
        onView(withId(R.id.RecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.libraryPopupCardUnlockButton)).perform(click());
        onView(withId(R.id.RecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withId(R.id.libraryPopupCardUnlockButton)).perform(click());

        // show unlocked cards
        onView(withId(R.id.ShowUnlocked)).perform(click());
        // go into edit deck mode
        onView(withId(R.id.StartEdit)).perform(click());
        // select 5 cards for the deck
        onView(withId(R.id.RecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.RecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));
        onView(withId(R.id.RecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(4, click()));
        onView(withId(R.id.RecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(5, click()));
        onView(withId(R.id.RecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(6, click()));
        // save selection
        onView(withId(R.id.SaveSelected)).perform(click());
    }

    @Test
    public void editDeckCancel() {
        /* When the player goes into edit mode but the quits without saving.
         */
        // go to library
        onView(withId(R.id.library)).perform(click());
        // show locked cards
        onView(withId(R.id.ShowLocked)).perform(click());
        // purchase 2 cards
        onView(withId(R.id.RecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.libraryPopupCardUnlockButton)).perform(click());
        onView(withId(R.id.RecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withId(R.id.libraryPopupCardUnlockButton)).perform(click());

        // show unlocked cards
        onView(withId(R.id.ShowUnlocked)).perform(click());
        // go into edit deck mode
        onView(withId(R.id.StartEdit)).perform(click());
        // select 5 cards for the deck
        onView(withId(R.id.RecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.RecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));
        onView(withId(R.id.RecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(4, click()));
        onView(withId(R.id.RecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(6, click()));
        // cancel selection
        onView(withId(R.id.CancelSelected)).perform(click());
    }

    @Test
    public void editDeckIllegal() {
        /* When the player goes into edit mode and selects less than 5 card before saving.
        This is not allow and not changes are made to the deck.
         */
        // go to library
        onView(withId(R.id.library)).perform(click());
        // show locked cards
        onView(withId(R.id.ShowLocked)).perform(click());
        // purchase 2 cards
        onView(withId(R.id.RecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.libraryPopupCardUnlockButton)).perform(click());
        onView(withId(R.id.RecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withId(R.id.libraryPopupCardUnlockButton)).perform(click());

        // show unlocked cards
        onView(withId(R.id.ShowUnlocked)).perform(click());
        // go into edit deck mode
        onView(withId(R.id.StartEdit)).perform(click());
        // select 5 cards for the deck
        onView(withId(R.id.RecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.RecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(5, click()));
        onView(withId(R.id.RecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(6, click()));
        // save selection
        onView(withId(R.id.SaveSelected)).perform(click());
    }



}
