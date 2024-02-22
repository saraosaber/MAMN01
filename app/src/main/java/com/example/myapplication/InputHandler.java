package com.example.myapplication;
import android.content.Context;
import android.hardware.SensorManager;

import android.view.View;

import android.view.GestureDetector;
import android.view.MotionEvent;

import androidx.annotation.NonNull;

// https://stackoverflow.com/questions/76882374/swipe-up-down-gesture-on-launcher-application
public class InputHandler implements View.OnTouchListener {

    private final GestureDetector gestureDetector;

    public InputHandler(Context context, GestureDetector gestureDetector) {
        gestureDetector = new GestureDetector(context, new GestureListener());

        this.gestureDetector = gestureDetector;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onDown(@NonNull MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float diffY = e2.getY() - e1.getY();
            float diffX = e2.getX() - e1.getX();

            if (Math.abs(diffY) > Math.abs(diffX)) {
                if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY > 0) {
                        onSwipeDown();
                    } else {
                        onSwipeUp();
                    }
                    return true;
                }
            }
            return false;
        }
    }

    public void onSwipeDown() {
        System.out.println("Down!!!");
    }

    public void onSwipeUp() {
        System.out.println("Up!!!");
    }
}