package com.example.memecards;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import java.util.ArrayList;

import com.example.domainobjects.MemeCard;
import com.example.memedatabase.DBLoader;
import com.example.memedatabase.MasterDeckStub;

public class CardLibraryActivity extends AppCompatActivity {
    private ArrayList<MemeCard> cards = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_library);

        // load DB into static stub
        MasterDeckStub masterDeck = new MasterDeckStub();
        DBLoader.loadMasterDeck(masterDeck, this.getApplicationContext());
        MemeCard card;
        for (String n : masterDeck.retrieveAllCardNames()) {
            System.out.println(n);
            card = masterDeck.retrieveCard(n);
            this.cards.add(card);
        }

        showCardStats(masterDeck);
        MakeCardsList();
    }

    /** Back to the home page when the user taps the BACK button */
    public void BackHomePage(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    // Show the number of total cards and locked cards
    private void showCardStats(MasterDeckStub masterDeck){
        TextView unlockedText = findViewById(R.id.UnlockedText);
        TextView unlockedNum = findViewById(R.id.UnlockedNum);

        unlockedText.setText("Unlocked Cards");
        unlockedNum.setText(":  " + masterDeck.retrieveUnlockedCardNames().size() + " / " + masterDeck.deckSize());
    }

    private void MakeCardsList(){
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

    /** Hides the status bar and action bar for an activity**/
    private void hideActionBar(){
        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        // Hide the action bar
        getSupportActionBar().hide();
    }
}
