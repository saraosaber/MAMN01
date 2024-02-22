package com.example.myapplication;
import java.util.concurrent.TimeUnit;

public class GameController {

    public GameController() {
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
