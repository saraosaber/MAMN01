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
    private Sensor accelerometer;
    private float[] gravity;
    private float[] acceleration;
    private static final float THRESHOLD_ANGLE = 0.8f; // 0.8f Good so far
    private float tiltAngle = 0.0f;
    private long lastTimestamp = 0;

    public InputHandler(Context context, GestureDetector gestureDetector) {
        this.gestureDetector = gestureDetector;
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (inputListener != null) {
                inputListener.onClick();
            }
        }
        return gestureDetector.onTouchEvent(event);
    }

    /* OLD VERSION
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            float threshold = 0.5f; // Adjust this threshold as needed


            // Handle gyroscope events
            if (event.values[1] > threshold) {
                // Gyroscope tilted to the right
                if (inputListener != null) {
                    inputListener.onTiltRight();
                }
            } else if (event.values[1] < -threshold) {
                // Gyroscope tilted to the left
                if (inputListener != null) {
                    inputListener.onTiltLeft();
                }
            }
        }
    } */


    /* Only gyroscope
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            // Handle gyroscope events
            float angularVelocityY = event.values[1];

            // Integrate angular velocity to get the tilt angle
            tiltAngle += angularVelocityY * (float) (event.timestamp - lastTimestamp) / 1e9f;

            // Limit the tilt angle to -PI/2 to PI/2
            tiltAngle = (float) Math.max(-Math.PI / 2, Math.min(tiltAngle, Math.PI / 2));
            lastTimestamp = event.timestamp;

            // Check if tilt is left, right, or none
            if (tiltAngle > THRESHOLD_ANGLE) {
                notifyTiltListener(TiltDirection.RIGHT);
            } else if (tiltAngle < -THRESHOLD_ANGLE) {
                notifyTiltListener(TiltDirection.LEFT);
            } else {
                notifyTiltListener(TiltDirection.NONE);
            }
        }
    }
    */

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            // Get the accelerometer values
            acceleration = event.values.clone();

            // Low-pass filter to reduce noise
            final float alpha = 0.8f;
            if (gravity == null) {
                gravity = event.values.clone();
            } else {
                for (int i = 0; i < 3; i++) {
                    gravity[i] = alpha * gravity[i] + (1 - alpha) * event.values[i];
                }
            }

            // Remove the gravity contribution with high-pass filter
            float[] linearAcceleration = new float[3];
            for (int i = 0; i < 3; i++) {
                linearAcceleration[i] = acceleration[i] - gravity[i];
            }

            // Calculate tilt angle from linear acceleration
            float x = linearAcceleration[0];
            float y = linearAcceleration[1];
            float z = linearAcceleration[2];
            tiltAngle = (float) Math.atan2(-x, Math.sqrt(y * y + z * z));

            // Check if tilt is left, right, or none
            if (tiltAngle > THRESHOLD_ANGLE) {
                notifyTiltListener(TiltDirection.RIGHT);
            } else if (tiltAngle < -THRESHOLD_ANGLE) {
                notifyTiltListener(TiltDirection.LEFT);
            } else {
                notifyTiltListener(TiltDirection.NONE);
            }
        }
    }


    private void notifyTiltListener(TiltDirection direction) {
        if (inputListener != null) {
            switch (direction) {
                case RIGHT:
                    inputListener.onTiltRight();
                    break;
                case LEFT:
                    inputListener.onTiltLeft();
                    break;
                case NONE:
                    inputListener.onTiltNone();
                    break;
            }
        }
    }

    enum TiltDirection {
        LEFT,
        RIGHT,
        NONE
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
        void onClick();
        void onTiltNone();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do nothing
    }
    public void unregisterListener() {
        sensorManager.unregisterListener(this);
    }
}
