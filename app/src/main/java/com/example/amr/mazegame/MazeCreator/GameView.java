package com.example.amr.mazegame.MazeCreator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.amr.mazegame.Activities.LevelSelector;
import com.example.amr.mazegame.Activities.MainActivity;
import com.example.amr.mazegame.Levels.LevelOne;
import com.example.amr.mazegame.Levels.LevelThree;
import com.example.amr.mazegame.Levels.LevelTwo;
import com.example.amr.mazegame.Managers.KeyManager;
import com.example.amr.mazegame.Managers.SoundManager;
import com.example.amr.mazegame.Managers.ref;

/**
 * Created by Amr on 30/01/2018.
 */
public class GameView extends View {

    public static boolean FINISH;
    public static int levelCounter;
    private final static int TILE_WIDTH = Math.min(MainActivity.MOBILE_WIDTH / 50, MainActivity.MOBILE_HEIGHT / 50);
    private float Xoffset, Yoffset, LastXOffset, LastYOffset;
    private float LastPlayerX, LastPlayerY;
    private float PlayerSpeed = 0.1f;
    private Canvas canvas;
    //----------------------------
    private boolean WorkOnceForLvl;
    private LevelOne levelOne;
    private LevelTwo levelTwo;
    private LevelThree levelThree;
    //----------------------------
    public static SoundManager mng;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        GameView.FINISH = false;
    }

    @Override
    public void onDraw(Canvas canvas) {
        // load sound
        if (mng == null) {
            mng = new SoundManager();
        }
        this.canvas = canvas;
        // Paint Maze

        for (int i = 0; i < LevelMap.ROW_TILE; i++) {
            for (int j = 0; j < LevelMap.COL_TILE; j++) {
                if (LevelMap.MAP[i][j] == 0) {
                    continue;
                }
                    Paint pt = new Paint();
                    pt.setColor(Color.parseColor(ref.wall_color));

                // in dow           in col
                canvas.drawRect(j * TILE_WIDTH, i * TILE_WIDTH, j * TILE_WIDTH + TILE_WIDTH, i * TILE_WIDTH + TILE_WIDTH, pt);
            }

            PlayerMoving();
            // Paint Player
            Paint playerPaint = new Paint();
            playerPaint.setColor(Color.parseColor(ref.player_color));
            float PlayerX = 3 * TILE_WIDTH + Xoffset;
            float PlayerY = 3 * TILE_WIDTH + Yoffset;

            if (WallCollision(PlayerX, PlayerY)) {
                PlayerX = LastPlayerX;
                PlayerY = LastPlayerY;
                Xoffset = LastXOffset;
                Yoffset = LastYOffset;
            }
            canvas.drawCircle(PlayerX, PlayerY, TILE_WIDTH, playerPaint);
            LastPlayerX = PlayerX;
            LastPlayerY = PlayerY;
            LastXOffset = Xoffset;
            LastYOffset = Yoffset;

            // Run Current LevelOne
            if (LevelSelector.SELECTED_LEVEL == 1) {
                if (!WorkOnceForLvl) {
                    WorkOnceForLvl = true;
                    levelOne = new LevelOne(this);
                }
                levelOne.Update(canvas);
            } else if (LevelSelector.SELECTED_LEVEL == 2) {
                if (!WorkOnceForLvl) {
                    WorkOnceForLvl = true;
                    levelTwo = new LevelTwo(this);
                }
                levelTwo.Update(canvas);

            } else if (LevelSelector.SELECTED_LEVEL == 3) {
                if (!WorkOnceForLvl) {
                    WorkOnceForLvl = true;
                    levelThree = new LevelThree(this);
                }
                levelThree.Update(canvas);
            }

            // render everytime
            invalidate() ;
        }
    }

    private void PlayerMoving() {
        if (KeyManager.isDown) {
            Yoffset += PlayerSpeed;
        } else if (KeyManager.isLeft) {
            Xoffset += -PlayerSpeed;
        } else if (KeyManager.isRight) {
            Xoffset += PlayerSpeed;
        } else if (KeyManager.isUp) {
            Yoffset += -PlayerSpeed;
        }
    }

    private boolean WallCollision(float PlayerX, float PlayerY) {
        for (int i = 0; i < LevelMap.ROW_TILE; i++) {
            for (int j = 0; j < LevelMap.COL_TILE; j++) {
                if (PlayerX + TILE_WIDTH / 2 > j * TILE_WIDTH && PlayerX - TILE_WIDTH / 2 < j * TILE_WIDTH + TILE_WIDTH
                        && PlayerY + TILE_WIDTH / 2 > i * TILE_WIDTH && PlayerY - TILE_WIDTH / 2 < i * TILE_WIDTH + TILE_WIDTH &&
                        LevelMap.MAP[i][j] == 1) {
                    return true;
                }
            }
        }
        return false;
    }

    public float getYoffset() {
        return Yoffset;
    }

    public void setYoffset(float yoffset) {
        Yoffset = yoffset;
    }

    public float getXoffset() {
        return Xoffset;
    }

    public void setXoffset(float xoffset) {
        Xoffset = xoffset;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    public float getLastXOffset() {
        return LastXOffset;
    }

    public void setLastXOffset(float lastXOffset) {
        LastXOffset = lastXOffset;
    }

    public float getLastYOffset() {
        return LastYOffset;
    }

    public void setLastYOffset(float lastYOffset) {
        LastYOffset = lastYOffset;
    }

    public float getLastPlayerX() {
        return LastPlayerX;
    }

    public void setLastPlayerX(float lastPlayerX) {
        LastPlayerX = lastPlayerX;
    }

    public float getLastPlayerY() {
        return LastPlayerY;
    }

    public void setLastPlayerY(float lastPlayerY) {
        LastPlayerY = lastPlayerY;
    }

    public float getPlayerSpeed() {
        return PlayerSpeed;
    }

    public void setPlayerSpeed(float playerSpeed) {
        PlayerSpeed = playerSpeed;
    }

    public static int getTileWidth() {
        return TILE_WIDTH;
    }

    public boolean isWorkOnceForLvl() {
        return WorkOnceForLvl;
    }

    public void setWorkOnceForLvl(boolean workOnceForLvl) {
        WorkOnceForLvl = workOnceForLvl;
    }
}
