package com.example.memecards;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.domainobjects.Deck;
import com.example.domainobjects.EventList;
import com.example.domainobjects.MemeCard;
import com.example.gamelogic.GameEngine;
import com.example.memedatabase.DBLoader;
import com.example.memedatabase.MasterDeckInterface;
import com.example.memedatabase.MasterDeckStub;

import java.util.ArrayList;
import java.util.List;

public class StartGameActivity extends AppCompatActivity implements View.OnClickListener {

    List<MemeCard> test;
    Deck d;
    CardView[] hand_card_btn = new CardView[5];
    EventList evL;
    GameEngine gameEngine;
    MemeCard card_played_by_human;
    MemeCard card_played_by_AI;
    int time_for_a_turn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);

        //get DB
        MasterDeckInterface db = new MasterDeckStub();

        // load DB
        DBLoader.loadMasterDeck(db, this.getApplicationContext());

        //get cards
        test = new ArrayList<MemeCard>();
        for (String cardName : db.retrieveAllCardNames()) {
            test.add(db.retrieveCard(cardName));
        }

        //Deck for user player
        d = new Deck(test);

        //Generating time for a turn
        time_for_a_turn = 7;

        //Creating gameEngine (gamelogic)
        gameEngine = new GameEngine(d, 0);


        displayDeck();;

        //Generating button for cardview
        for (int i = 0; i < 5; i++) {
            int tempI = getCardView(i);
            hand_card_btn[i] = (CardView) findViewById(tempI);
            hand_card_btn[i].setOnClickListener(this);
        }

        //Generating events and display the events
        gameEngine.generatingEventList();
        evL = gameEngine.getEventList();
        displayEvents();;

    }

    //Display the user's deck to the screen
    private void displayDeck() {
        ImageView curV;
        TextView curT;
        int tempI;
        for (int i = 0; i < 5; i++) {
            tempI = getCardImg(i);
            curV = (ImageView) findViewById(tempI);
            curV.setImageResource(d.getCardinDeck(i).getResId());
            tempI = getCardUpvote(i);
            curT = (TextView) findViewById(tempI);
            curT.setText(d.getCardinDeck(i).getUpvotesStr());
        }
    }

    //Display events to the screen
    private void displayEvents() {
        TextView curT;
        int tempI;
        for (int i = 0; i < 3; i++) {
            tempI = getEventDisplayPosition(i);
            curT = (TextView) findViewById(tempI);
            curT.setText(evL.getEventList().get(i).getDesc());

            tempI = getEventPriorityPosition(i);
            curT = (TextView) findViewById(tempI);
            switch (evL.getEventByPos(i).getPrio()) {
                case 2:
                    curT.setText("Hot");
                    break;
                case 1:
                    curT.setText("New");
                    break;
                case 0:
                    curT.setText("Fluff");
                    break;
            }
        }
    }

    //display the card user played in the middle screen
    private void displayCardPlayed(int pos) {
        ImageView card_img;
        TextView card_name;
        TextView card_upvote;
        ImageView upvote_icon;

        card_img = (ImageView) findViewById(R.id.cardfield_img_1);
        card_img.setImageResource(d.getCardinDeck(pos).getResId());

        card_name = (TextView) findViewById(R.id.cardfield_name_1);
        card_name.setText(d.getCardinDeck(pos).getName());

        card_upvote = (TextView) findViewById(R.id.cardfield_text_1);
        card_upvote.setText(d.getCardinDeck(pos).getUpvotesStr());

        upvote_icon = (ImageView) findViewById(R.id.cardfield_upvote_img_1);
        upvote_icon.setImageResource(R.drawable.icon_upvote);

        card_name = (TextView) findViewById(R.id.cardfield_tag_1);
        card_name.setText(d.getCardinDeck(pos).getTag());
    }

    //Display the card that AI played in the middle screen
    private void displayAIplayedCard(MemeCard card) {
        ImageView card_img;
        TextView card_name;
        TextView card_upvote;
        ImageView upvote_icon;

        card_img = (ImageView) findViewById(R.id.cardfield_img_0);
        card_img.setImageResource(card.getResId());

        card_name = (TextView) findViewById(R.id.cardfield_name_0);
        card_name.setText(card.getName());

        card_upvote = (TextView) findViewById(R.id.cardfield_text_0);
        card_upvote.setText(card.getUpvotesStr());

        upvote_icon = (ImageView) findViewById(R.id.cardfield_upvote_img_0);
        upvote_icon.setImageResource(R.drawable.icon_upvote);

        card_name = (TextView) findViewById(R.id.cardfield_tag_0);
        card_name.setText(card.getTag());
    }

    //Get the card image position in the screen to display it
    private int getCardImg(int pos) {
        String tempS = "card_img_" + pos;
        int tempI = getResources().getIdentifier(tempS, "id",getPackageName() );
        return tempI;
    }

    //Get the card upvote position in the screen to display it
    private int getCardUpvote(int pos) {
        String tempS = "card_text_" + pos;
        int tempI = getResources().getIdentifier(tempS, "id",getPackageName() );
        return tempI;
    }

    //Get the cardview position in the screen to display it
    private int getCardView(int pos) {
        String tempS = "cardview_" + pos;
        int tempI = getResources().getIdentifier(tempS, "id",getPackageName() );
        return tempI;
    }

    //Get the event position in the screen to display it
    private int getEventDisplayPosition (int pos) {
        String tempS = "event_display_" + pos;
        int tempI = getResources().getIdentifier(tempS, "id",getPackageName() );
        return tempI;
    }

    //Get the event priority position in the screen to display it
    private int getEventPriorityPosition (int pos) {
        String tempS = "event_priority_display_" + pos;
        int tempI = getResources().getIdentifier(tempS, "id",getPackageName() );
        return tempI;
    }

    //Get the card's tag position in the screen to display it
    private int getCardfieldTagPosition(int pos) {
        String tempS = "cardfield_tag_" + pos;
        int tempI = getResources().getIdentifier(tempS, "id",getPackageName() );
        return tempI;
    }

    //Make the card clickable or unclickable
    private void makeCardClickable(boolean b) {
        for (int i = 0; i < 5; i++)
        {
            hand_card_btn[i].setClickable(b);
        }
    }

    //Update the new card upvotes to the display
    private void updateUpvotes() {
        int upvForHuman = gameEngine.calculateNewUpv(card_played_by_human.getUpvotes(), card_played_by_human.getTag());
        int upvForAI = gameEngine.calculateNewUpv(card_played_by_AI.getUpvotes(), card_played_by_AI.getTag());
        card_played_by_AI.setUpvotes(upvForAI);
        card_played_by_human.setUpvotes(upvForHuman);

        TextView temp  = (TextView) findViewById(R.id.cardfield_text_0);
        temp.setText(card_played_by_AI.getUpvotesStr());
        temp  = (TextView) findViewById(R.id.cardfield_text_1);
        temp.setText(card_played_by_human.getUpvotesStr());
    }

    //Decise which player win the turn and increase point for that player
    private void deciseWinnerForATurn() {
        int humanUpv = card_played_by_human.getUpvotes();
        int aiUpv = card_played_by_AI.getUpvotes();

        if (humanUpv > aiUpv) {
            String temp = card_played_by_human.getName() + " won, Congrats";
            Toast.makeText(StartGameActivity.this, temp,Toast.LENGTH_SHORT).show();
            gameEngine.increaseScoreForHuman();
        } else if (aiUpv > humanUpv) {
            String temp = card_played_by_AI.getName() + " won, Too bad loser";
            Toast.makeText(StartGameActivity.this, temp,Toast.LENGTH_SHORT).show();
            gameEngine.increaseScoreForAI();
        } else {
            Toast.makeText(StartGameActivity.this, "WOW, we get a draw,  both players will get a point",Toast.LENGTH_SHORT).show();
            gameEngine.increaseScoreForHuman();
            gameEngine.increaseScoreForAI();
        }

        updateScore();;
    }

    //Update the new score to the scoreboard
    private void updateScore() {
        TextView temp  = (TextView) findViewById(R.id.score_for_AI);
        temp.setText( "" + gameEngine.getScoreForAI());
        temp  = (TextView) findViewById(R.id.score_for_Human);
        temp.setText( "" +gameEngine.getScoreForHuman());
    }


    @Override
    protected void onResume () {
        super.onResume();
        hideActionBar();
    }

    //The game flow, this is each phase of a turn, each phase will do something
    private void gamePlayFlow() {
        new CountDownTimer(13000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                if (time_for_a_turn == 5) {
                    Toast.makeText(StartGameActivity.this, "Calculating Upvotes...",Toast.LENGTH_SHORT).show();
                }

                if (time_for_a_turn == 4) {
                    Toast.makeText(StartGameActivity.this, "Updated Upvotes for both cards",Toast.LENGTH_SHORT).show();
                    updateUpvotes();;
                }

                if (time_for_a_turn == 2) {
                    deciseWinnerForATurn();;
                }

                time_for_a_turn--;
            }

            @Override
            public void onFinish() {
                gameEngine.nextTurn();
                if (!gameEngine.checkIfGameisOver()) {
                    Toast.makeText(StartGameActivity.this, "Next Turn", Toast.LENGTH_SHORT).show();
                    makeCardClickable(true);
                    time_for_a_turn = 13;
                } else {
                    if (gameEngine.getScoreForHuman() >= 3) {
                        Toast.makeText(StartGameActivity.this, "Congrats, you won", Toast.LENGTH_SHORT).show();
                        finish();;
                    } else {
                        Toast.makeText(StartGameActivity.this, "Too bad, you lost, what an idiot", Toast.LENGTH_SHORT).show();
                        finish();;
                    }
                }
            }
        }.start();
    }

    //Get a move from AI player
    public void getMoveFromAI() {
        if (gameEngine.checkAImovable())
            card_played_by_AI = gameEngine.moveByAI();

    }

    //Display the animation after user pick a card
    private void delayDisplayForAnimation(int pos) {
        final int position = pos;
        final ProgressBar pb1 = findViewById(R.id.cardfield_progressBar_1);
        final ProgressBar pb2 = findViewById(R.id.cardfield_progressBar_0);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pb1.setVisibility(View.GONE);
                pb2.setVisibility(View.GONE);
                displayCardPlayed(position);
                displayAIplayedCard(card_played_by_AI);
            }
        }, 1500);
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



    //Open a popup card when player choose a card (it will contains all of the card's information)
    @Override
    public void onClick(View v) {

        int tempI = 0;
        switch (((View)v).getId() ) {
            case R.id.cardview_0:
                tempI = 0;
                break;
            case R.id.cardview_1:
                tempI = 1;
                break;
            case R.id.cardview_2:
                tempI = 2;
                break;
            case R.id.cardview_3:
                tempI = 3;
                break;
            case R.id.cardview_4:
                tempI = 4;
                break;
        }
        Intent i = new Intent(getApplicationContext(), PopupCardActivity.class);
        i.putExtra("Name", d.getCardinDeck(tempI).getName());
        i.putExtra("Upvote", d.getCardinDeck(tempI).getUpvotesStr());
        i.putExtra("Desc", d.getCardinDeck(tempI).getDescription());
        i.putExtra("Img",d.getCardinDeck(tempI).getResId());
        i.putExtra("Pos", tempI);
        i.putExtra("Tag", d.getCardinDeck(tempI).getTag());

        startActivityForResult(i,1);

    }

    //After player click "play card", play that card and create the game flow
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                final int card_played_pos = data.getIntExtra("CardPlayed",0);
                card_played_by_human = d.getCardinDeck(card_played_pos);
                Toast.makeText(StartGameActivity.this, "Posting meme, Opponent's also posting...", Toast.LENGTH_SHORT).show();
                ProgressBar temp = findViewById(R.id.cardfield_progressBar_1);
                ProgressBar temp2 = findViewById(R.id.cardfield_progressBar_0);
                temp.setVisibility(View.VISIBLE);
                temp2.setVisibility(View.VISIBLE);
                makeCardClickable(false);
                hand_card_btn[card_played_pos].setVisibility(View.GONE);
                getMoveFromAI();
                delayDisplayForAnimation(card_played_pos);
                gamePlayFlow();

            }
        }
    }
}
