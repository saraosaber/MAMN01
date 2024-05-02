package com.example.myapplication;


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
    private final int jump = 0;
    private final int duck = 1;

    private int stateVertical;
    private int stateHorizontal;
    private boolean interrupted;
    private SoundManager sm;

    public Player(SoundManager sm) {
        stateVertical = RUNNING;
        stateHorizontal = RUNNING;
        interrupted = false;
        this.sm = sm;
    }

    @Override
    public void onTiltLeft() {
        if(!interrupted && stateHorizontal != LEFT) {
            stateHorizontal = LEFT;
        }
    }

    @Override
    public void onTiltRight() {
        if(!interrupted && stateHorizontal != RIGHT) {
            stateHorizontal = RIGHT;
        }
    }

    @Override
    public void onSwipeUp() {
        // Player reacts to swipe up event
        if(!interrupted && stateVertical != UP) {
            stateVertical = UP;
            sm.playSound(jump, 1, 1);
            scheduler.schedule(() -> {
                stateVertical = 0;
            },2, TimeUnit.SECONDS);
        }
    }

    @Override
    public void onSwipeDown() {
        // Player reacts to swipe down event
        if(!interrupted && stateVertical != DOWN) {
            stateVertical = DOWN;
            sm.playSound(duck,1, 1);
            scheduler.schedule(() -> {
                stateVertical = 0;
            },2, TimeUnit.SECONDS);
        }
    }

    public void pause() {
        interrupted = true;
    }

    public void resume() {
    }

    @Override
    public void run() {
        System.out.println("Player is running!");
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

    public boolean isDucking() {
        return stateVertical == DOWN;
    }
}
