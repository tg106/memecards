package com.example.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.memecards.R;

public class ModeSelectPopup extends AppCompatActivity {

    Button personalize_mode_btn;
    Button random_mode_btn;
    Button time_constrainst_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_select_popup);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int w = dm.widthPixels;
        int h = dm.heightPixels;

        getWindow().setLayout((int)(w*0.8),(int)(h*0.6));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);
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

    public void selectPersonalizeMode(View v) {
        Intent intent = new Intent(getApplicationContext(), StartGameActivity.class);
        intent.putExtra("Mode", 0);
        startActivity(intent);
        finish();;
    }

    public void selectRandomMode(View v) {
        Intent intent = new Intent(getApplicationContext(), StartGameActivity.class);
        intent.putExtra("Mode", 1);
        startActivity(intent);
        finish();;
    }

    public void selectTimeConstrainstMode(View v) {
        Intent intent = new Intent(getApplicationContext(), StartGameActivity.class);
        intent.putExtra("Mode", 2);
        startActivity(intent);
        finish();;
    }
}
