package com.example.presentation;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.view.Gravity;
import android.widget.Button;


import java.util.ArrayList;

import com.example.domainobjects.MemeCard;
import com.example.memecards.R;
import com.example.memedatabase.sqlite.implementations.MasterDeck;
import com.example.memedatabase.dbinterface.MasterDeckInterface;
import com.example.memedatabase.sqlite.implementations.PlayerStats;
import com.example.memedatabase.dbinterface.PlayerStatsInterface;
import com.example.memedatabase.sqlite.implementations.BattleDeck;


public class CardLibraryActivity extends AppCompatActivity {
    private ArrayList<MemeCard> cards = new ArrayList<>();
    private MasterDeckInterface masterDeck = null;
    private PlayerStatsInterface playerStats = null;
    private BattleDeck battleDeck;

    private RecyclerView myRecyView;
    private RecyclerViewAdapter adapter;
    private LinearLayoutManager layoutManager;
    private Button myEdit; // edit button to start edit the customized deck
    private Button mySave; // save the selections for the customized deck
    private Button myCancel; // cancel the selections for the customized deck
    private RadioGroup myViewFilter;
    public static boolean isStart = false; // true: start the edit mode; false: exit the edit mode
    public static boolean isCancel = true; // true: clean all selections and exit the edit mode
    public static boolean showDeck = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_library);

        // instantiate master deck
        this.masterDeck = new MasterDeck(this.getApplicationContext());
        // instantiate player stats
        this.playerStats = new PlayerStats(this.getApplicationContext());
        // load Battle Deck
        this.battleDeck = new BattleDeck(this.getApplicationContext());

        showCash();
        showCardStats();
        MakeCardsList();
    }

    /** Back to the home page when the user taps the BACK button */
    public void BackHomePage(View v) {
        onBackPressed();
    }

    private void showCash() {
        TextView cashNum = findViewById(R.id.CashNum);
        cashNum.setText("  " + this.playerStats.getPlayerCash());
    }

    // Show the number of total cards and locked cards
    private void showCardStats() {
        TextView unlockedNum = findViewById(R.id.UnlockedNum);
        unlockedNum.setText(
                "  " + this.masterDeck.retrieveUnlockedCardNames().size() +
                        " / " + this.masterDeck.deckSize()
        );
    }

    private void MakeCardsList() {
        for (String s : masterDeck.retrieveAllCardNames()) {
            cards.add(masterDeck.retrieveCard(s));
        }
        this.myEdit = (Button) findViewById(R.id.StartEdit);
        this.mySave = (Button) findViewById(R.id.SaveSelected);
        this.myCancel = (Button) findViewById(R.id.CancelSelected);
        this.myViewFilter = (RadioGroup) findViewById(R.id.ViewFilter);

        this.myRecyView = (RecyclerView)findViewById(R.id.RecyclerView);
        this.adapter = new RecyclerViewAdapter(this, this.cards);
        this.layoutManager = new LinearLayoutManager(this);
        this.layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        this.myRecyView.setLayoutManager(layoutManager);
        this.myRecyView.setAdapter(adapter);
        myRecyView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        // save button click listener
        mySave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adapter.getSelected().size() == 5) {
                    battleDeck.clearDeck();
                    for (int i = 0; i < adapter.getSelected().size(); i++) {
                        battleDeck.insertCard(adapter.getSelected().get(i).getName());
                        isStart = false; // successfully saved then exit
                        mySave.setVisibility(View.INVISIBLE);
                        myCancel.setVisibility(View.INVISIBLE);
                        myEdit.setVisibility(View.VISIBLE);
                        myViewFilter.setVisibility(View.VISIBLE);
                    }
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Your deck has been saved.", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                }
                // select more or less than 5 cards
                else if (adapter.getSelected().size() != 5 && adapter.getSelected().size() != 0){
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Please select 5 cards.", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    cancelSelect(view);
                }
                // no selection
                else{
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "No Selection", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    cancelSelect(view);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    // tell the adapter that we are on the edit mode
    public void startSelect(View v){
        isStart = true;
        isCancel = false;
        mySave.setVisibility(View.VISIBLE);
        myCancel.setVisibility(View.VISIBLE);
        myEdit.setVisibility(View.INVISIBLE);
        myCancel.bringToFront();
        myViewFilter.setVisibility(View.INVISIBLE);
        adapter.notifyDataSetChanged();
    }

    // tell the adapter that we exit the edit mode
    public void cancelSelect(View v){
        isCancel = true;
        isStart = false;
        mySave.setVisibility(View.INVISIBLE);
        myCancel.setVisibility(View.INVISIBLE);
        myEdit.setVisibility(View.VISIBLE);
        myViewFilter.setVisibility(View.VISIBLE);
        adapter.notifyDataSetChanged();
    }

    public void showMyDeck(View v){
        cards.clear();
        for (String s : battleDeck.retrieveAllCardNames()) {
            cards.add(masterDeck.retrieveCard(s));
        }
        myEdit.setVisibility(View.INVISIBLE);
        this.adapter.notifyDataSetChanged();
    }

    public void showAll(View v) {
        cards.clear();
        for (String s : masterDeck.retrieveAllCardNames()) {
            cards.add(masterDeck.retrieveCard(s));
        }
        myEdit.setVisibility(View.VISIBLE);
        this.adapter.notifyDataSetChanged();
    }

    public void showUnlocked(View v) {
        cards.clear();
        for (String s : masterDeck.retrieveUnlockedCardNames()) {
            cards.add(masterDeck.retrieveCard(s));
        }
        myEdit.setVisibility(View.VISIBLE);
        this.adapter.notifyDataSetChanged();
    }

    public void showLocked(View v) {
        cards.clear();
        for (String s : masterDeck.retrieveLockedCardNames()) {
            cards.add(masterDeck.retrieveCard(s));
        }
        myEdit.setVisibility(View.VISIBLE);
        this.adapter.notifyDataSetChanged();
    }

    //Unlock button pressed on popup card
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent resultIntent) {
        super.onActivityResult(requestCode, resultCode, resultIntent);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                int price = resultIntent.getIntExtra("Price", Integer.MAX_VALUE);
                if (playerStats.getPlayerCash() -  price >= 0) {
                    this.playerStats.subtractPlayerCash(price);
                    this.masterDeck.unlockCard(resultIntent.getStringExtra("Name"));
                    this.adapter.notifyItemChanged(resultIntent.getIntExtra("Position", -1));
                    showCash();
                    showCardStats();
                } else {
                    Toast.makeText(CardLibraryActivity.this, "Not enough minerals.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onResume () {
        super.onResume();
        hideActionBar();
    }


    /** Hides the status bar and action bar for an activity**/
    private void hideActionBar() {
        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        // Hide the action bar
        getSupportActionBar().hide();
    }
}
