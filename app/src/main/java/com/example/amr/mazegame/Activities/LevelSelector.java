package com.example.amr.mazegame.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.amr.mazegame.R;

public class LevelSelector extends AppCompatActivity {

    public static int SELECTED_LEVEL ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_selector);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    }

    public void Level1Action(View view) {
        SELECTED_LEVEL = 1 ;
        Intent i = new Intent(this,ActivityLevel.class);
        startActivity(i);
    }

    public void Level2Action(View view) {
        SELECTED_LEVEL = 2 ;
        Intent i = new Intent(this,ActivityLevel.class);
        startActivity(i);
    }

    public void Level3Action(View view) {
        SELECTED_LEVEL = 3 ;
        Intent i = new Intent(this,ActivityLevel.class);
        startActivity(i);
    }

}
