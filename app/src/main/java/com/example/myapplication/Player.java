package com.example.myapplication;

import android.content.Context;

import java.util.concurrent.TimeUnit;

public class Player implements Runnable, InputHandler.SwipeListener  {

    private boolean running = true;
    private SoundManager soundManager;
    private int jump = 0;
    private int duck = 1;
    private int run = 5;
    public Player(SoundManager sm) {
        soundManager = sm;
    }

    @Override
    public void onSwipeUp() {
        // Player reacts to swipe up event
        System.out.println("--- Player jumps! ---");
        soundManager.playSound(jump);
    }

    @Override
    public void onSwipeDown() {
        // Player reacts to swipe down event
        System.out.println("--- Player ducks! ---");

        soundManager.playSound(duck);

    }

    public void pause() {
        running = false;
    }

    public void resume() {
        running = true;
    }

    @Override
    public void run() {
        while (running) {
            // Player logic goes here
            System.out.println("Player thread is running");
            //soundManager.playSound(run);
            try {
                // Adjust sleep time as needed
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
