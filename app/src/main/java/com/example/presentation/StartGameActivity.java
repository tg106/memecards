package com.example.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.domainobjects.Deck;
import com.example.domainobjects.Event;
import com.example.domainobjects.EventList;
import com.example.domainobjects.MemeCard;
import com.example.databaseloader.DBLoader;
import com.example.gamelogic.GameEngine;
import com.example.memecards.R;
import com.example.memedatabase.sqlite.implementations.BattleDeck;
import com.example.memedatabase.dbinterface.BattleDeckInterface;
import com.example.memedatabase.dbinterface.EventListInterface;
import com.example.memedatabase.stubs.EventListStub;
import com.example.memedatabase.sqlite.implementations.MasterDeck;
import com.example.memedatabase.dbinterface.MasterDeckInterface;

import java.util.ArrayList;

public class StartGameActivity extends AppCompatActivity implements View.OnClickListener {

    Deck d;
    CardView[] hand_card_btn = new CardView[5];
    EventList evL;
    GameEngine gameEngine;
    MemeCard card_played_by_human;
    MemeCard card_played_by_AI;
    int time_for_a_turn;
    Animation smalltobig;
    Animation bigtosmall;
    Button quit_btn;
    boolean end;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);

        smalltobig = AnimationUtils.loadAnimation(this, R.anim.smalltobig);
        bigtosmall = AnimationUtils.loadAnimation(this, R.anim.bigtosmall);

        int mode = getIntent().getExtras().getInt("Mode");

        EventListInterface eDB = new EventListStub();
        DBLoader.loadEventsList(eDB, this.getApplicationContext());

        ArrayList<MemeCard> modeDeck;
        if (mode != 1)
        {
            BattleDeckInterface deckDB = new BattleDeck(this.getApplicationContext());
            modeDeck = ((BattleDeck) deckDB).retrieveAllCards();
        } else {
            MasterDeckInterface mdDB = new MasterDeck(this.getApplicationContext());
            ArrayList<MemeCard> master_deck = ((MasterDeck) mdDB).retrieveAllCards();
            modeDeck = new ArrayList<MemeCard>();
            for (int i = 0; i < 5; i++)
            {
                modeDeck.add(master_deck.get( (int) (Math.random() * master_deck.size()) ));
            }
        }

        //get events
        ArrayList<Event> evList = new ArrayList<Event>();
        for (String eventName : eDB.retrieveAllEventNames())
            evList.add(eDB.retrieveEvent(eventName));

        //Deck for user player
        this.d = new Deck(modeDeck);

        //Generating time for a turn
        this.time_for_a_turn = 7;

        //Creating gameEngine (gamelogic)
        MasterDeckInterface aiDeckDB = new MasterDeck(this.getApplicationContext());
        ArrayList<MemeCard> aiDeck = ((MasterDeck) aiDeckDB).retrieveAllCards();
        this.gameEngine = new GameEngine(d, 0, aiDeck);


        displayDeck();;

        //Generating button for cardview
        for (int i = 0; i < 5; i++) {
            int tempI = getCardView(i);
            this.hand_card_btn[i] = (CardView) findViewById(tempI);
            this.hand_card_btn[i].setOnClickListener(this);
        }

        quit_btn = (Button) findViewById(R.id.ragequit_btn);
        quit_btn.setOnClickListener(this);

        //Generating events and display the events
        this.gameEngine.generatingAllEventList(evList);
        this.evL = this.gameEngine.generatingNewEvents();
        displayEvents();;

        makeCardClickable(false);
        displayEventAnimation();
        end = false;

        if (mode == 2) {
            countdown();
            Toast.makeText(StartGameActivity.this, "You have 30 seconds to beat the AI, if not you lose", Toast.LENGTH_SHORT).show();
        }

    }


    //Time constrainst mode
    private void countdown() {

        final View tempV = findViewById(R.id.time_constraint_mode);
        tempV.setVisibility(View.VISIBLE);
        final TextView tempT = findViewById(R.id.time_countdown);

        new CountDownTimer(30000,1000) {
            int timer = 30;

            @Override
            public void onTick(long millisUntilFinished) {
                timer--;
               tempT.setText(timer + "");
            }

            @Override
            public void onFinish() {
                tempT.setText("0");
                if (!gameEngine.checkIfGameisOver())
                {
                    Toast.makeText(StartGameActivity.this, "Time's up", Toast.LENGTH_SHORT).show();
                    makeCardClickable(false);
                    Intent newIntent = new Intent(getApplicationContext(), PopUpEndGameActivity.class);
                    newIntent.putExtra("Win", false);
                    startActivityForResult(newIntent, 2);
                    end = true;
                }
            }
        }.start();
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
            curT.setText(evL.getEventByPos(i).getDesc());

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

    //Get the event info board position in the screen to display it
    private int getEventBoardInfoPosition(int pos) {
        String tempS = "info_board_event_info_" + pos;
        int tempI = getResources().getIdentifier(tempS, "id",getPackageName() );
        return tempI;
    }

    //Get the event priority position in info board
    private int getEventPrioBoardInfoPosition(int pos) {
        String tempS = "info_board_event_tag_" + pos;
        int tempI = getResources().getIdentifier(tempS, "id",getPackageName() );
        return tempI;
    }

    //Get AI hand card position
    private int getHandCardForAIPosition(int pos) {
        String tempS = "ai_card_" + pos;
        int tempI = getResources().getIdentifier(tempS, "id",getPackageName() );
        return tempI;
    }

    //Make the card clickable or unclickable
    private void makeCardClickable(boolean b) {
        for (int i = 0; i < 5; i++)
        {
            this.hand_card_btn[i].setClickable(b);
        }
    }

    //Update the new card upvotes to the display
    private void updateUpvotes() {
        int upvForHuman = this.gameEngine.calculateNewUpv(
                this.card_played_by_human.getUpvotes(),
                this.card_played_by_human.getTag()
        );
        int upvForAI = this.gameEngine.calculateNewUpv(
                this.card_played_by_AI.getUpvotes(),
                this.card_played_by_AI.getTag()
        );
        this.card_played_by_AI.setUpvotes(upvForAI);
        this.card_played_by_human.setUpvotes(upvForHuman);

        TextView temp  = (TextView) findViewById(R.id.cardfield_text_0);
        temp.setText(this.card_played_by_AI.getUpvotesStr());
        temp  = (TextView) findViewById(R.id.cardfield_text_1);
        temp.setText(this.card_played_by_human.getUpvotesStr());
    }

    //Decise which player win the turn and increase point for that player
    private void deciseWinnerForATurn() {
        int humanUpv = this.card_played_by_human.getUpvotes();
        int aiUpv = this.card_played_by_AI.getUpvotes();

        if (humanUpv > aiUpv) {
            String temp = this.card_played_by_human.getName() + " won, Congrats";
            Toast.makeText(StartGameActivity.this, temp,Toast.LENGTH_SHORT).show();
            this.gameEngine.increaseScoreForHuman();
        } else if (aiUpv > humanUpv) {
            String temp = this.card_played_by_AI.getName() + " won, Too bad loser";
            Toast.makeText(StartGameActivity.this, temp,Toast.LENGTH_SHORT).show();
            this.gameEngine.increaseScoreForAI();
        } else {
            Toast.makeText(
                    StartGameActivity.this,
                    "WOW, we get a draw,  both players will get a point",
                    Toast.LENGTH_SHORT
            ).show();
            this.gameEngine.increaseScoreForHuman();
            this.gameEngine.increaseScoreForAI();
        }

        updateScore();;
    }

    //Update the new score to the scoreboard
    private void updateScore() {
        TextView temp  = (TextView) findViewById(R.id.score_for_AI);
        temp.setText( "" + this.gameEngine.getScoreForAI());
        temp  = (TextView) findViewById(R.id.score_for_Human);
        temp.setText( "" + this.gameEngine.getScoreForHuman());
    }


    @Override
    protected void onResume () {
        super.onResume();
        hideActionBar();
    }

    //The game flow, this is each phase of a turn, each phase will display something
    private void gamePlayFlow() {
        new CountDownTimer(7000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                if (time_for_a_turn == 5 && !end) {
                    Toast.makeText(StartGameActivity.this, "Calculating Upvotes...",Toast.LENGTH_SHORT).show();
                }

                if (time_for_a_turn == 4 && !end)  {
                    Toast.makeText(StartGameActivity.this, "Updated Upvotes for both cards",Toast.LENGTH_SHORT).show();
                    updateUpvotes();;
                }

                if (time_for_a_turn == 2 && !end) {
                    deciseWinnerForATurn();;
                }

                time_for_a_turn--;
            }

            @Override
            public void onFinish() {
                gameEngine.nextTurn();
                if (!gameEngine.checkIfGameisOver() && !end) {
                    Toast.makeText(StartGameActivity.this, "Next Turn", Toast.LENGTH_SHORT).show();
                    time_for_a_turn = 7;
                    evL = gameEngine.generatingNewEvents();
                    displayEvents();;
                    setCardFieldVisible(false);
                    displayEventAnimation();
                } else if (!end) {
                    Intent newIntent = new Intent(getApplicationContext(), PopUpEndGameActivity.class);
                    if (gameEngine.getScoreForHuman() >= 3) {
                        newIntent.putExtra("Win",  true);
                    } else {
                        newIntent.putExtra("Win", false);
                    }
                    startActivityForResult(newIntent, 2);
                }
            }
        }.start();
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

    //make cardfield invisible
    public  void setCardFieldVisible(boolean b)
    {
        View temp = (LinearLayout) findViewById(R.id.human_cardfield);
        if (b)
        {
            temp.setVisibility(View.VISIBLE);
            temp.startAnimation(smalltobig);
            temp = (LinearLayout) findViewById(R.id.ai_cardfield);
            temp.setVisibility(View.VISIBLE);
            temp.startAnimation(smalltobig);
            temp = (TextView) findViewById(R.id.vs_text);
            temp.setVisibility(View.VISIBLE);
            temp.startAnimation(smalltobig);
        } else {
            hideViewAnimation(temp);
            temp = (LinearLayout) findViewById(R.id.ai_cardfield);
            hideViewAnimation(temp);
            temp = (TextView) findViewById(R.id.vs_text);
            hideViewAnimation(temp);
            temp = (LinearLayout) findViewById(R.id.event_board);
            hideViewAnimation(temp);
        }
    }

    //reset the cardfield img to make it blank
    public  void resetCardField() {
        View temp = (ImageView) findViewById(R.id.cardfield_img_1);
        ((ImageView) temp).setImageResource(0);
        temp = (ImageView) findViewById(R.id.cardfield_img_0);
        ((ImageView) temp).setImageResource(0);
        temp = (ImageView) findViewById(R.id.cardfield_upvote_img_1);
        ((ImageView) temp).setImageResource(0);
        temp = (ImageView) findViewById(R.id.cardfield_upvote_img_0);
        ((ImageView) temp).setImageResource(0);
        temp = (TextView) findViewById(R.id.cardfield_name_0);
        ((TextView) temp).setText("");
        temp = (TextView) findViewById(R.id.cardfield_name_1);
        ((TextView) temp).setText("");
        temp = (TextView) findViewById(R.id.cardfield_tag_0);
        ((TextView) temp).setText("");
        temp = (TextView) findViewById(R.id.cardfield_tag_1);
        ((TextView) temp).setText("");
        temp = (TextView) findViewById(R.id.cardfield_text_0);
        ((TextView) temp).setText("");
        temp = (TextView) findViewById(R.id.cardfield_text_1);
        ((TextView) temp).setText("");
    }

    //displaying event in the middle of the screen in early round
    private void displayEventAnimation() {
        final LinearLayout info_board = (LinearLayout) findViewById(R.id.info_board);
        info_board.setVisibility(View.VISIBLE);

        if (gameEngine.getTurn() > 0)
            info_board.startAnimation(smalltobig);

        TextView curT;
        int tempI;
        for (int i = 0; i < 3; i++) {
            tempI = getEventBoardInfoPosition(i);
            curT = (TextView) findViewById(tempI);
            curT.setText(evL.getEventByPos(i).getDesc());

            tempI = getEventPrioBoardInfoPosition(i);
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

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hideViewAnimation(info_board);
                View tempV = (LinearLayout) findViewById(R.id.event_board);
                tempV.setVisibility(View.VISIBLE);
                tempV.startAnimation(smalltobig);
                makeCardClickable(true);
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

        boolean check = true;

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

        if  ( ((View)v).getId() == R.id.ragequit_btn ) {
            check = false;
            gameEngine.gameEnd();
            finish();
        }

        if (check) {
            Intent i = new Intent(getApplicationContext(), PopupCardActivity.class);
            i.putExtra("Name", d.getCardinDeck(tempI).getName());
            i.putExtra("Upvote", d.getCardinDeck(tempI).getUpvotesStr());
            i.putExtra("Desc", d.getCardinDeck(tempI).getDescription());
            i.putExtra("Img", d.getCardinDeck(tempI).getResId());
            i.putExtra("Pos", tempI);
            i.putExtra("Tag", d.getCardinDeck(tempI).getTag());

            startActivityForResult(i, 1);
        }
    }

    //After player click "play card", play that card and display the played card on the field and move to next turn (in gameflow)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                final int card_played_pos = data.getIntExtra("CardPlayed",0);
                this.card_played_by_human = d.getCardinDeck(card_played_pos);
                Toast.makeText(StartGameActivity.this, "Posting meme, Opponent's also posting...", Toast.LENGTH_SHORT).show();
                setCardFieldVisible(true);
                View temp = findViewById(R.id.cardfield_progressBar_1);
                ProgressBar temp2 = findViewById(R.id.cardfield_progressBar_0);
                temp.setVisibility(View.VISIBLE);
                temp2.setVisibility(View.VISIBLE);
                resetCardField();
                makeCardClickable(false);
                hideHumanPlayedCard(card_played_pos);
                if (gameEngine.checkAImovable())
                    card_played_by_AI = gameEngine.moveByAI();
                temp = findViewById(getHandCardForAIPosition(gameEngine.getTurn()));
                temp.animate().alpha(0).setDuration(500);
                temp.startAnimation(bigtosmall);
                delayDisplayForAnimation(card_played_pos);
                gamePlayFlow();

            }
        } else if (requestCode == 2) {
            if (resultCode == RESULT_OK)
                finish();
        }
    }


    //Animation APIs

    //hide played card animation
    public void hideHumanPlayedCard(int pos)
    {
        final int position = pos;
        hand_card_btn[pos].animate().alpha(0).setDuration(500);
        hand_card_btn[pos].startAnimation(bigtosmall);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hand_card_btn[position].setVisibility(View.INVISIBLE);
            }
        }, 1000);
    }

    //hide view animation
    public void hideViewAnimation(View temp) {
        final View tempV = temp;
        tempV.startAnimation(bigtosmall);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tempV.setVisibility(View.INVISIBLE);
            }
        }, 500);
    }

}
