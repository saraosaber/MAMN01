package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.widget.RelativeLayout;

import com.example.myapplication.Player;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize GameController with the Player and Obstacle instances
        VibrationManager vm = new VibrationManager(this);
        SoundManager sm = new SoundManager(this);
        Thread gameControllerThread = new Thread(new GameController(this, sm, vm));
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