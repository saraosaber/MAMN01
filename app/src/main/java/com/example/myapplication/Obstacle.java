package com.example.myapplication;

import java.util.concurrent.TimeUnit;

public class Obstacle implements Runnable {

    private boolean running = true;
    private SoundManager soundManager;
    private VibrationManager vibrationManager;

    public void pause() {
        running = false;
    }

    public void resume() {
        running = true;
    }
    public Obstacle(SoundManager sm, VibrationManager vm){
        soundManager = sm;
        vibrationManager = vm;


    }
    @Override
    public void run() {
        while (running) {
            // Obstacle logic goes here
            System.out.println("Obstacle thread is running");
            try {
                // Adjust sleep time as needed
                TimeUnit.MILLISECONDS.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
