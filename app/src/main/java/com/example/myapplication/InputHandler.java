package com.example.myapplication;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class InputHandler implements SensorEventListener {

    private static TiltListener tiltListener;
    private SensorManager sensorManager;
    private Sensor gyroscopeSensor;

    public InputHandler(Context context) {
        // Initialize SensorManager and Gyroscope sensor
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        if (gyroscopeSensor != null) {
            sensorManager.registerListener(this, gyroscopeSensor, SensorManager.SENSOR_DELAY_GAME);
        }
    }

    public interface TiltListener {
        void onTiltLeft();
        void onTiltRight();
    }

    public void setTiltListener(TiltListener tiltListener) {
        this.tiltListener = tiltListener;
    }

    // Gyroscope methods
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            // Handle gyroscope events
            if (event.values[1] > 1.0f) {
                // Gyroscope tilted to the right
                if (tiltListener != null) {
                    tiltListener.onTiltRight(); // You can change this to any desired action
                }
            } else if (event.values[1] < -1.0f) {
                // Gyroscope tilted to the left
                if (tiltListener != null) {
                    tiltListener.onTiltLeft(); // You can change this to any desired action
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not needed for gyroscope
    }

    public void unregisterListener() {
        sensorManager.unregisterListener(this);
    }
}
