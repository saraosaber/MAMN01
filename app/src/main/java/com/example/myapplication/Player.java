package com.example.myapplication;


import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
public class Player implements Runnable, InputHandler.TiltListener  {

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private static final int RUNNING = 0;
    private static final int LEFT = 1;
    private static final int RIGHT = 2;
    private int state;
    private boolean interrupted;
    private SoundManager sm;
    private int jump = 0;
    private int duck = 1;
    private int run = 5;

    public Player(SoundManager sm) {
        state = RUNNING;
        interrupted = false;
        this.sm = sm;
    }

    @Override
    public void onTiltLeft() {
        if(!interrupted && state != LEFT) {
            System.out.println("--- Player jumps! ---");
            state = LEFT;
        }
    }

    @Override
    public void onTiltRight() {
        if(!interrupted && state != RIGHT) {
            System.out.println("--- Player ducks! ---");
            state = RIGHT;
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
        return state == LEFT;
    }

    public boolean isRight() {
        return state == RIGHT;
    }

    public String getStateString() {
        if(state == 0) return "Running";
        if(state == 1) return "LEFT";
        return "RIGHT";
    }
}
