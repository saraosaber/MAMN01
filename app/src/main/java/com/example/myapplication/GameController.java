package com.example.myapplication;
import android.os.Vibrator;
import android.view.GestureDetector;
import android.widget.RelativeLayout;
import java.util.concurrent.TimeUnit;

public class GameController implements Runnable {

    private InputHandler inputHandler;
    private Player player;
    private static Thread playerThread;
    private final SoundManager sm;
    private int start = 6;
    private int crash = 2;
    private int gameOver = 7;
    private int running = 5;
    private int music = 8;
    private Vibrator v;

    public GameController(MainActivity context, SoundManager sm, Vibrator v) {
        this.sm = sm;
        this.v = v;

        // Initialize the Player
        player = new Player(sm);

        // Initialize the InputHandler and set the SwipeListener
        // Initialize the InputHandler and set the SwipeListener
        // Initialize the GestureDetector
        GestureDetector gestureDetector = new GestureDetector(context, new InputHandler.GestureListener());
        inputHandler = new InputHandler(context, gestureDetector);
        inputHandler.setTiltListener(player);

        // Get the view where you want to detect swipe gestures
        RelativeLayout swipeAreaView = context.findViewById(R.id.swipeAreaView);

        // Set the InputHandler as the OnTouchListener for the view
        swipeAreaView.setOnTouchListener(inputHandler);
    }

    public void startGame() {
        playerThread = new Thread(player);
        Thread obstaclesThread = new Thread(new Obstacles(player, sm, v));

        // Intro sounds with correct timing
        System.out.println("--- Are you ready? ---");
        try {
            sm.playSound(start, 1, 1);
            TimeUnit.MILLISECONDS.sleep(4000);
            sm.playSound(running, 1, 1); // looped
            sm.playSound(music, 1, 1); // looped
            TimeUnit.MILLISECONDS.sleep(2000);
            System.out.println("Start!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        sm.adjustVolume(0.1f, 0.1f);
        sm.playMusic();
        playerThread.start();
        obstaclesThread.start();

        try {
            obstaclesThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        playerThread.interrupt();
        player.pause();

        sm.stopMusic();
        System.out.println("Game over...");
        long[] pattern = {1500, 20, 400, 1600, 500, 200};
        v.vibrate(pattern, -1);
        sm.playSound(crash, 1, 1);
        sm.playSound(gameOver, 1, 1);
    }

    public void stop() {
        // Stop player and obstacle threads if needed
    }

    @Override
    public void run() {
        startGame();
    }
}