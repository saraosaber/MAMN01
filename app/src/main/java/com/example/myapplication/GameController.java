package com.example.myapplication;
import android.view.GestureDetector;
import android.widget.RelativeLayout;
import java.util.concurrent.TimeUnit;

public class GameController implements Runnable {

    private InputHandler inputHandler;
    private Player player;
    private static Thread playerThread;

    public GameController(MainActivity context) {

          // Initialize the Player
          player = new Player();

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
        Thread obstaclesThread = new Thread(new Obstacles(player));

        //remove later (for  testing)
        System.out.println("--- Are you ready? ---");
        System.out.println("Countdown");
        try { // remove later
            TimeUnit.MILLISECONDS.sleep(1000);
            System.out.println("3");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try { // remove later
            TimeUnit.MILLISECONDS.sleep(1000);
            System.out.println("2");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try { // remove later
            TimeUnit.MILLISECONDS.sleep(1000);
            System.out.println("1");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Start!");

        // TO-DO skapa metod för att  köra intro

        playerThread.start();
        obstaclesThread.start();

        try {
            obstaclesThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        playerThread.interrupt();

        System.out.println("Game over...");
    }

    public void stop() {
        // Stop player and obstacle threads if needed
    }

    @Override
    public void run() {
        startGame();
    }
}