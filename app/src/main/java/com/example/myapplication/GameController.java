package com.example.myapplication;
import android.os.Vibrator;
import android.view.GestureDetector;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class GameController implements Runnable {

    private InputHandler inputHandler;
    private Player player;
    private Obstacles obstacles;
    private final SoundManager sm;
    private int start = 6;
    private int running = 5;
    private Vibrator v;
    private RelativeLayout swipeAreaView;
    public boolean isStarted;


    public GameController(MainActivity context, SoundManager sm, Vibrator v) {
        this.sm = sm;
        this.v = v;
        isStarted = false;

        // Initialize the Player
        player = new Player(sm, v);
        obstacles = new Obstacles(player, sm, v);

        // Initialize the InputHandler and set the SwipeListener
        GestureDetector gestureDetector = new GestureDetector(context, new InputHandler.GestureListener());
        inputHandler = new InputHandler(context, gestureDetector);
        inputHandler.setInputListener(player);

        // Get the view where you want to detect swipe gestures
        swipeAreaView = context.findViewById(R.id.swipeAreaView);

        // Set the InputHandler as the OnTouchListener for the view
        swipeAreaView.setOnTouchListener(inputHandler);
    }

    public void startGame() {
        isStarted = true;
        System.out.println("Start!!");
        player.resume();

        try {
            sm.playSound(start,  0.2f, 0.2f);
            TimeUnit.MILLISECONDS.sleep(4000);
            sm.playSound(running, 1, 1); // looped
            TimeUnit.MILLISECONDS.sleep(2000);
            System.out.println("Start!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        sm.adjustVolume(0.1f, 0.1f);
        sm.playMusic();

        Thread playerThread = new Thread(player);
        Thread obstaclesThread = new Thread(obstacles);
        playerThread.start();
        obstaclesThread.start();

        try {
            obstaclesThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        stop();
    }

    public synchronized void stop() {
        isStarted = false;
        player.setClicked(false);
        player.pause();
        sm.stopMusic();

        long[] pattern = {100, 20, 400, 1600, 50, 20};
        v.vibrate(pattern, -1);

        sm.playSound(2, 1, 1);
        sm.playSound(7, 1, 1);

        waitPlease(5000);

        // Play the sound effects
        int score = obstacles.getCompletedObstacles();
        if(score <= 1) {
            sm.playSound(8, 1, 1);
            waitPlease(5000);

        } else {
            if(score > obstacles.getHighScore()) {
                obstacles.setHighScore(score);
                waitPlease(1000);
                sm.playSound(39, 1, 1); // "New highscore..."
                waitPlease(1800);

                System.out.println(score+100);
                System.out.println(score);
                sm.playSound((100 +score), 1, 1); // "...Five..." Play the correct number according to SoundManager list
                try {
                    TimeUnit.MILLISECONDS.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                sm.playSound(42, 1, 1); // "...obstacles"
                waitPlease(2000);
            } else {
                sm.playSound((100 + score), 1, 1); // "...Five..." Play the correct number according to SoundManager list
                waitPlease(1000);

                sm.playSound(42, 1, 1); // "...obstacles"
                waitPlease(2000);

                Random ran = new Random();
                int funnySound = ran.nextInt(10) + 29; // Generates a random number between 29 and 38
                sm.playSound(funnySound, 1, 1);
                waitPlease(5000);
            }
        }
        sm.playSound(10, 1, 1);
    }

    @Override
    public void run() {
        startGame(); // First game
        player.setClicked(false);
        player.setReadyRestart(false);

        for(int i = 0; i < 20; i++) {
            while(!isStarted) {
                try {
                    synchronized (this) {
                        wait(400);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (player.isReadyRestart()) {
                    v.vibrate(20);
                    player.setClicked(false);
                    startGame();
                }
            }
        }
    }
    public void waitPlease(long ms) {
        try {
            TimeUnit.MILLISECONDS.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}