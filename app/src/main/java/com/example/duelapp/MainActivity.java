package com.example.duelapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public static boolean switches[] = {true, true, true, true, true, true};;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void openGame(View v){//When playGameButton is pressed
        Intent gameActivity = new Intent(this, Game.class);
        startActivity(gameActivity);
    }

    public void openSettings(View v){//when openSettingsButton is pressed
        Intent settings = new Intent(this, Settings.class);
        startActivity(settings);
    }
}