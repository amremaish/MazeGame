package com.example.amr.mazegame.Levels;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.widget.TextView;

import com.example.amr.mazegame.Activities.ActivityLevel;
import com.example.amr.mazegame.Activities.LevelSelector;
import com.example.amr.mazegame.Managers.SoundManager;
import com.example.amr.mazegame.Managers.ref;
import com.example.amr.mazegame.MazeCreator.GameView;
import com.example.amr.mazegame.R;
import com.example.amr.mazegame.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by Amr on 02/02/2018.
 */
public class LevelTwo {

    private int NumbersXY[][] = {
            {35, 4},
            {28, 18},
            {16, 23},
            {2, 28}
    };


    // X , Y , ( slow = 0 , speed  = 1) , Taken
    private int SlowAndSpeedXY[][] = {
            {12, 2, 1, 0},
            {34, 10, 1, 0},
            {21, 1, 0, 0},
            {1, 12, 0, 0}
    };

    private Bitmap SpeedUpImg, SlowDownImg;
    private int TILE_WIDTH;
    private GameView game;
    private ArrayList<Integer> OriginalNumbers;
    private int Answer;

    public LevelTwo(GameView game) {
        this.game = game;
        this.TILE_WIDTH = game.getTileWidth();
        initializing();
        initializeAssets();

    }

    private void initializeAssets() {
        // text
        TextView levelName = (TextView) ActivityLevel.ActivityLevel.findViewById(R.id.levelName);
        levelName.setText("المرحلة 2 متوسط المستوى رقم"+" " +(GameView.levelCounter+1) );
        // sound
        SoundManager.stopAllBackground();
        SoundManager.background2 =  SoundManager.Loader(true, 60, R.raw.background2);
        SoundManager.background2.start();
    }
    public void initializing() {
        OriginalNumbers = new ArrayList<>();
        SpeedUpImg = BitmapFactory.decodeResource(ActivityLevel.ActivityLevel.getResources(), R.drawable.speedup);
        SlowDownImg = BitmapFactory.decodeResource(ActivityLevel.ActivityLevel.getResources(), R.drawable.slowdown);
        // scale
        SpeedUpImg = Bitmap.createScaledBitmap(SpeedUpImg, TILE_WIDTH * 3, TILE_WIDTH * 3, false);
        SlowDownImg = Bitmap.createScaledBitmap(SlowDownImg, TILE_WIDTH * 3, TILE_WIDTH * 3, false);
        generateQuestionAndAnswer();
    }

    private void generateQuestionAndAnswer() {
        int n1 = new Random().nextInt(5) + 1;
        int n2 = new Random().nextInt(5) + 1;
        // set Question
        TextView Question = (TextView) ActivityLevel.ActivityLevel.findViewById(R.id.Quesion);
       int signR = new Random().nextInt(2);
       char sign  = ((signR == 0) ? '+' : '-');
        // swap two number to insure n1 > n2
        if (n2 > n1){
            int temp = n1 ;
            n1 = n2 ;
            n2 = temp ;
        }
        Question.setText(n1 + " "+sign+" "+ n2 + " = ?");
        if (sign == '-') {
            OriginalNumbers.add(n1 - n2);
            Answer = n1 - n2;
        }
        else {
            OriginalNumbers.add(n1 + n2);
            Answer = n1 + n2;
        }
        boolean freq[] = new boolean[11] ;
        freq[Answer] = true ;
        for (int i = 0; i < NumbersXY.length - 1; i++) {
            int n = 0 ;
            while (true) {
                n = new Random().nextInt(10) + 1;
                if (!freq[n]){
                    break;
                }
            }
            freq[n]= true;
            OriginalNumbers.add(n);
        }
        Collections.shuffle(OriginalNumbers);
    }

    public void Update(Canvas canvas) {
        if (GameView.FINISH)
            return;

        // draw images
        for (int i = 0; i < SlowAndSpeedXY.length; i++) {
            if (SlowAndSpeedXY[i][3] == 1)
                continue;
            Bitmap bitmap = ((SlowAndSpeedXY[i][2] == 1) ? SpeedUpImg : SlowDownImg);
            canvas.drawBitmap(bitmap, SlowAndSpeedXY[i][0] * TILE_WIDTH, SlowAndSpeedXY[i][1] * TILE_WIDTH, new Paint());
        }
        SlowAndSpeedCollision();
        // ---------------------------------------------------------
        // draw numbers
        Paint pt = new Paint();
        pt.setColor(Color.parseColor(ref.number_color));
        pt.setFakeBoldText(true);
        pt.setTextSize(TILE_WIDTH * 3);
        for (int i = 0; i < NumbersXY.length; i++) {
            canvas.drawText(OriginalNumbers.get(i) + "", NumbersXY[i][0] *
                    TILE_WIDTH, NumbersXY[i][1] * TILE_WIDTH, pt);
        }
        NumberCollision();

    }

    private void SlowAndSpeedCollision() {
        for (int i = 0; i < SlowAndSpeedXY.length; i++) {
            int NumX = SlowAndSpeedXY[i][0] * TILE_WIDTH;
            int NumY = SlowAndSpeedXY[i][1] * TILE_WIDTH;
            if (game.getLastPlayerX() > NumX - TILE_WIDTH * 2 && game.getLastPlayerX() < NumX + TILE_WIDTH  * 2.5 &&
                    game.getLastPlayerY() > NumY - TILE_WIDTH  * 2&& game.getLastPlayerY() < NumY + TILE_WIDTH  * 2.5
                    && SlowAndSpeedXY[i][3] == 0) {
                if (SlowAndSpeedXY[i][2] == 1) {
                    SoundManager.speed_up.start();
                    game.setPlayerSpeed(0.2f);
                } else {
                    SoundManager.slow_down.start();
                    game.setPlayerSpeed(0.1f);
                }
                SlowAndSpeedXY[i][3] = 1;
            }
        }
    }

    private void NumberCollision() {
        int chosenNumber = -1;
        for (int i = 0; i < NumbersXY.length; i++) {
            int NumX = NumbersXY[i][0] * TILE_WIDTH;
            int NumY = NumbersXY[i][1] * TILE_WIDTH;
            if (game.getLastPlayerX() > NumX - TILE_WIDTH && game.getLastPlayerX() < NumX + TILE_WIDTH + TILE_WIDTH &&
                    game.getLastPlayerY() > NumY - TILE_WIDTH && game.getLastPlayerY() < NumY + TILE_WIDTH + TILE_WIDTH) {
                chosenNumber = OriginalNumbers.get(i);
                break;
            }
        }

        if (chosenNumber == -1) {
            return;
        }

        if (chosenNumber == Answer) {
            Util.OpenDialog(ActivityLevel.ActivityLevel, "أحسنت أجابة صحيحة ", false);
            SoundManager.win.start();
        }
        else {
            Util.OpenDialog(ActivityLevel.ActivityLevel, "أخطأت.. الإجابة الصحيحة هى " + Answer, false);
            SoundManager.loss.start();
        }

        if (GameView.levelCounter == 4){
            LevelSelector.SELECTED_LEVEL = 3 ;
            GameView.levelCounter = 0 ;
        }
        else {
            LevelSelector.SELECTED_LEVEL = 2;
        }
        GameView.levelCounter++;
        GameView.FINISH = true;
        game.setWorkOnceForLvl(false);

    }
}
