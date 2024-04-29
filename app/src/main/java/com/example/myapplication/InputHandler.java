package com.example.myapplication;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class InputHandler implements View.OnTouchListener {

    private final GestureDetector gestureDetector;
    private static TiltListener tiltListener;
    private SensorManager sensorManager;
    private Sensor gyroscopeSensor;

    public InputHandler(Context context, GestureDetector gestureDetector) {
        this.gestureDetector = gestureDetector;
    }

    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    public interface TiltListener {
        void onTiltLeft();
        void onTiltRight();
    }

    public void setTiltListener(TiltListener tiltListener) {
        this.tiltListener = tiltListener;
    }

    // Gyroscope methods
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

            if (Math.abs(diffX) > Math.abs(diffY)) {
                if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX > 0) {
                        if (tiltListener != null) {
                            tiltListener.onTiltRight();
                        }
                    } else {
                        if (tiltListener != null) {
                            tiltListener.onTiltLeft();
                        }
                    }
                    return true;
                }
            }
            return false;
        }
    }
}
