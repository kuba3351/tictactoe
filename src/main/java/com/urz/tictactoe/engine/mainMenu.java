package com.urz.tictactoe.engine;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.urz.tictactoe.engine.Game;
import com.urz.tictactoe.kkoikrzyyk.R;

public class mainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        final Button startGame = (Button)findViewById(R.id.startGame);
        startGame.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    Intent intent = new Intent(getBaseContext(), Game.class);
                    Bundle b = new Bundle();
                    RadioButton circleComputer = (RadioButton)findViewById(R.id.computerCircle);
                    RadioButton crossComputer = (RadioButton)findViewById(R.id.computerCross);
                    b.putBoolean("crossAI", crossComputer.isChecked());
                    b.putBoolean("circleAI", circleComputer.isChecked());
                    intent.putExtras(b);
                    startActivity(intent);
                }
                return true;
            }
        });
        RadioGroup circleGroup = (RadioGroup)findViewById(R.id.circleGroup);
        RadioGroup crossGroup = (RadioGroup)findViewById(R.id.crossGroup);
        circleGroup.check(R.id.computerCircle);
        crossGroup.check(R.id.humanCross);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }
}
