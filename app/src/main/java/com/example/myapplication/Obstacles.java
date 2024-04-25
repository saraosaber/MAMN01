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
    private int jump = 0;
    private int duck = 1;
    private int LEFT = 10;
    private int RIGHT = 11;
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
            startObstacle(LEFT);
        } else {
            startObstacle(RIGHT);
        }
        v.vibrate(100);
    }

    private void startObstacle(int directionOfObstacle) {
        if (!Thread.currentThread().isInterrupted()) {

            if(directionOfObstacle == RIGHT) {
                System.out.println("Obstacle from right -> TILT LEFT !");
                playRandomObstacle(RIGHT);

                try {
                    TimeUnit.MILLISECONDS.sleep(1500);
                } catch (InterruptedException e) {
                    // Handle interruption
                    e.printStackTrace(); // Print the stack trace
                    Thread.currentThread().interrupt(); // Reset interrupted status
                    return; // Exit the method
                }

                if (!player.isLeft()) {
                    Thread.currentThread().interrupt(); // Hit obstacle
                }
            }

            if(directionOfObstacle == LEFT) {
                System.out.println("Obstacle from right -> TILT RIGHT !");
                playRandomObstacle(LEFT);

                try {
                    TimeUnit.MILLISECONDS.sleep(1500);
                } catch (InterruptedException e) {
                    // Handle interruption
                    e.printStackTrace(); // Print the stack trace
                    Thread.currentThread().interrupt(); // Reset interrupted status
                    return; // Exit the method
                }

                if (!player.isRight()) {
                    Thread.currentThread().interrupt(); // Hit obstacle
                }
            }
            sm.playSound(jump, 1, 1); // Play jump sound; means obstacle completed!
        }
    }

    private void playRandomObstacle(int directionOfSound) {
        Random ran = new Random();
        float volumeLeft;
        float volumeRight;

        // Decide which ear to play the sound
        if(directionOfSound == RIGHT) {
            volumeLeft = 0;
            volumeRight = 1;
        } else {
            volumeLeft = 1;
            volumeRight = 0;
        }

        // Play the sound using sound manager
        if(ran.nextBoolean()) {
            sm.playSound(snake, volumeLeft, volumeRight);
        } else {
            sm.playSound(bird, volumeLeft, volumeRight);
        }
    }
}
