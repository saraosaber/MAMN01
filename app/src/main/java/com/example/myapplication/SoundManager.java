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
    private int start;
    private int snake;
    private int jump;
    private int duck;
    private int crash;
    private int gameOver;
    private int music;

    public SoundManager(Context context){
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        soundPool = new SoundPool.Builder().setAudioAttributes(attributes).setMaxStreams(3).build();
        running = soundPool.load(context, R.raw.running, 1);
        bird = soundPool.load(context, R.raw.crow, 1);
        snake = soundPool.load(context, R.raw.snake_new, 1);
        jump = soundPool.load(context, R.raw.jump_2, 1);
        duck = soundPool.load(context, R.raw.duck, 1);
        crash = soundPool.load(context, R.raw.crash, 1);
        start = soundPool.load(context, R.raw.intro, 1);
        gameOver = soundPool.load(context, R.raw.game_over, 1);
        music = soundPool.load(context, R.raw.music, 1);

        mediaPlayer = MediaPlayer.create(context, R.raw.music); // Load your audio file here
        mediaPlayer.setLooping(true); // Set the media player to loop
    }
    public void playSound(int action, float volumeLeft, float volumeRight)
    {
        switch (action) {
            case 0: //jump
                // This play function
                // takes five parameter
                // leftVolume, rightVolume,
                // priority, loop and rate.
                soundPool.play(jump, 1, 1, 3, 0, 1);
                break;
            case 1: //duck
                soundPool.play(duck, 1, 1, 3, 0, 1);
                break;
            case 2: //crash
                soundPool.play(crash,1, 1, 3, 0, 1);
                break;
            case 3: //bird
                soundPool.play(bird,volumeLeft, volumeRight, 3, 0, 1);
                break;
            case 4: //snake
                soundPool.play(snake,volumeLeft, volumeRight, 3, 0, 1);
                break;
            case 5: //running
                soundPool.play(running,1, 1, 2, 1, 1);
                break;
            case 6: //start sound
                soundPool.play(start, 1, 1, 1, 0, 1);
                break;
            case 7: //start sound
                soundPool.play(gameOver,volumeLeft, volumeRight, 3, 0, 1);
                break;
            case 8: //start sound, TO-DO move this to media player instead of soundPool‹
                soundPool.play(music,(float)0.2, (float)0.2, 1, 1, 1);
                break;
        }
    }
    public void playMusic() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start(); // Start playing the music
        }
    }

    public void stopMusic() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause(); // Pause the music
        }
    }

    public void adjustVolume(float leftVolume, float rightVolume) {
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(leftVolume, rightVolume); // Adjust the volume
        }
    }
}
