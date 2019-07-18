package com.example.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.memecards.R;
import com.example.memedatabase.sqlite.implementations.PlayerStats;
import com.example.memedatabase.dbinterface.PlayerStatsInterface;

public class PopUpEndGameActivity extends AppCompatActivity {

    Button btn;
    boolean win;
    PlayerStatsInterface cashDB;
    int cash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_end_game);

        this.win = getIntent().getExtras().getBoolean("Win");

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int w = dm.widthPixels;
        int h = dm.heightPixels;

        getWindow().setLayout((int)(w*0.8),(int)(h*0.8));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);

        cashDB = new PlayerStats(this.getApplicationContext());
        cash = cashDB.getPlayerCash();

        displayEndGameBoard();

        this.btn = (Button) findViewById(R.id.endgame_button);

        this.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
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

    @Override
    protected void onResume () {
        super.onResume();
        hideActionBar();
    }

    public void displayEndGameBoard() {

        TextView tempV = findViewById(R.id.popup_endgame_titile);

        if (!win) {
            tempV.setText("TOO BAD DUMBASS");
            tempV = findViewById(R.id.popup_endgame_text);
            tempV.setText("YOU LOST");
            tempV = findViewById(R.id.popup_endgame_gold);
            tempV.setText("+ 0 gold");
            tempV = findViewById(R.id.player_cash);
            tempV.setText("Cash: " + cash);
        } else {
            tempV = findViewById(R.id.player_cash);
            tempV.setText("Cash: " + cash + " + 69");
            cashDB.addPlayerCash(69);
        }
    }

}
