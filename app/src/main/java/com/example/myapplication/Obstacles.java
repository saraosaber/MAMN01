package com.example.myapplication;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Obstacles implements Runnable {

    private Player player;
    private SoundManager sm;
    private VibrationManager vm;
    private int bird = 3;
    private int snake = 4;

    public Obstacles(Player player, SoundManager sm, VibrationManager vm){
        this.player = player;
        this.sm = sm;
        this.vm = vm;
    }

    public void pause() {
    }

    public void resume() {
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            // to-do add random waiting time between obstacles
            System.out.println("New obstacle!");
            try {
                TimeUnit.MILLISECONDS.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            // Obstacle logic goes here
            nextObstacle();
        }
    }

    private void nextObstacle() {
        Random ran = new Random();
        boolean randomObstacle = ran.nextBoolean();

        if(randomObstacle) {
            startBird();
        } else {
            startSnake();
        }
    }

    private void startBird() {
        if (Thread.currentThread().isInterrupted()) {
            System.out.println("Thread interrupted, exiting startSnake()");
            return;
        }
        for (int i = 1; i <= 3; i++) {
            System.out.println("bird" + (3 - i));
            sm.playSound(bird, (float) (i * 0.3));
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                // Handle interruption
                e.printStackTrace(); // Print the stack trace
                Thread.currentThread().interrupt(); // Reset interrupted status
                return; // Exit the method
            }
        }

        if (!player.isDucking()) {
            Thread.currentThread().interrupt();
        }
    }

    private void startSnake() {
        if (!Thread.currentThread().isInterrupted()) {

            for (int i = 1; i <= 3; i++) {
                System.out.println("snake" + (3 - i));
                sm.playSound(snake, (float)(i*0.3));
                try {
                    TimeUnit.MILLISECONDS.sleep(1000);
                } catch (InterruptedException e) {
                    // Handle interruption
                    e.printStackTrace(); // Print the stack trace
                    Thread.currentThread().interrupt(); // Reset interrupted status
                    return; // Exit the method
                }
            }

            if (!player.isJumping()) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
