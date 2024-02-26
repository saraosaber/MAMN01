package com.example.myapplication;

// https://stackoverflow.com/questions/76882374/swipe-up-down-gesture-on-launcher-application

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class InputHandler implements View.OnTouchListener {

    private final GestureDetector gestureDetector;
    private static SwipeListener swipeListener;

    public InputHandler(Context context, GestureDetector gestureDetector) {
        this.gestureDetector = gestureDetector;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    public interface SwipeListener {
        void onSwipeUp();
        void onSwipeDown();
    }

    public void setSwipeListener(SwipeListener swipeListener) {
        this.swipeListener = swipeListener;
    }

    static class GestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float diffY = e2.getY() - e1.getY();
            float diffX = e2.getX() - e1.getX();

            if (Math.abs(diffY) > Math.abs(diffX)) {
                if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY > 0) {
                        if (swipeListener != null) {
                            swipeListener.onSwipeDown();
                        }
                    } else {
                        if (swipeListener != null) {
                            swipeListener.onSwipeUp();
                        }
                    }
                    return true;
                }
            }
            return false;
        }
    }
}
