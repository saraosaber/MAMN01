package com.example.myapplication;

import android.os.Vibrator;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Obstacles implements Runnable {

    private Player player;
    private SoundManager sm;
    private final int LEFT = 10;
    private final int RIGHT = 11;
    private int completedObstacles;
    private int highScore = 0;
    private boolean firstGame = true;
    private Vibrator v;
    private Random ran;

    public Obstacles(Player player, SoundManager sm, Vibrator v) {
        this.player = player;
        this.sm = sm;
        this.v = v;
        this.ran = new Random();
    }

    public void pause() {
    }

    public void resume() {
    }

    @Override
    public void run() {
        completedObstacles = 0;
        while (!Thread.currentThread().isInterrupted()) {
            if(firstGame) {
                startWalkthrough();
            }
            if(!firstGame) {
                nextObstacle();
                completedObstacles++;
            }
        }
    }

    private void nextObstacle() {
        int maxMilliseconds = 4000;
        int randomMilliseconds = (int) (maxMilliseconds * getDifficultyMultiplier());

        waitPlease(randomMilliseconds);

        if(completedObstacles < 3 ) {
            startVerticalObstacle();
        } else if (ran.nextBoolean()){
            startHorizontalObstacle();
        } else {
            startVerticalObstacle();
        }
    }

    private double getDifficultyMultiplier() {
        if (completedObstacles < 5) {
            return 0.8 + ran.nextDouble() * 0.2;
        } else if (completedObstacles < 7) {
            return 0.7 + ran.nextDouble() * 0.2;
        } else if (completedObstacles < 10) {
            return 0.6 + ran.nextDouble() * 0.2;
        } else if (completedObstacles < 15) {
            return 0.5 + ran.nextDouble() * 0.2;
        } else if (completedObstacles < 20) {
            return 0.4 + ran.nextDouble() * 0.2;
        } else {
            return 0.3 + ran.nextDouble() * 0.2;
        }
    }

    private void startVerticalObstacle() {
        if (!Thread.currentThread().isInterrupted()) {
            // Start bird or snake
            if (ran.nextBoolean()) {
                sm.playSound(3, 1, 1);
                waitPlease(1500);

                if (!player.isDucking()) {
                    firstGame = false;
                    Thread.currentThread().interrupt();
                }
            } else {
                sm.playSound(4, 1, 1);
                waitPlease(1500);

                if (!player.isJumping()) {
                    firstGame = false;
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    private void startHorizontalObstacle() {
        if (Thread.currentThread().isInterrupted()) {
            return;
        }

        boolean directionIsRight = ran.nextBoolean();
        int directionOfSound = directionIsRight ? RIGHT : LEFT;

        playHorizontalObstacle(directionOfSound);
        waitPlease(1500);

        boolean isHit = directionIsRight ? !player.isLeft() : !player.isRight();
        if (isHit) {
            firstGame = false;
            Thread.currentThread().interrupt();
        }

        sm.playSound(16, (float) 0.3, (float) 0.3);
        v.vibrate(20);
    }

    private void playHorizontalObstacle(int directionOfSound) {
        float volumeLeft = directionOfSound == RIGHT ? 0 : 1;
        float volumeRight = directionOfSound == RIGHT ? 1 : 0;
        sm.playSound(15, volumeLeft, volumeRight);
    }

    private void waitForClick() {
        while (!player.hasClicked()) {
            waitPlease(400);
        }
        v.vibrate(20);
        player.setClicked(false);
    }

    private void waitForJump() {
        while (!player.isJumping()) {
            waitPlease(1500);
        }
    }

    private void waitForDuck() {
        while (!player.isDucking()) {
            waitPlease(1500);
        }
    }

    private void waitForTilt(int direction) {
        playHorizontalObstacle(direction == RIGHT ? LEFT : RIGHT);

        // Wait for the tilt to the correct direction
        while ((direction == RIGHT && !player.isRight()) || (direction == LEFT && !player.isLeft())) {
            waitPlease(400);
        }
        sm.playSound(16,0.3f,0.3f);
    }

    private void startWalkthrough() {
        // 1 Welcome, click to start
        sm.playSound(14, 1, 1);
        waitForClick();
        waitPlease(4000);

        // 2 Snake swipe up
        sm.playSound(4, 1, 1);
        sm.playSound(12, 1, 1);
        waitForJump();
        waitPlease(3000);

        // 3 Snake swipe down
        sm.playSound(3, 1, 1);
        sm.playSound(9, 1, 1);
        waitForDuck();
        waitPlease(3000);

        // 4 Instruction to tilt
        sm.playSound(13, 1, 1);
        waitPlease(6000);
        waitForTilt(RIGHT);
        waitPlease(3000);
        waitForTilt(LEFT);

        waitPlease(2000);
        firstGame = false;
    }


    /* REMOVE THIS LATER IF WE DONT WANT USER TO "die" WHILE DOING A WALKTHROGUH*/
    private boolean horizontalWalkthrough(int direction) {
        playHorizontalObstacle(direction);
        try {
            TimeUnit.MILLISECONDS.sleep(1500); // Give user time to tilt
        } catch (InterruptedException e) {
            return false;
        }

        boolean success = direction == RIGHT ? player.isLeft() : player.isRight();
        if (!success) {
            sm.playSound(2, 1, 1); // Fail sound
        } else {
            sm.playSound(16, (float) 0.3, (float) 0.3); // Success sound
            v.vibrate(20); // Vibrate
        }
        return success;
    }

    public int getHighScore() {
        return highScore;
    }

    public int getCompletedObstacles() {
        return completedObstacles;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public void waitPlease(long ms) {
        try {
            TimeUnit.MILLISECONDS.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}