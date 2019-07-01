package com.example.memecards;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class LibraryPopupCardActivity extends AppCompatActivity {
    String price;
    String desc;
    int imageID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library_popup_card);

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

        displayCardContent();
    }

    public void displayCardContent() {
        ImageView libraryPopupCardImage = findViewById(R.id.libraryPopupCardImage);
        TextView libraryPopupCardDesc = findViewById(R.id.libraryPopupCardDesc);

        libraryPopupCardDesc.setText(this.desc);
        libraryPopupCardImage.setImageResource(this.imageID);
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
