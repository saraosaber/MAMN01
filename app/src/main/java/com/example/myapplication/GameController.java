package com.example.myapplication;
import android.view.GestureDetector;
import android.widget.RelativeLayout;
import java.util.concurrent.TimeUnit;

public class GameController implements Runnable {

    private InputHandler inputHandler;
    private Player player;
    private static Thread playerThread;
    private final SoundManager sm;
    private final VibrationManager vm;
    private int start = 6;
    private int crash = 2;
    private int gameOver = 7;
    private int running = 5;
    private int music = 8;

    public GameController(MainActivity context, SoundManager sm, VibrationManager vm) {
          this.sm = sm;
          this.vm = vm;

          // Initialize the Player
          player = new Player(sm);

          // Initialize the InputHandler and set the SwipeListener
          // Initialize the GestureDetector
          GestureDetector gestureDetector = new GestureDetector(context, new InputHandler.GestureListener());
          inputHandler = new InputHandler(context, gestureDetector);
          inputHandler.setSwipeListener(player);

          // Get the view where you want to detect swipe gestures
          RelativeLayout swipeAreaView = context.findViewById(R.id.swipeAreaView);

          // Set the InputHandler as the OnTouchListener for the view
          swipeAreaView.setOnTouchListener(inputHandler);
    }

    public void startGame() {

        playerThread = new Thread(player);
        Thread obstaclesThread = new Thread(new Obstacles(player, sm, vm));

        // Intro sounds with correct timing
        System.out.println("--- Are you ready? ---");
        try {
            sm.playSound(start, 1);
            TimeUnit.MILLISECONDS.sleep(4000);
            sm.playSound(running, 1); // looped
            sm.playSound(music, 1); // looped
            TimeUnit.MILLISECONDS.sleep(2000);
            System.out.println("Start!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        playerThread.start();
        obstaclesThread.start();

        try {
            obstaclesThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        playerThread.interrupt();
        player.pause();

        System.out.println("Game over...");
        sm.playSound(crash, 1);
        sm.playSound(gameOver, 1);
    }

    public void stop() {
        // Stop player and obstacle threads if needed
    }

    @Override
    public void run() {
        startGame();
    }
}