package com.example.myapplication;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class InputHandler implements View.OnTouchListener, SensorEventListener {

    private final GestureDetector gestureDetector;
    private static InputListener inputListener;
    private SensorManager sensorManager;
    private Sensor gyroscopeSensor;

    public InputHandler(Context context, GestureDetector gestureDetector) {
        this.gestureDetector = gestureDetector;
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        if (gyroscopeSensor != null) {
            sensorManager.registerListener(this, gyroscopeSensor, SensorManager.SENSOR_DELAY_GAME);
        }
    }

    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            // Handle gyroscope events
            if (event.values[1] > 1.0f) {
                // Gyroscope tilted to the right
                if (inputListener != null) {
                    inputListener.onTiltRight();
                }
            } else if (event.values[1] < -1.0f) {
                // Gyroscope tilted to the left
                if (inputListener != null) {
                    inputListener.onTiltLeft();
                }
            }
        }
    }

    public void setInputListener(InputListener inputListener) {
        this.inputListener = inputListener;
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

            if (Math.abs(diffY) > Math.abs(diffX)) {
                if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY > 0) {
                        if (inputListener != null) {
                            inputListener.onSwipeDown();
                        }
                    } else {
                        if (inputListener != null) {
                            inputListener.onSwipeUp();
                        }
                    }
                    return true;
                }
            }
            return false;
        }
    }

    public interface InputListener {
        void onTiltLeft();
        void onTiltRight();
        void onSwipeDown();
        void onSwipeUp();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do nothing
    }
    public void unregisterListener() {
        sensorManager.unregisterListener(this);
    }
}
