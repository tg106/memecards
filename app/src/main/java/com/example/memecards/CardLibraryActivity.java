package com.example.memecards;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

import com.example.memedatabase.BattleDeckStub;
import com.example.memedatabase.MasterDeckStub; // temporary

public class CardLibraryActivity extends AppCompatActivity {
    private Button Back;
//    private ImageView myCardImage;
//    private TextView myCardName;
//    private ListView myListView;
    private ArrayList<String> Names = new ArrayList<>();
    private ArrayList<String> ImageURLs = new ArrayList<>();

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

        MasterDeckStub masterDeck = null;
        BattleDeckStub temp = new BattleDeckStub(masterDeck);

        int numCards = temp.numCards();
        int numLocked = 0; // temporary
        String number1 = "Total Cards: ";
        String number2 = "Locked Cards: ";
        number1 += Integer.toString(numCards);
        number2 += Integer.toString(numLocked);
        CardsNum.setText(number1);
        LockedNum.setText(number2);
    }

    private void InitImages(){
        ImageURLs.add("@drawable/no1");
        Names.add("No1 name");
        ImageURLs.add("@drawable/no2");
        Names.add("No2 name");
        ImageURLs.add("@drawable/no3");
        Names.add("No3 name");

        MakeCardsList();
    }

    private void MakeCardsList(){
//        myListView = (ListView)findViewById(R.id.ListView);
//
//        ArrayList<Card> list = new ArrayList<>();
//        list.add(new Card("@drawable/no1", "No1 name"));
//        list.add(new Card("@drawable/no2", "No2 name"));
//        list.add(new Card("@drawable/no3", "No3 name"));
//
//
//        myListView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.RecyclerView);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, Names, ImageURLs);
        recyclerView.setAdapter(adapter);
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
