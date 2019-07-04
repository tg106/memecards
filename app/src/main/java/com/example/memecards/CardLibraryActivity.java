package com.example.memecards;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import com.example.domainobjects.MemeCard;
import com.example.memedatabase.DBLoader;
import com.example.memedatabase.MasterDeckInterface;
import com.example.memedatabase.MasterDeckStub;
import com.example.memedatabase.BattleDeckInterface;
import com.example.memedatabase.BattleDeck;

public class CardLibraryActivity extends AppCompatActivity {
    private ArrayList<MemeCard> cards = new ArrayList<>();
    private static MasterDeckInterface masterDeck = new MasterDeckStub();
    private BattleDeckInterface battleDeck;
    private RecyclerViewAdapter adapter;
    private Button myEdit; // edit button to start edit the customized deck
    private Button mySave; // save the selections for the customized deck
    private Button myCancel; // cancel the selections for the customized deck
    public static boolean isStart = false; // true: start the edit mode; false: exit the edit mode
    public static boolean isCancel = true; // true: clean all selections and exit the edit mode

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_library);

        // load DB into static stub
        DBLoader.loadMasterDeck(this.masterDeck, this.getApplicationContext());
        // load Battle Deck
        this.battleDeck = new BattleDeck(this.getApplicationContext());

        MemeCard card;
        for (String n : this.masterDeck.retrieveAllCardNames()) {
            card = this.masterDeck.retrieveCard(n);
            this.cards.add(card);
        }

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

    // show all cards in the card library activity
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
        this.myEdit = (Button) findViewById(R.id.StartEdit);
        this.mySave = (Button) findViewById(R.id.SaveSelected);
        this.myCancel = (Button) findViewById(R.id.CancelSelected);
        mySave.setVisibility(View.INVISIBLE); // only show when on the edit mode
        myCancel.setVisibility(View.INVISIBLE); //

        myRecyView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        adapter = new RecyclerViewAdapter(this, this.cards);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        myRecyView.setLayoutManager(layoutManager);
        myRecyView.setAdapter(adapter);

        // save button click listener
        mySave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adapter.getSelected().size() == 5) {
                    //StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0; i < adapter.getSelected().size(); i++) {
//                        stringBuilder.append(adapter.getSelected().get(i).getName());
//                        stringBuilder.append("\n");
                        //=========================================================================
                        battleDeck.insertCard(adapter.getSelected().get(i).getName());
                        //=========================================================================
                        isStart = false; // successfully saved then exit
                        mySave.setVisibility(View.INVISIBLE);
                        myCancel.setVisibility(View.INVISIBLE);
                        myEdit.setVisibility(View.VISIBLE);
                    }
                    //showToast(stringBuilder.toString());
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
                }
                // no selection
                else{
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "No Selection", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
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
        adapter.notifyDataSetChanged();
    }

    // tell the adapter that we exit the edit mode
    public void cancelSelect(View v){
        isCancel = true;
        isStart = false;
        mySave.setVisibility(View.INVISIBLE);
        myCancel.setVisibility(View.INVISIBLE);
        myEdit.setVisibility(View.VISIBLE);
        adapter.notifyDataSetChanged();
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
