package com.example.amr.mazegame.Activities;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.amr.mazegame.Managers.KeyManager;
import com.example.amr.mazegame.Managers.SoundManager;
import com.example.amr.mazegame.R;

public class ActivityLevel extends AppCompatActivity {

   public static  AppCompatActivity ActivityLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
       setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // Key Manager
        new KeyManager((Button)findViewById(R.id.DownBtnLvl1) ,
                (Button)findViewById(R.id.UpBtnLvl1) ,
                (Button)findViewById(R.id.LeftBtnLvl1)  ,
                (Button)findViewById(R.id.RightBtnLvl1));

        ActivityLevel = this;

    }

    @Override
    public void onBackPressed() {
    SoundManager.stopAllBackground();
    finish();

    }


}
