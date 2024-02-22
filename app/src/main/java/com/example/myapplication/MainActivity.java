package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.GestureDetector;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    private GestureDetector gestureDetector;
    private InputHandler inputHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Assuming your SwipeAreaView is defined in activity_main.xml
        RelativeLayout swipeAreaView = findViewById(R.id.swipeAreaView);

        gestureDetector = new GestureDetector(this, new GestureListener());
        inputHandler = new InputHandler(this, gestureDetector);

        // Set the InputHandler as the OnTouchListener for SwipeAreaView
        swipeAreaView.setOnTouchListener(inputHandler);
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        // You can override gesture-related methods here if needed
    }
}