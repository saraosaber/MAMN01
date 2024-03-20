package com.example.myapplication;

import java.util.concurrent.TimeUnit;

public class Player implements Runnable, InputHandler.SwipeListener  {

    private static final int RUNNING = 0;
    private static final int JUMPING = 1;
    private static final int DUCKING = 2;
    private int state;

    public Player() {
        state = RUNNING;
    }

    @Override
    public void onSwipeUp() {
        // Player reacts to swipe up event
        System.out.println("--- Player jumps! ---");
    }

    @Override
    public void onSwipeDown() {
        // Player reacts to swipe down event
        System.out.println("--- Player ducks! ---");
    }

    public void pause() {
    }

    public void resume() {
    }

    @Override
    public void run() {
        while (true) {
            // Player logic goes here
            System.out.println("Player thread is running");
            try {
                // Adjust sleep time as needed
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isJumping() {
        return state == JUMPING;
    }

    public boolean isDucking() {
        return state == DUCKING;
    }
}
