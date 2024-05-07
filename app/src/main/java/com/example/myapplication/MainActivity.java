package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.GestureDetector;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.myapplication.Player;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    GameController gameController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        // Initialize GameController with the Player and Obstacle instances
        SoundManager sm = new SoundManager(this);
        gameController = new GameController(this, sm, vibrator);
        
        //Om vi flyttar rad 29-35 till en egen metod, kallar den för "newGame" och 
        //låter en knapp (kanske lika stor som skärmen, aka man bara klickar på skärmen) på startsidan anropa den metoden så tror jag det borde funka som smort??
        //Då återstår dock problemet med att starta om för att man inte får tillbaka knappen + att den kanske ligger kvar på skärmen iofs... 
        //Så tror vi behöver en 2a activity som är spelet, och att main activity är en knapp som leder till activity 2 där spelet startar av sig själv och 
        //när man där återvänder man till main activity. 
        Thread gameControllerThread = new Thread(gameController);
        try { // dont remove, this allows all initialisation to complete before starting. otherwise things like sound manager wont work
            TimeUnit.MILLISECONDS.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        gameControllerThread.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
