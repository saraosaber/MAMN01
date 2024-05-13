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

        System.out.println(randomMilliseconds);
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


/*


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
    private int completedObstacles;
    private int highScore = 0;
    private boolean firstGame = true;
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
        completedObstacles = 0;
        while (!Thread.currentThread().isInterrupted()) {
            // Obstacle logic goes here
            if(firstGame) {
                startWalkthrough();
            }
            if(!firstGame) {
                nextObstacle();
                completedObstacles++;
            }
        }
    }

    private void startWalkthrough() {
        // 1 Welcome, click to start
        sm.playSound(14, 1, 1);
        while(!player.hasClicked()) {
            try {
                TimeUnit.MILLISECONDS.sleep(400);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        v.vibrate(20);
        player.setClicked(false);

        // wait
        try {
            TimeUnit.MILLISECONDS.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 3 Snake swipe up
        sm.playSound(4, 1, 1);
        sm.playSound(12, 1, 1);
        while(!player.isJumping()) {
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // wait
        try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 3 Snake swipe down
        sm.playSound(3, 1, 1);
        sm.playSound(9, 1, 1);
        while(!player.isDucking()) {
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // wait
        try {
            TimeUnit.MILLISECONDS.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Instruction to tilt
        sm.playSound(13, 1, 1);
        try {
            TimeUnit.MILLISECONDS.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        boolean completedTilt = horizontalWalkthrough(RIGHT);

        while (!completedTilt) {
            completedTilt = horizontalWalkthrough(RIGHT); // Try again
            try {
                TimeUnit.MILLISECONDS.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //wait
        sm.playSound(38, 1, 1);
        try {
            TimeUnit.MILLISECONDS.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        completedTilt = false;
        while (!completedTilt) {
            completedTilt = horizontalWalkthrough(LEFT);
            try {
                TimeUnit.MILLISECONDS.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // wait
        try {
            TimeUnit.MILLISECONDS.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        firstGame = false;
    }

    private void nextObstacle() {
        // Set random timeout based on completed obstacles
        double randomFactor;
        if (completedObstacles < 5) {
            randomFactor = 0.8 + ran.nextDouble() * 0.2;
        } else if (completedObstacles < 7) {
            randomFactor = 0.7 + ran.nextDouble() * 0.2;
        } else if(completedObstacles < 10) {
            randomFactor = 0.6 + ran.nextDouble() * 0.2;
        } else if(completedObstacles < 15) {
            randomFactor = 0.5 + ran.nextDouble() * 0.2;
        } else if(completedObstacles < 20) {
            randomFactor = 0.4 + ran.nextDouble() * 0.2;
        } else {
            randomFactor = 0.3 + ran.nextDouble() * 0.2;
        }

        // Calculate random milliseconds based on the random factor
        int maxMilliseconds = 4000; // Change this to your desired maximum milliseconds
        int randomMilliseconds = (int) (maxMilliseconds * randomFactor);

        try {
            TimeUnit.MILLISECONDS.sleep(randomMilliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
            return;
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
                    firstGame = false;
                    Thread.currentThread().interrupt();
                }
            } else {
                sm.playSound(snake, 1, 1);
                try {
                    TimeUnit.MILLISECONDS.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                    return; // Exit the method
                }

                if (!player.isJumping()) {
                    firstGame = false;
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
                playHorizontalObstacle(RIGHT);

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
                    firstGame = false;
                    Thread.currentThread().interrupt();
                }
                sm.playSound(16, (float) 0.3, (float) 0.3);
                v.vibrate(20);
            }

            // Obstacle from left
            if(!directionIsRight) {
                playHorizontalObstacle(LEFT);

                try {
                    TimeUnit.MILLISECONDS.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                    return;
                }

                // Hit obstacle
                if (!player.isRight()) {
                    firstGame = false;
                    Thread.currentThread().interrupt();
                }
                sm.playSound(16, (float) 0.3, (float) 0.3);
                v.vibrate(20);
            }
        }
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

    private void playHorizontalObstacle(int directionOfSound) {
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
        sm.playSound(15, volumeLeft, volumeRight);
    }

    public boolean horizontalWalkthrough(int direction) {
        playHorizontalObstacle(direction);
        // Give user time to tilt
        try {
            TimeUnit.MILLISECONDS.sleep(1500); // Adjust this delay as needed
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Check if user tilted in the correct direction
        if (direction == RIGHT && player.isLeft()) {
            sm.playSound(16, (float) 0.3, (float) 0.3);
            v.vibrate(20);
            return true;
        } else if (direction == LEFT && player.isRight()) {
            sm.playSound(16, (float) 0.3, (float) 0.3);
            v.vibrate(20);
            return true;
        } else {
            sm.playSound(2, 1, 1); // crash
            return false;
        }
    }
}

* */