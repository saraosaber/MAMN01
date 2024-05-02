package com.example.myapplication;

import android.os.Vibrator;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Obstacles implements Runnable {

    private Player player;
    private SoundManager sm;
    private final int bird = 3;
    private final int snake = 4;
    private final int jump = 0;
    private final int LEFT = 10;
    private final int RIGHT = 11;
    private int completedObstacles = 0;
    private Vibrator v;
    private Random ran;



    public Obstacles(Player player, SoundManager sm, Vibrator v){
        this.player = player;
        this.sm = sm;
        this.v=v;
        ran = new Random();
    }

    public void pause() {
    }

    public void resume() {
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
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

        if(completedObstacles < 3 ) {
            startVericalObstacle();
        } else if (randomObstacle){
            startHorizontalObstacle();
        } else {
            startVericalObstacle();
        }
        v.vibrate(100);
        completedObstacles++;
        System.out.println("completedObstacles: "+completedObstacles);
    }

    private void startVericalObstacle() {
        if (!Thread.currentThread().isInterrupted()) {
            boolean isBird = ran.nextBoolean();

            // Start bird or snake
            if (isBird) {
                sm.playSound(bird, 1, 1);
                try {
                    TimeUnit.MILLISECONDS.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                    return;
                }

                if (!player.isDucking()) {
                    Thread.currentThread().interrupt();
                }
            } else {
                sm.playSound(snake, 1, 1);
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

    private void startHorizontalObstacle() {
        if (!Thread.currentThread().isInterrupted()) {
            boolean directionIsRight = ran.nextBoolean();

            // Obstacle from right
            if(directionIsRight) {
                playRandomHorizontalObstacle(RIGHT);

                try {
                    TimeUnit.MILLISECONDS.sleep(1500);
                } catch (InterruptedException e) {
                    // Handle interruption
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                    return;
                }

                // Hit obstacle
                if (!player.isLeft()) {
                    Thread.currentThread().interrupt();
                }
            }

            // Obstacle from left
            if(!directionIsRight) {
                playRandomHorizontalObstacle(LEFT);

                try {
                    TimeUnit.MILLISECONDS.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                    return;
                }

                // Hit obstacle
                if (!player.isRight()) {
                    Thread.currentThread().interrupt();
                }
            }
            sm.playSound(jump, 1, 1); // Play jump sound; means obstacle completed!
        }
    }

    private void playRandomHorizontalObstacle(int directionOfSound) {
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
