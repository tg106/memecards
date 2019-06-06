package com.example.memecards;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button battle;
    private Button library;
    private Button quit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        battle = (Button) findViewById(R.id.battle);
        library = (Button) findViewById(R.id.library);
        quit = (Button) findViewById(R.id.quit);

        battle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStartGameActivity();
            }
        });

        library.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCardLibraryActivity();
            }
        });

        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });
    }

    @Override
    protected void onResume () {
        super.onResume();
        hideActionBar();
    }

    /** Called when the user taps the StartGame button */
    public void openStartGameActivity() {
        Intent intent = new Intent(this, StartGameActivity.class);
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
