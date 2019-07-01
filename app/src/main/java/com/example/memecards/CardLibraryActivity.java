package com.example.memecards;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import com.example.domainobjects.MemeCard;
import com.example.memedatabase.DBLoader;
import com.example.memedatabase.MasterDeck;
import com.example.memedatabase.MasterDeckInterface;
import com.example.memedatabase.MasterDeckStub;

public class CardLibraryActivity extends AppCompatActivity {
    private ArrayList<MemeCard> cards = new ArrayList<>();
    private MasterDeckInterface masterDeck = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_library);

        // instantiate master Deck
        this.masterDeck = new MasterDeck(this.getApplicationContext());

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
        RecyclerView myRecyView = (RecyclerView)findViewById(R.id.RecyclerView);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, this.cards);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        myRecyView.setLayoutManager(layoutManager);
        myRecyView.setAdapter(adapter);
    }

    @Override
    protected void onResume () {
        super.onResume();
        hideActionBar();
    }

    public void unlockCard(View v) {
        String new_card = null;
        for (MemeCard card : this.cards){
            if (card.isLocked()) {
                masterDeck.unlockCard(card.getName());
                new_card = card.getName();
                Toast.makeText(
                        CardLibraryActivity.this,
                        "Congratulations, you have unlocked " + new_card + "!!",
                        Toast.LENGTH_SHORT).show();
                break;
            }
        }
        if (new_card == null)
            Toast.makeText(
                    CardLibraryActivity.this,
                    "You've already unlocked all cards!!!",
                    Toast.LENGTH_SHORT).show();
        else {
            //update recycler view
            MakeCardsList();
            showCardStats();
        }

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
