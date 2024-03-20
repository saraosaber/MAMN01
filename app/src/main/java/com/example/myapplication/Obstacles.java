package com.example.myapplication;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Obstacles implements Runnable {

    private Player player;

    public Obstacles(Player player){
        this.player = player;

    }

    public void pause() {
    }

    public void resume() {
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            // Obstacle logic goes here
            System.out.println("Obstacles thread is running");

            // to-do add random waiting time between obstacles

            nextObstacle();
        }
    }

    private void nextObstacle() {
        Random ran = new Random();
        boolean randomObstacle = ran.nextBoolean();

        if(randomObstacle) {
            startBird();
        } else {
            startSnake();
        }
    }

    private void startBird() {
        for(int i = 0 ; i <= 3 ; i++) {
            System.out.println("bird"+i);
            try {
                TimeUnit.MILLISECONDS.sleep(1000);  
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Current player state: " + player.getStateString() + ". Bird: "+ i);
        }
        
        if(!player.isDucking()) {
            System.out.println("-----------DEAD!--------");
            Thread.currentThread().interrupt();
        }
    }

    private void startSnake() {
        for(int i = 0 ; i <= 3 ; i++) {
            System.out.println("snake"+i);
            try {
                TimeUnit.MILLISECONDS.sleep(1000);  
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        if(!player.isJumping()) {
            System.out.println("-----------DEAD!--------");
            Thread.currentThread().interrupt();
        }
    }
}
