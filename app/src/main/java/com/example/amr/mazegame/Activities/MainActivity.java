package com.example.amr.mazegame.Activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.example.amr.mazegame.R;

public class MainActivity extends AppCompatActivity {

    public static int MOBILE_WIDTH , MOBILE_HEIGHT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
         MOBILE_WIDTH =  dm.widthPixels;
         MOBILE_HEIGHT = dm.heightPixels;

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public void startAction(View view) {
        Intent i = new Intent(this,LevelSelector.class);
        startActivity(i);
    }

    public void contactAction(View view) {
        Intent i = new Intent(this,Contact.class);
        startActivity(i);
    }

    public void aboutAppAction(View view) {

        Intent i = new Intent(this,AboutApp.class);
        startActivity(i);
    }
}
