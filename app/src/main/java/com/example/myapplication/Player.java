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

    public Player() {
        state = RUNNING;
        interrupted = false;
    }

    @Override
    public void onSwipeUp() {
        // Player reacts to swipe up event
        System.out.println("--- Player jumps! ---");
        if(!interrupted) {
            state = 2;
            scheduler.schedule(() -> {
                state = 0;
                System.out.println("--- Player running! ---");
            }, 2, TimeUnit.SECONDS);
        }
    }

    @Override
    public void onSwipeDown() {
        // Player reacts to swipe down event
        System.out.println("--- Player ducks! ---");
        if(!interrupted) {
            state = 2;
            scheduler.schedule(() -> {
                state = 0;
                System.out.println("--- Player running! ---");
            }, 2, TimeUnit.SECONDS);
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
