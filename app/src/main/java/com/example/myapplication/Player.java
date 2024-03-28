package com.example.myapplication;


import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
public class Player implements Runnable, InputHandler.SwipeListener  {

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private static final int RUNNING = 0;
    private static final int JUMPING = 1;
    private static final int DUCKING = 2;
    private int state;
    private boolean interrupted;
    private SoundManager sm;
    private int jump = 0;
    private int duck = 1;
    private int run = 5;

    public Player(SoundManager sm) {
        state = RUNNING;
        interrupted = false;
        this.sm = sm;
    }

    @Override
    public void onSwipeUp() {
        // Player reacts to swipe up event
        if(!interrupted && state != JUMPING) {
            System.out.println("--- Player jumps! ---");
            state = JUMPING;
            sm.playSound(jump, 1);
            new Thread(() -> {
                try {
                    Thread.sleep(2000); // Wait for 2 seconds
                    state = RUNNING;
                    System.out.println("--- Back to running (after jump/duck) ---");
                } catch (InterruptedException e){
                    e.printStackTrace();                            
                }
            }).start();
        }
    }

    @Override
    public void onSwipeDown() {
        // Player reacts to swipe down event
        if(!interrupted && state != DUCKING) {
            System.out.println("--- Player ducks! ---");
            state = 2;
            sm.playSound(duck,1);
            scheduler.schedule(() -> {
                state = 0;
                System.out.println("--- Player running! ---");
            },2, TimeUnit.SECONDS);
        }
    }

    public void pause() {
        interrupted = true;
    }

    public void resume() {
    }

    @Override
    public void run() {
        System.out.println("Player is running!");
    }

    public boolean isJumping() {
        return state == JUMPING;
    }

    public boolean isDucking() {
        return state == DUCKING;
    }

    public String getStateString() {
        if(state == 0) return "Running";
        if(state == 1) return "Jumping";
        return "Ducking";
    }
}
