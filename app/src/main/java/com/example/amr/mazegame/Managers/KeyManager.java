package com.example.amr.mazegame.Managers;

import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

/**
 * Created by Amr on 01/02/2018.
 */
public class KeyManager {
    public static boolean isRight , isLeft , isUp , isDown ;
    private Button UpBtn , DownBtn , LeftBtn , RightBtn ;

    public KeyManager(Button downBtn, Button upBtn, Button leftBtn, Button rightBtn) {
        DownBtn = downBtn;
        UpBtn = upBtn;
        LeftBtn = leftBtn;
        RightBtn = rightBtn;
        PressedAndReleasedAction() ;

    }

    private void PressedAndReleasedAction(){

        UpBtn.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == android.view.MotionEvent.ACTION_DOWN) {
                    isUp = true ;
                } else if (event.getAction() == android.view.MotionEvent.ACTION_UP) {
                    isUp = false ;
                }
                return true;
            }
        });

        DownBtn.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == android.view.MotionEvent.ACTION_DOWN) {
                    isDown = true ;
                } else if (event.getAction() == android.view.MotionEvent.ACTION_UP) {
                    isDown = false ;
                }
                return true;
            }
        });

        LeftBtn.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == android.view.MotionEvent.ACTION_DOWN) {
                    isLeft = true ;
                } else if (event.getAction() == android.view.MotionEvent.ACTION_UP) {
                    isLeft = false ;
                }
                return true;
            }
        });

        RightBtn.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == android.view.MotionEvent.ACTION_DOWN) {
                    isRight = true ;
                } else if (event.getAction() == android.view.MotionEvent.ACTION_UP) {
                    isRight = false ;
                }
                return true;
            }
        });
    }






}
