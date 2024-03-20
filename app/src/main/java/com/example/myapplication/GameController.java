package com.example.myapplication;
import com.example.myapplication.Player;
import com.example.myapplication.Obstacles;

public class GameController {

    private static Thread player;

    public GameController(Player player) {
        this.player = player;
    }

    public void start() {
        Thread obstacles = new Obstacles();

        // TO-DO skapa metod för att  köra intro

        player.start();
        obstacles.start(player);
        /* TO-DO .await() till obstacles,
        alltså när obstacle är "färdig = interrupt", stäng av player thread, sen anropas metoden för
        gameOver() (ljud) */

        
    }

    public void stop() {
        // Stop player and obstacle threads if needed
    }
} 

/*Inspirationskod från chattis
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        Player player = new Player();
        Obstacle obstacle = new Obstacle();

        Thread playerThread = new Thread(player);
        Thread obstacleThread = new Thread(obstacle);

        playerThread.start();
        obstacleThread.start();
    }
}

class Player implements Runnable {
    @Override
    public void run() {
        while (true) {
            // Player logic goes here
            System.out.println("Player thread is running");
            try {
                TimeUnit.MILLISECONDS.sleep(100); // Adjust as needed
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Obstacle implements Runnable {
    @Override
    public void run() {
        while (true) {
            // Obstacle logic goes here
            System.out.println("Obstacle thread is running");
            try {
                TimeUnit.MILLISECONDS.sleep(200); // Adjust as needed
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}*/
