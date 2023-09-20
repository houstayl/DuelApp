package com.example.duelapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

public class Settings extends AppCompatActivity {

    private Switch gameSwitches[] = new Switch[MainActivity.switches.length];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        System.out.println(findViewById(R.id.switchSpelling));
        gameSwitches[0] = (Switch)(findViewById(R.id.switchSpelling));
        gameSwitches[1] = (Switch)(findViewById(R.id.switchMath));
        gameSwitches[2] = (Switch)(findViewById(R.id.switchLetterIndex));
        gameSwitches[3] = (Switch)(findViewById(R.id.switchCharacterColors));
        gameSwitches[4] = (Switch)(findViewById(R.id.switchLengthOfWord));
        gameSwitches[5] = (Switch)(findViewById(R.id.switchWordColors));

        for(int i = 0; i < gameSwitches.length; i++){
            gameSwitches[i].setChecked(MainActivity.switches[i]);
        }
    }

    public void saveChanges(View v){
        if(numGames() == 0){
            Toast.makeText(getApplicationContext(), "None of the switches are turned on.", Toast.LENGTH_LONG).show();
        }
        else {
            Intent gameActivity = new Intent(this, Game.class);
            startActivity(gameActivity);
        }
    }



    public void switchSpelling(View v){
        Switch s = (Switch)v;
        MainActivity.switches[0] = s.isChecked();
    }
    public void switchMath(View v){
        Switch s = (Switch)v;
        MainActivity.switches[1] = s.isChecked();
    }
    public void switchLetterIndex(View v){
        Switch s = (Switch)v;
        MainActivity.switches[2] = s.isChecked();
    }
    public void switchCharacterColors(View v){
        Switch s = (Switch)v;
        MainActivity.switches[3] = s.isChecked();
    }
    public void switchLengthOfWord(View v){
        Switch s = (Switch)v;
        MainActivity.switches[4] = s.isChecked();
    }
    public void switchWordColors(View v){
        Switch s = (Switch)v;
        MainActivity.switches[5] = s.isChecked();
    }

    public static int numGames(){
        int num = 0;
        for(boolean b: MainActivity.switches){
            if(b){
                num++;
            }
        }
        return num;
    }
}