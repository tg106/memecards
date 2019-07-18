package com.example.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.memecards.R;

public class LibraryPopupCardActivity extends AppCompatActivity {
    private String name;
    private String desc;
    private int price;
    private int imageID;
    private int position;
    private boolean locked;
    private Button unlockButton;
    private String upv;
    private String tag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library_popup_card);

        //Make popup be in the center
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
        this.position = getIntent().getExtras().getInt("Position");
        this.upv = getIntent().getExtras().getString("Upv");
        this.tag = getIntent().getExtras().getString("Tag");

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

        libraryPopupCardName = findViewById(R.id.libraryPopupCardTag);
        libraryPopupCardName.setText(this.tag);
        libraryPopupCardName = findViewById(R.id.libraryPopupCardATK);
        libraryPopupCardName.setText(this.upv);
    }

    public void unlockCard(View v) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("Name", this.name);
        resultIntent.putExtra("Position", this.position);
        resultIntent.putExtra("Price", this.price);
        setResult(RESULT_OK, resultIntent);
        finish();
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
