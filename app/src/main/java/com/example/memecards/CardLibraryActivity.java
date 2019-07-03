package com.example.memecards;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import com.example.domainobjects.MemeCard;
import com.example.memedatabase.MasterDeck;
import com.example.memedatabase.MasterDeckInterface;
import com.example.memedatabase.PlayerStats;
import com.example.memedatabase.PlayerStatsInterface;


public class CardLibraryActivity extends AppCompatActivity {
    private ArrayList<MemeCard> cards = new ArrayList<>();
    private MasterDeckInterface masterDeck = null;
    private PlayerStatsInterface playerStats = null;
    private RecyclerView myRecyView;
    private RecyclerViewAdapter adapter;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_library);

        // instantiate master deck
        this.masterDeck = new MasterDeck(this.getApplicationContext());
        // instantiate player stats
        this.playerStats = new PlayerStats(this.getApplicationContext());

        showCardStats();
        MakeCardsList();
    }

    /** Back to the home page when the user taps the BACK button */
    public void BackHomePage(View v) {
//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);
        onBackPressed();
    }

    // Show the number of total cards and locked cards
    private void showCardStats() {
        TextView unlockedText = findViewById(R.id.UnlockedText);
        TextView unlockedNum = findViewById(R.id.UnlockedNum);

        unlockedText.setText("Unlocked Cards");
        unlockedNum.setText(
                ":  " + this.masterDeck.retrieveUnlockedCardNames().size() +
                        " / " + this.masterDeck.deckSize()
        );
    }

    private void MakeCardsList() {
        ArrayList<MemeCard> lockedCards = new ArrayList<>();
        this.cards.clear();
        MemeCard card;
        // make sure unlocked cards is at the front of list
        for (String n : this.masterDeck.retrieveAllCardNames()) {
            card = this.masterDeck.retrieveCard(n);
            if (card.isLocked())
                lockedCards.add(card);
            else
                this.cards.add(card);
        }
        this.cards.addAll(lockedCards);
        this.myRecyView = (RecyclerView)findViewById(R.id.RecyclerView);
        this.adapter = new RecyclerViewAdapter(this, this.cards);
        this.layoutManager = new LinearLayoutManager(this);
        this.layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        this.myRecyView.setLayoutManager(layoutManager);
        this.myRecyView.setAdapter(adapter);
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
