package com.example.myapplication;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Vibrator;
import android.media.AudioAttributes;
import android.media.AudioManager;

public class SoundManager {
    private MediaPlayer mediaPlayer;
    private SoundPool soundPool;
    private Vibrator vibrator;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        soundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        running = soundPool.load(this, R.raw.running, 1);
        bird = soundPool.load(this, R.raw.bird, 1);
        snake = soundPool.load(this, R.raw.snake, 1);
        jump = soundPool.load(this, R.raw.jump, 1);
        duck = soundPool.load(this, R.raw.duck, 1);
        crash = soundPool.load(this, R.raw.crash, 1);
    }
    public void playSound(Action action)
    {
        switch (action) {
            case action.jump:
                // This play function
                // takes five parameter
                // leftVolume, rightVolume,
                // priority, loop and rate.
                soundPool.play(jump, 1, 1, 0, 0, 1);
                break;
            case action.duck:
                soundPool.play(duck, 1, 1, 0, 0, 1);
                break;
    }
}
}
