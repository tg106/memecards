package com.example.memecards;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CardInformationActivity extends AppCompatActivity {
    private TextView InfoDesc;
    private ImageView InfoImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_information);
        // hide status bar
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        ShowCardInformation();
    }

    public void ShowCardInformation(){
        InfoDesc = (TextView)findViewById(R.id.CardInfoDesc);
        InfoImage = (ImageView)findViewById(R.id.CardInfoImage);

        Intent intent = getIntent();
        String desc = intent.getExtras().getString("Description");
        int image = intent.getExtras().getInt("ImageID");

        InfoDesc.setText(desc);
        InfoImage.setImageResource(image);
    }

    /** Back to the home page when the user taps the BACK button */
    public void BackCardLib(View v) {
        Intent intent = new Intent(this, CardLibraryActivity.class);
        startActivity(intent);
    }
}
