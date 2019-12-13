package com.example.amr.mazegame.Levels;

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
 * Created by Amr on 01/02/2018.
 */
public class LevelOne {

    //   X , Y , 0
    private int NumbersXY[][] = {
            {35, 4, 0},
            {28, 18, 0},
            {16, 23, 0},
            {2, 28, 0}
    };
    private int TILE_WIDTH;
    private GameView game;
    // if order  = 0 --> Descending
    // if order  = 1 ---> Ascending
    private int order;
    private ArrayList<Integer> OriginalNumbers, takenNumber;
    private boolean[] taken;

    public LevelOne(GameView game) {
        this.game = game;
        TILE_WIDTH = game.getTileWidth();
        initializeAssets();
        initializing();
    }

    private void initializeAssets() {
        // Text
        TextView Quesion = (TextView) ActivityLevel.ActivityLevel.findViewById(R.id.Quesion);
        // generate 0 or 1
        order = new Random().nextInt(2);
        if (order == 1)
            Quesion.setText("رتب الأرقام من صغير الى كبير");
        else
            Quesion.setText("رتب الأرقام من كبير الى صغير");
        TextView levelName = (TextView) ActivityLevel.ActivityLevel.findViewById(R.id.levelName);
        levelName.setText("المرحلة 1 سهل المستوى رقم" + " " + (GameView.levelCounter + 1));
        // sound
        SoundManager.stopAllBackground();
        SoundManager.background1 = SoundManager.Loader(true, 60, R.raw.background1);
        SoundManager.background1.start();
    }

    public void initializing() {
        OriginalNumbers = new ArrayList<>();
        takenNumber = new ArrayList<>();
        taken = new boolean[100];
        for (int i = 0; i < NumbersXY.length; i++) {
            NumbersXY[i][2] = 0;
        }
        GenerateNumbers();
    }

    private void GenerateNumbers() {
        boolean freq[] = new boolean[11];
        for (int i = 0; i < NumbersXY.length; i++) {
            int n = 0;
            while (true) {
                n = new Random().nextInt(10) + 1;
                if (freq[n] == false) {
                    break;
                }
            }
            freq[n] = true;
            OriginalNumbers.add(n);
        }
    }

    public void Update(Canvas canvas) {
        if (GameView.FINISH)
            return;
        Paint pt = new Paint();
        pt.setColor(Color.parseColor(ref.number_color));
        pt.setFakeBoldText(true);
        pt.setTextSize(TILE_WIDTH * 3);

        for (int i = 0; i < NumbersXY.length; i++) {
            if (NumbersXY[i][2] == 0) {
                canvas.drawText(OriginalNumbers.get(i) + "", NumbersXY[i][0] *
                        TILE_WIDTH, NumbersXY[i][1] * TILE_WIDTH, pt);
            }
        }
        NumberCollision();
        GameFinish();
    }

    private void NumberCollision() {
        for (int i = 0; i < NumbersXY.length; i++) {
            int NumX = NumbersXY[i][0] * TILE_WIDTH;
            int NumY = NumbersXY[i][1] * TILE_WIDTH;
            if (game.getLastPlayerX() > NumX - TILE_WIDTH * 2 && game.getLastPlayerX() < NumX + TILE_WIDTH * 2.5 &&
                    game.getLastPlayerY() > NumY - TILE_WIDTH * 2 && game.getLastPlayerY() < NumY + TILE_WIDTH * 2.5) {
                if (!taken[OriginalNumbers.get(i)]) {
                    NumbersXY[i][2] = 1;
                    takenNumber.add(OriginalNumbers.get(i));
                    taken[OriginalNumbers.get(i)] = true;
                    SoundManager.take_number.start();
                }
            }
        }
    }

    private void GameFinish() {

        for (int i = 0; i < NumbersXY.length; i++) {
            if (NumbersXY[i][2] == 0)
                return;
        }

        boolean wrongAnswer = false;

        for (int i = 1; i < takenNumber.size(); i++) {
            // Ascending
            if (order == 1)
                if (takenNumber.get(i - 1) > takenNumber.get(i)) {
                    wrongAnswer = true;
                    break;
                } else {
                    if (takenNumber.get(i - 1) < takenNumber.get(i)) {
                        wrongAnswer = true;
                        break;
                    }
                }
        }

        GameView.FINISH = true;

        if (wrongAnswer) {
            String ans = "";
            Collections.sort(takenNumber);
            for (int i = 0; i < takenNumber.size(); i++) {
                ans += takenNumber.get(i) + ((i + 1 != takenNumber.size()) ? " - " : "");
            }
            Util.OpenDialog(ActivityLevel.ActivityLevel, "أخطأت.. الإجابة الصحيحة هى " + ans, false);
            SoundManager.loss.start();
            SoundManager.stopAllBackground();
        } else {
            Util.OpenDialog(ActivityLevel.ActivityLevel, "أحسنت أجابة صحيحة ", false);
            SoundManager.win.start();
            SoundManager.stopAllBackground();
        }

        GameView.levelCounter++;

        if (GameView.levelCounter == 5) {
            LevelSelector.SELECTED_LEVEL = 2;
            GameView.levelCounter = 0;
        } else {
            LevelSelector.SELECTED_LEVEL = 1;
        }
        game.setWorkOnceForLvl(false);
    }

}
