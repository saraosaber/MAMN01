package com.example.myapplication;


import android.os.Vibrator;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
public class Player implements Runnable, InputHandler.InputListener  {

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private static final int RUNNING = 0;
    private static final int LEFT = 1;
    private static final int RIGHT = 2;
    private static final int UP = 3;
    private static final int DOWN = 4;
    private static final int NO_TILT = 5;
    private final int jump = 0;
    private final int duck = 1;

    private int stateVertical;
    private int stateHorizontal;
    private SoundManager sm;
    private Vibrator v;
    private boolean restartMode = false;
    private boolean readyRestart = false;
    private boolean clicked = false;


    public Player(SoundManager sm, Vibrator v) {
        stateVertical = RUNNING;
        stateHorizontal = RUNNING;
        this.sm = sm;
        this.v = v;
    }

    @Override
    public void onTiltLeft() {
        if(stateHorizontal != LEFT) {
            stateHorizontal = LEFT;
        }
    }

    @Override
    public void onTiltRight() {
        if(stateHorizontal != RIGHT) {
            stateHorizontal = RIGHT;
        }
    }

    @Override
    public void onSwipeUp() {
        // Player reacts to swipe up event
        if(stateVertical != UP && !restartMode) {
            v.vibrate(20);
            stateVertical = UP;
            sm.playSound(jump, 1, 1);
            scheduler.schedule(() -> {
                stateVertical = 0;
            },2, TimeUnit.SECONDS);
        }
    }

    @Override
    public void onClick() {
        clicked = true;
        if(restartMode) {
            readyRestart = true;
        }
    }

    @Override
    public void onTiltNone() {
        if(stateHorizontal != NO_TILT) {
            stateHorizontal = NO_TILT;
        }
    }

    @Override
    public void onSwipeDown() {
        // Player reacts to swipe down event
        if(stateVertical != DOWN) {
            v.vibrate(20);
            stateVertical = DOWN;
            sm.playSound(duck,1, 1);
            scheduler.schedule(() -> {
                stateVertical = 0;
            },2, TimeUnit.SECONDS);
        }
    }

    public void pause() {
        restartMode = true;
    }

    public boolean isReadyRestart(){
        return readyRestart;
    }
    public void setReadyRestart(boolean condition) {
        readyRestart = condition;
    }

    public void resume() {
        restartMode = false;
        readyRestart = false;
    }

    @Override
    public void run() {
        // Thread started
    }

    public boolean isLeft() {
        return stateHorizontal == LEFT;
    }

    public boolean isRight() {
        return stateHorizontal == RIGHT;
    }

    public boolean isJumping() {
        return stateVertical == UP;
    }
    public boolean hasClicked() { return clicked; }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

    public boolean isDucking() {
        return stateVertical == DOWN;
    }
}
