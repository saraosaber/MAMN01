package com.example.myapplication;

import android.os.VibrationEffect;
import android.os.Vibrator;
import static androidx.core.content.ContextCompat.getSystemService;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Obstacles implements Runnable {

    private Player player;
    private SoundManager sm;
    private Vibrator vibrator;
    private int bird = 3;
    private int snake = 4;
    private Vibrator v;


    public Obstacles(Player player, SoundManager sm, Vibrator v){
        this.player = player;
        this.sm = sm;
        this.v=v;
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

        // Set random timeout
        double randomFactor = 0.5 + ran.nextDouble() * 0.5;
        int maxMilliseconds = 1500; // Change this to your desired maximum milliseconds
        int randomMilliseconds = (int) (maxMilliseconds * randomFactor);

        try {
            TimeUnit.MILLISECONDS.sleep(randomMilliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Randomly chose and start next obstacle
        boolean randomObstacle = ran.nextBoolean();
        if(randomObstacle) {
            startBird();
            v.vibrate(100);
        } else {
            startSnake();
            v.vibrate(100);
        }
    }

    private void startBird() {
        if (Thread.currentThread().isInterrupted()) {
            System.out.println("Thread interrupted, exiting startSnake()");
            return;
        }

        System.out.println("bird");
        sm.playSound(bird, (float) 1);
        try {
            TimeUnit.MILLISECONDS.sleep(1500);
        } catch (InterruptedException e) {
            // Handle interruption
            e.printStackTrace(); // Print the stack trace
            Thread.currentThread().interrupt(); // Reset interrupted status
            return; // Exit the method
        }

        if (!player.isDucking()) {
            Thread.currentThread().interrupt();

        }
    }

    private void startSnake() {
        if (!Thread.currentThread().isInterrupted()) {

            System.out.println("snake");
            sm.playSound(snake, (float) 1);
            try {
                TimeUnit.MILLISECONDS.sleep(1500);
            } catch (InterruptedException e) {
                // Handle interruption
                e.printStackTrace(); // Print the stack trace
                Thread.currentThread().interrupt(); // Reset interrupted status
                return; // Exit the method
            }

            if (!player.isJumping()) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
