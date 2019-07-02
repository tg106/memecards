package com.example.memecards;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import com.example.memedatabase.MasterDeck;
import com.example.memedatabase.MasterDeckInterface;
import com.example.memedatabase.PlayerStats;
import com.example.memedatabase.PlayerStatsInterface;

public class LibraryPopupCardActivity extends AppCompatActivity {
    private String name;
    private String desc;
    private int price;
    private int imageID;
    private boolean locked;
    private MasterDeckInterface masterDeck = null;
    private PlayerStatsInterface player;
    private Button unlockButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library_popup_card);

        // instantiate master Deck
        this.masterDeck = new MasterDeck(this.getApplicationContext());
        // instantiate player stats
        this.player = new PlayerStats(this.getApplicationContext());

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        getWindow().setLayout(800,1200);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);

        this.desc = getIntent().getExtras().getString("Description");
        this.imageID = getIntent().getExtras().getInt("ImageID");
        this.price = getIntent().getExtras().getInt("Price");
        this.locked = getIntent().getExtras().getBoolean("Locked");
        this.name = getIntent().getExtras().getString("Name");

        this.unlockButton = (Button) findViewById(R.id.libraryPopupCardUnlockButton);
        this.unlockButton.setText("Unlock for " + price);

        if (locked) {
            this.unlockButton.setVisibility(View.VISIBLE);
        }

        displayCardContent();
    }

    public void displayCardContent() {
        ImageView libraryPopupCardImage = findViewById(R.id.libraryPopupCardImage);
        TextView libraryPopupCardDesc = findViewById(R.id.libraryPopupCardDesc);
        TextView libraryPopupCardName = findViewById(R.id.libraryPopupCardName);

        libraryPopupCardName.setText(this.name);
        libraryPopupCardDesc.setText(this.desc);
        libraryPopupCardImage.setImageResource(this.imageID);
    }

    public void unlockCard(View v) {
        // Checks player's cash before unlocking
//        if (this.locked && (this.player.getPlayerCash() - this.price >= 0)) {
//            masterDeck.unlockCard(this.name);
//            this.player.subtractPlayerCash(this.price);
//            Intent intent = new Intent(this, CardLibraryActivity.class);
//            startActivity(intent);
//        } else {
//            Toast.makeText(LibraryPopupCardActivity.this, "You've already unlocked all cards!!!", Toast.LENGTH_SHORT).show();
//        }

        masterDeck.unlockCard(this.name);
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

    @Override
    protected void onResume () {
        super.onResume();
        hideActionBar();
    }
}
