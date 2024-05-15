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
    private int niceTry;
    private int birdIns;
    private int restartIns;
    private int runIns;
    private int runn;
    private int snakeIns;
    private int tiltIns;
    private int welcomeIns;
    private int growl;
    private int swoosh;

    private int zero;
    private int one;
    private int two;
    private int three;
    private int four;
    private int five;
    private int six;
    private int seven;
    private int eight;
    private int nine;
    private int ten;
    private int eleven;
    private int twelve;
    private int thirteen;
    private int fourteen;
    private int fifteen;
    private int sixteen;
    private int seventeen;
    private int eighteen;
    private int nineteen;
    private int twenty;

    private int funny_one;
    private int funny_two;
    private int funny_three;
    private int funny_four;
    private int funny_five;
    private int funny_six;
    private int funny_seven;
    private int funny_eight;
    private int funny_nine;
    private int funny_ten;

    private int completed;
    private int highscore;
    private int instr;
    private int nicetry;
    private int obstacles;
    private int tryagain;

    /*
    *
    *   CREDITS TO:     Narakeet - for voice over material
    *                   Pixabay - for free sound effects
    *
    */


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
        niceTry = soundPool.load(context, R.raw.nicetry, 1);

        birdIns = soundPool.load(context, R.raw.bird2, 1);
        restartIns = soundPool.load(context, R.raw.restart, 1);
        runIns = soundPool.load(context, R.raw.run, 1);
        runn = soundPool.load(context, R.raw.runn, 1);
        snakeIns = soundPool.load(context, R.raw.snake2, 1);
        tiltIns = soundPool.load(context, R.raw.tilt2, 1);
        welcomeIns = soundPool.load(context, R.raw.welcome, 1);
        growl = soundPool.load(context, R.raw.growl, 1);
        swoosh = soundPool.load(context, R.raw.swosh, 1);

        zero = soundPool.load(context, R.raw.zero, 1);
        one = soundPool.load(context, R.raw.one, 1);
        two = soundPool.load(context, R.raw.two, 1);
        three = soundPool.load(context, R.raw.three, 1);
        four = soundPool.load(context, R.raw.four, 1);
        five = soundPool.load(context, R.raw.five, 1);
        six = soundPool.load(context, R.raw.six, 1);
        seven = soundPool.load(context, R.raw.seven, 1);
        eight = soundPool.load(context, R.raw.eight, 1);
        nine = soundPool.load(context, R.raw.nine, 1);
        ten = soundPool.load(context, R.raw.ten, 1);
        eleven = soundPool.load(context, R.raw.eleven, 1);
        twelve = soundPool.load(context, R.raw.twelve, 1);
        thirteen = soundPool.load(context, R.raw.thirteen, 1);
        fourteen = soundPool.load(context, R.raw.fourteen, 1);
        fifteen = soundPool.load(context, R.raw.fifteen, 1);
        sixteen = soundPool.load(context, R.raw.sixteen, 1);
        seventeen = soundPool.load(context, R.raw.seventeen, 1);
        eighteen = soundPool.load(context, R.raw.eighteen, 1);
        nineteen = soundPool.load(context, R.raw.nineteen, 1);
        twenty = soundPool.load(context, R.raw.twenty, 1);

        completed = soundPool.load(context, R.raw.completed, 1);
        funny_one = soundPool.load(context, R.raw.funny_one, 1);
        funny_two = soundPool.load(context, R.raw.funny_two, 1);
        funny_three = soundPool.load(context, R.raw.funny_three, 1);
        funny_four = soundPool.load(context, R.raw.funny_four, 1);
        funny_five = soundPool.load(context, R.raw.funny_five, 1);
        funny_six = soundPool.load(context, R.raw.funny_six, 1);
        funny_seven = soundPool.load(context, R.raw.funny_seven, 1);
        funny_eight = soundPool.load(context, R.raw.funny_eight, 1);
        funny_nine = soundPool.load(context, R.raw.funny_nine, 1);
        funny_ten = soundPool.load(context, R.raw.funny_ten, 1);
        highscore = soundPool.load(context, R.raw.highscore, 1);
        instr = soundPool.load(context, R.raw.instr, 1);
        nicetry = soundPool.load(context, R.raw.nicetry, 1);
        obstacles = soundPool.load(context, R.raw.obstacles, 1);
        tryagain = soundPool.load(context, R.raw.tryagain, 1);


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
                soundPool.play(start, 1, 1, 3, 0, 1);
                break;
            case 7: //start sound
                soundPool.play(gameOver,volumeLeft, volumeRight, 3, 0, 1);
                break;
            case 8:
                soundPool.play(niceTry, volumeLeft, volumeLeft, 1, 0, 1);
                break;
            case 9:
                soundPool.play(birdIns, 1, 1, 1, 0, 1);
                break;
            case 10:
                soundPool.play(restartIns, 1, 1, 1, 0, 1);
                break;
            case 11:
                soundPool.play(runIns,1, 1, 1, 0, 1);
                break;
            case 12:
                soundPool.play(snakeIns,1, 1, 1, 0, 1);
                break;
            case 13:
                soundPool.play(tiltIns,1, 1, 1, 0, 1);
                break;
            case 14:
                soundPool.play(welcomeIns,1, 1, 1, 0, 1);
                break;
            case 15:
                soundPool.play(growl, volumeLeft, volumeRight, 1, 0, 1);
                break;
            case 16:
                soundPool.play(swoosh, volumeLeft, volumeRight, 1, 0, 1);
                break;

            case 28:
                soundPool.play(completed, volumeLeft, volumeRight, 1, 0, 1);
                break;

            case 29:
                soundPool.play(funny_one, volumeLeft, volumeRight, 1, 0, 1);
                break;

            case 30:
                soundPool.play(funny_two, volumeLeft, volumeRight, 1, 0, 1);
                break;

            case 31:
                soundPool.play(funny_three, volumeLeft, volumeRight, 1, 0, 1);
                break;

            case 32:
                soundPool.play(funny_four, volumeLeft, volumeRight, 1, 0, 1);
                break;

            case 33:
                soundPool.play(funny_five, volumeLeft, volumeRight, 1, 0, 1);
                break;

            case 34:
                soundPool.play(funny_six, volumeLeft, volumeRight, 1, 0, 1);
                break;

            case 35:
                soundPool.play(funny_seven, volumeLeft, volumeRight, 1, 0, 1);
                break;

            case 36:
                soundPool.play(funny_eight, volumeLeft, volumeRight, 1, 0, 1);
                break;

            case 37:
                soundPool.play(funny_nine, volumeLeft, volumeRight, 1, 0, 1);
                break;

            case 38:
                soundPool.play(funny_ten, volumeLeft, volumeRight, 1, 0, 1);
                break;

            case 39:
                soundPool.play(highscore, volumeLeft, volumeRight, 1, 0, 1);
                break;

            case 40:
                soundPool.play(instr, volumeLeft, volumeRight, 1, 0, 1);
                break;

            case 41:
                soundPool.play(nicetry, volumeLeft, volumeRight, 1, 0, 1);
                break;

            case 42:
                soundPool.play(obstacles, volumeLeft, volumeRight, 1, 0, 1);
                break;

            case 43:
                soundPool.play(tryagain, volumeLeft, volumeRight, 1, 0, 1);
                break;

            case 44:
                soundPool.play(runn, volumeLeft, volumeRight,1, 0, 1);
                break;

            case 100:
                soundPool.play(zero, volumeLeft, volumeRight, 1, 0, 1);
                break;

            case 101:
                soundPool.play(one, volumeLeft, volumeRight, 1, 0, 1);
                break;

            case 102:
                soundPool.play(two, volumeLeft, volumeRight, 1, 0, 1);
                break;

            case 103:
                soundPool.play(three, volumeLeft, volumeRight, 1, 0, 1);
                break;

            case 104:
                soundPool.play(four, volumeLeft, volumeRight, 1, 0, 1);
                break;

            case 105:
                soundPool.play(five, volumeLeft, volumeRight, 1, 0, 1);
                break;

            case 106:
                soundPool.play(six, volumeLeft, volumeRight, 1, 0, 1);
                break;

            case 107:
                soundPool.play(seven, volumeLeft, volumeRight, 1, 0, 1);
                break;

            case 108:
                soundPool.play(eight, volumeLeft, volumeRight, 1, 0, 1);
                break;

            case 109:
                soundPool.play(nine, volumeLeft, volumeRight, 1, 0, 1);
                break;

            case 110:
                soundPool.play(ten, volumeLeft, volumeRight, 1, 0, 1);
                break;

            case 111:
                soundPool.play(eleven, volumeLeft, volumeRight, 1, 0, 1);
                break;

            case 112:
                soundPool.play(twelve, volumeLeft, volumeRight, 1, 0, 1);
                break;

            case 113:
                soundPool.play(thirteen, volumeLeft, volumeRight, 1, 0, 1);
                break;

            case 114:
                soundPool.play(fourteen, volumeLeft, volumeRight, 1, 0, 1);
                break;

            case 115:
                soundPool.play(fifteen, volumeLeft, volumeRight, 1, 0, 1);
                break;

            case 116:
                soundPool.play(sixteen, volumeLeft, volumeRight, 1, 0, 1);
                break;

            case 117:
                soundPool.play(seventeen, volumeLeft, volumeRight, 1, 0, 1);
                break;

            case 118:
                soundPool.play(eighteen, volumeLeft, volumeRight, 1, 0, 1);
                break;

            case 119:
                soundPool.play(nineteen, volumeLeft, volumeRight, 1, 0, 1);
                break;

            case 120:
                soundPool.play(twenty, volumeLeft, volumeRight, 1, 0, 1);
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
