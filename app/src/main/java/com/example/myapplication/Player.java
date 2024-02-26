package com.example.myapplication;

import java.util.concurrent.TimeUnit;

public class Player implements Runnable, InputHandler.SwipeListener  {

    private boolean running = true;

    public Player() {
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
            try {
                // Adjust sleep time as needed
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
