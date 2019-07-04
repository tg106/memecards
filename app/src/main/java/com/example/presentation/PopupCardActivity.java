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


public class PopupCardActivity extends AppCompatActivity {

    String name;
    String desc;
    String upvotes;
    String tag;
    int imgpath;
    int position;

    Button playCard_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_card);

        this.name = getIntent().getExtras().getString("Name");
        this.desc = getIntent().getExtras().getString("Desc");
        this.upvotes = getIntent().getExtras().getString("Upvote");
        this.imgpath = getIntent().getExtras().getInt("Img");
        this.position = getIntent().getExtras().getInt("Pos");
        this.tag = getIntent().getExtras().getString("Tag");


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        getWindow().setLayout(800,1200);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);
        displayCardContent();

        this.playCard_btn = (Button) findViewById(R.id.playcard_popup_btn);

        this.playCard_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("CardPlayed",position);

                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

    }

    public void displayCardContent()
    {
        ImageView img = findViewById(R.id.popupcard_img_0);
        TextView cardDesc = findViewById(R.id.popupcard_description_0);
        TextView cardName = findViewById(R.id.popupcard_name_0);
        TextView cardUpvote = findViewById(R.id.popupcard_atk_0);
        TextView cardTag = findViewById(R.id.popupcard_tag_0);

        img.setImageResource(this.imgpath);
        cardName.setText(this.name);
        cardDesc.setText(this.desc);
        cardUpvote.setText(this.upvotes);
        cardTag.setText(this.tag);
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
