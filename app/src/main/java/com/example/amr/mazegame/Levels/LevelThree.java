package com.example.amr.mazegame.Levels;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.widget.TextView;

import com.example.amr.mazegame.Activities.ActivityLevel;
import com.example.amr.mazegame.Activities.LevelSelector;
import com.example.amr.mazegame.Managers.SoundManager;
import com.example.amr.mazegame.MazeCreator.GameView;
import com.example.amr.mazegame.R;
import com.example.amr.mazegame.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/**
 * Created by Amr on 03/02/2018.
 */
public class LevelThree {

    int BAN_TYPE = 0, APPLE_TYPE = 1, ORANGE_TYPE = 2;

    //       Question          answer         type

    private int QuesionAndAnswer[][] = {
            {R.drawable.q1, R.drawable.o5, ORANGE_TYPE},
            {R.drawable.q2, R.drawable.o4, ORANGE_TYPE},
            {R.drawable.q3, R.drawable.a2, APPLE_TYPE},
            {R.drawable.q4, R.drawable.b1, BAN_TYPE}
    };

    // answers locations
    private int NumbersXY[][] = {
            {34, 1},
            {26, 20},
            {1, 25},
    };

    private ArrayList<Integer> AppleAnswer = new ArrayList<>(Arrays.asList(R.drawable.a1, R.drawable.a2, R.drawable.a3, R.drawable.a4, R.drawable.a5));
    private ArrayList<Integer> BanAnswer = new ArrayList<>(Arrays.asList(R.drawable.b1, R.drawable.b2, R.drawable.b3, R.drawable.b4, R.drawable.b5));
    private ArrayList<Integer> OrangeAnswer = new ArrayList<>(Arrays.asList(R.drawable.o1, R.drawable.o2, R.drawable.o3, R.drawable.o4, R.drawable.o5));


    private int QuesionIndex;
    private int TILE_WIDTH;
    private GameView game;
    private ArrayList<Bitmap> ChoicesBitmap;
    private ArrayList<Integer> Choices;


    public LevelThree(GameView game) {
        Choices = new ArrayList<>();
        this.game = game;
        this.TILE_WIDTH = game.getTileWidth();
        initializing();
        initializeAssets();
    }
    private void initializeAssets() {
        // text
        TextView levelName = (TextView) ActivityLevel.ActivityLevel.findViewById(R.id.levelName);
        levelName.setText("المرحلة 3 صعب المستوى رقم"+" " +(GameView.levelCounter+1) );
        // sound
        SoundManager.stopAllBackground();
        SoundManager.background3 =  SoundManager.Loader(true, 60, R.raw.background3);
        SoundManager.background3.start();
    }
    private void initializing() {
        setQuestion();
        setChoices();
        LoadAnswerImages();
    }

    private void LoadAnswerImages() {
        ChoicesBitmap = new ArrayList<>();
        for (int i = 0; i < Choices.size(); i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(ActivityLevel.ActivityLevel.getResources(), Choices.get(i));
            bitmap = Bitmap.createScaledBitmap(bitmap, TILE_WIDTH * 4, TILE_WIDTH * 4, false);
            ChoicesBitmap.add(bitmap);
        }

    }

    private void selectChoices(ArrayList<Integer> answerList) {
        Collections.shuffle(answerList);
        // add right answer
        for (int i = 0; i < answerList.size(); i++) {
            if (QuesionAndAnswer[QuesionIndex][1] == answerList.get(i)) {
                Choices.add(answerList.get(i));
                break;
            }
        }
        // add other answer
        int OtherAnswers = 2;
        for (int i = 0; i < OtherAnswers; i++) {
            if (QuesionAndAnswer[QuesionIndex][1] == answerList.get(i)) {
                OtherAnswers++;
                continue;
            }
            Choices.add(answerList.get(i));
        }
        Collections.shuffle(Choices);
    }

    private void setChoices() {
        if (QuesionAndAnswer[QuesionIndex][2] == BAN_TYPE) {
            selectChoices(BanAnswer);
        } else if (QuesionAndAnswer[QuesionIndex][2] == APPLE_TYPE) {
            selectChoices(AppleAnswer);
        } else {
            selectChoices(OrangeAnswer);
        }

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setQuestion() {
        QuesionIndex = new Random().nextInt(4) ;
        TextView Quesion = (TextView) ActivityLevel.ActivityLevel.findViewById(R.id.Quesion);
        Quesion.setText("");
        Bitmap bitmap = BitmapFactory.decodeResource(ActivityLevel.ActivityLevel.getResources(), QuesionAndAnswer[QuesionIndex][0]);
        bitmap = Bitmap.createScaledBitmap(bitmap, TILE_WIDTH * 40, TILE_WIDTH * 6, false);
        Quesion.setBackground(new BitmapDrawable(ActivityLevel.ActivityLevel.getResources(), bitmap));

    }


    public void Update(Canvas canvas) {
        if (GameView.FINISH)
            return;
        // draw Answers
        for (int i = 0; i < NumbersXY.length; i++) {
            canvas.drawBitmap(ChoicesBitmap.get(i), NumbersXY[i][0] * TILE_WIDTH, NumbersXY[i][1] * TILE_WIDTH, new Paint());
        }
        AnswersCollision();

    }


    private void AnswersCollision() {
        int chosenNumber = -1;
        for (int i = 0; i < NumbersXY.length; i++) {
            int NumX = NumbersXY[i][0] * TILE_WIDTH;
            int NumY = NumbersXY[i][1] * TILE_WIDTH;
            if (game.getLastPlayerX() > NumX - TILE_WIDTH && game.getLastPlayerX() < NumX + TILE_WIDTH * 4 &&
                    game.getLastPlayerY() > NumY - TILE_WIDTH && game.getLastPlayerY() < NumY + TILE_WIDTH * 4 ) {
                chosenNumber = Choices.get(i);
                break;
            }
        }
        if (chosenNumber == -1 )
            return;

        boolean gameEnd = (GameView.levelCounter == 4) ? true : false ;

        if (chosenNumber == QuesionAndAnswer[QuesionIndex][1]) {
            Util.OpenDialog(ActivityLevel.ActivityLevel, "أحسنت أجابة صحيحة ", gameEnd);
            SoundManager.win.start();

        }
        else {
            Util.OpenDialog(ActivityLevel.ActivityLevel, "الإجابة خاطئة", gameEnd);
            SoundManager.loss.start();
        }
        GameView.levelCounter++;
        LevelSelector.SELECTED_LEVEL = 3 ;
        GameView.FINISH = true;
    }

}
