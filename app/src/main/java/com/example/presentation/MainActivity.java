package com.example.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.memecards.R;
import com.example.databaseloader.DBLoader;


public class MainActivity extends AppCompatActivity {
    private Button battle;
    private Button library;
    private Button quit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.battle = (Button) findViewById(R.id.battle);
        this.library = (Button) findViewById(R.id.library);
        this.quit = (Button) findViewById(R.id.quit);

        this.battle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStartGameActivity();
            }
        });

        this.library.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCardLibraryActivity();
            }
        });

        this.quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });

        Context context = this.getApplicationContext();

        // load DB
        DBLoader.loadDB(context);
    }

    @Override
    protected void onResume () {
        super.onResume();
        hideActionBar();
    }

    /** Called when the user taps the StartGame button */
    public void openStartGameActivity() {
        Intent intent = new Intent(this, ModeSelectPopup.class);
        startActivity(intent);
    }

    /** Called when the user taps the CardLibrary button */
    public void openCardLibraryActivity() {
        Intent intent = new Intent(this, CardLibraryActivity.class);
        startActivity(intent);
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
