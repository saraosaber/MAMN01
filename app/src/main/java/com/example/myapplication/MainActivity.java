package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.os.PowerManager;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    GameController gameController;
    private SoundManager sm;
    private Vibrator v;
    private Thread gameControllerThread;
    private PowerManager.WakeLock wakeLock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        v = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        // Keep the screen turned on!
        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        if (powerManager != null) {
            wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK |
                    PowerManager.ACQUIRE_CAUSES_WAKEUP |
                    PowerManager.ON_AFTER_RELEASE, "YourApp:WakeLockTag");
            wakeLock.acquire();
        }

        // Initialize SoundManager
        sm = new SoundManager(this);

        // Initialize GameController
        gameController = new GameController(this, sm, v);
        gameControllerThread = new Thread(gameController);
        try {
            TimeUnit.MILLISECONDS.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        gameControllerThread.start();
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