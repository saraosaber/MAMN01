package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.media.SoundPool;
import android.os.Bundle;
import android.view.GestureDetector;
import android.widget.RelativeLayout;

import com.example.myapplication.Player;
import com.example.myapplication.Obstacle;


public class MainActivity extends AppCompatActivity {

    private InputHandler inputHandler;
    private Player player;
    private SoundManager soundManager;
    private Obstacle obstacle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Initialize the GestureDetector
        GestureDetector gestureDetector = new GestureDetector(this, new InputHandler.GestureListener());

        // Initialize the Player
        soundManager = new SoundManager(this);
        player = new Player(soundManager);

        // Initialize the InputHandler and set the SwipeListener
        inputHandler = new InputHandler(this, gestureDetector);
        inputHandler.setSwipeListener(player);


        // Get the view where you want to detect swipe gestures
        RelativeLayout swipeAreaView = findViewById(R.id.swipeAreaView);

        // Set the InputHandler as the OnTouchListener for the view
        swipeAreaView.setOnTouchListener(inputHandler);

        // Initialize GameController with the Player and Obstacle instances
        GameController gameController = new GameController(player, obstacle);
        gameController.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}