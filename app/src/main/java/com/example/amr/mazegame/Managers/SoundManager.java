package com.example.amr.mazegame.Managers;

import android.media.MediaPlayer;

import com.example.amr.mazegame.Activities.ActivityLevel;
import com.example.amr.mazegame.MazeCreator.GameView;
import com.example.amr.mazegame.R;

/**
 * Created by Amr on 06/02/2018.
 */
public class SoundManager {

    public static MediaPlayer background1 , background2 , background3, loss;
    public static MediaPlayer slow_down , speed_up , take_number, win;

    public SoundManager(){
        initializing();
    }

    private void initializing() {
         loss = Loader(false, 100, R.raw.loss);
         slow_down = Loader(false, 100, R.raw.slow_down);
         speed_up = Loader(false, 100, R.raw.speed_up);
         take_number = Loader(false, 100, R.raw.take_number);
         win = Loader(false, 100, R.raw.win);
    }


    public static MediaPlayer Loader( boolean isloop , int Volume , int res ){
        MediaPlayer player = MediaPlayer.create(ActivityLevel.ActivityLevel , res);
        player.setLooping(isloop); // Set looping
        player.setVolume(Volume,Volume);
        return player ;
    }

    public static void stopAllBackground(){
        if ( SoundManager.background1 != null )
            SoundManager.background1.stop();

        if ( SoundManager.background2 != null )
            SoundManager.background2.stop();

        if ( SoundManager.background3 != null )
            SoundManager.background3.stop();
    }

}
