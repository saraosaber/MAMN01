package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.GestureDetector;
import android.widget.RelativeLayout;

import com.example.myapplication.Player;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        // Initialize GameController with the Player and Obstacle instances
        SoundManager sm = new SoundManager(this);
        Thread gameControllerThread = new Thread(new GameController(this, sm, vibrator));
        try { // dont remove, this allows all initialisation to complete before starting. otherwise things like sound manager wont work
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