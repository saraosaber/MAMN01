package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.Vibrator;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class VibrationManager extends AppCompatActivity {
    private Vibrator vibrator;

    public VibrationManager (Context context) {
        vibrator = (Vibrator) getSystemService(context.VIBRATOR_SERVICE);
    }

    public void vibrate(){
        vibrator.vibrate(50);

    }
}
