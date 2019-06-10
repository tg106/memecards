package com.example.memecards;

import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import com.example.domainobjects.MemeCard;
import com.example.memedatabase.DBLoader;
import com.example.memedatabase.MasterDeckInterface;
import com.example.memedatabase.MasterDeckStub;

public class CardLibraryActivity extends AppCompatActivity {
    private Button Back;
//    private ImageView myCardImage;
//    private TextView myCardName;
//    private ListView myListView;
//    private ArrayList<String> Names = new ArrayList<>();
//    private ArrayList<String> ImageURLs = new ArrayList<>();
    private List<Card> myCardList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_library);

        Back = (Button)findViewById(R.id.Back); // back to home page
        ShowCardsNumber();
//        myCardImage = (ImageView)findViewById(R.id.CardImage);
//        myCardName = (TextView)findViewById(R.id.CardName);
//
//        int imageResource = getResources().getIdentifier("@drawable/no1", null, this.getPackageName());
//        myCardImage.setImageResource(imageResource);
//        myCardName.setText("Card name");
        MakeCardsList();
    }

    /** Back to the home page when the user taps the BACK button */
    public void BackHomePage(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    // Show the number of total cards and locked cards
    private void ShowCardsNumber(){
        TextView CardsNum = (TextView) findViewById(R.id.CardsNum);
        TextView LockedNum = (TextView) findViewById(R.id.LockedNum);

//        MasterDeckStub masterDeck = null;
//        BattleDeckStub temp = new BattleDeckStub(masterDeck);
//        int numCards = temp.numCards();
        MasterDeckInterface masterDB = new MasterDeckStub();
        DBLoader.loadMasterDeck(masterDB, this);
        int numCards = masterDB.deckSize();
        //=========================================================
        int numLocked = 0; // temporary
        String number1 = "Total Cards: ";
        String number2 = "Locked Cards: ";
        number1 += Integer.toString(numCards);
        number2 += Integer.toString(numLocked);
        CardsNum.setText(number1);
        LockedNum.setText(number2);
    }

//    private void InitImages(){
//        ImageURLs.add("@drawable/no1");
//        Names.add("No1 name");
//        ImageURLs.add("@drawable/no2");
//        Names.add("No2 name");
//        ImageURLs.add("@drawable/no3");
//        Names.add("No3 name");
//
//        MakeCardsList();
//    }

    private void MakeCardsList(){
//        myListView = (ListView)findViewById(R.id.ListView);
//
//        ArrayList<Card> list = new ArrayList<>();
//        list.add(new Card("@drawable/no1", "No1 name"));
//        list.add(new Card("@drawable/no2", "No2 name"));
//        list.add(new Card("@drawable/no3", "No3 name"));
//        CustomListAdapter adapter = new CustomListAdapter(this, myCardName, myCardImage);
//        myListView.setAdapter(adapter);

//        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
//        RecyclerView recyclerView = findViewById(R.id.RecyclerView);
//        recyclerView.setLayoutManager(layoutManager);
//        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, Names, ImageURLs);
//        recyclerView.setAdapter(adapter);
        myCardList = new ArrayList<>();

        MasterDeckInterface masterDB = new MasterDeckStub();
        DBLoader.loadMasterDeck(masterDB, this);
        ArrayList<String> allCardsName = masterDB.retrieveAllCardNames();
        for(int i = 0; i < 20; i++) {
            MemeCard newMemeCard = new MemeCard(allCardsName.get(i), "", "", 0, "", false);
            Card newCard = new Card(newMemeCard);
            myCardList.add(newCard);
        }
        RecyclerView myRecyView = (RecyclerView)findViewById(R.id.RecyclerView);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, myCardList);
        //GridLayoutManager layoutManager = new GridLayoutManager(this, 5);// how many cards per row
        //layoutManager.setOrientation(GridLayoutManager.HORIZONTAL);
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
