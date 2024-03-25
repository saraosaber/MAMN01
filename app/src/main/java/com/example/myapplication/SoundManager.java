package com.example.myapplication;
import android.app.Notification;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Vibrator;
import android.media.AudioAttributes;
import android.media.AudioManager;

import androidx.appcompat.app.AppCompatActivity;

public class SoundManager extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    static SoundPool soundPool;
    private Vibrator vibrator;
    private int running;
    private int bird;
    private int snake;
    private int jump;
    private int duck;
    private int crash;

    public SoundManager(Context context){
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        soundPool = new SoundPool.Builder().setAudioAttributes(attributes).build();
        running = soundPool.load(context, R.raw.running, 1);
        bird = soundPool.load(context, R.raw.vulture, 1);
        snake = soundPool.load(context, R.raw.snake, 1);
        jump = soundPool.load(context, R.raw.grunt, 1);
        duck = soundPool.load(context, R.raw.thud1, 1);
        crash = soundPool.load(context, R.raw.punch1, 1);
    }
    public void playSound(int action)
    {
        switch (action) {
            case 0: //jump
                // This play function
                // takes five parameter
                // leftVolume, rightVolume,
                // priority, loop and rate.
                soundPool.play(jump, 1, 1, 0, 0, 1);
                break;
            case 1: //duck
                soundPool.play(duck, 1, 1, 0, 0, 1);
                break;
            case 2: //crash
                soundPool.play(crash,1, 1, 0, 0, 1);
                break;
            case 3: //bird
                soundPool.play(bird,1, 1, 0, 0, 1);
                break;
            case 4: //snake
                soundPool.play(snake,1, 1, 0, 0, 1);
                break;
            case 5: //running
                soundPool.play(running,1, 1, 0, 0, 1);
                break;
    }
}
}
