package com.example.myapplication;
import com.example.myapplication.Player;
import com.example.myapplication.Obstacles;

import java.util.concurrent.TimeUnit;

public class GameController {

    private static Thread playerThread;
    private Player player;

    public GameController(Player player) {
        this.player = player;
    }

    public void start() {
        Thread playerThread = new Thread(player);
        Thread obstaclesThread = new Thread(new Obstacles(player));

        //remove later (for  testing)
        System.out.println("--- Are you ready? ---");
        System.out.println("Countdown");
        try { // remove later
            TimeUnit.MILLISECONDS.sleep(1000);
            System.out.println("3");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try { // remove later
            TimeUnit.MILLISECONDS.sleep(1000);
            System.out.println("2");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try { // remove later
            TimeUnit.MILLISECONDS.sleep(1000);
            System.out.println("1");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Start!");

        // TO-DO skapa metod för att  köra intro

        playerThread.start();
        obstaclesThread.start();
        /* TO-DO .await() till obstacles,
        alltså när obstacle är "färdig = interrupt", stäng av player thread, sen anropas metoden för
        gameOver() (ljud) */

        
    }

    public void stop() {
        // Stop player and obstacle threads if needed
    }
}