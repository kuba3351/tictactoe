package com.urz.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.urz.tictactoe.kkoikrzyyk.R;

public class Game extends AppCompatActivity {

    private Symbol nextSymbol = Symbol.CIRCLE;
    private GameBoard gameBoard = GameBoard.getInstance();
    private ImageView nextMovie;
    private TextView gameLabel;
    private GameView gameView;
    private TextView restartLabel;
    private int blankFields = 9;
    private boolean crossAI;
    private boolean circleAI;
    private boolean aiInProgress = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        crossAI = getIntent().getExtras().getBoolean("crossAI");
        circleAI = getIntent().getExtras().getBoolean("circleAI");
        nextMovie = (ImageView)findViewById(R.id.nextMovie);
        gameView = new GameView(getApplicationContext());
        restartLabel = (TextView)findViewById(R.id.restartLabel);
        restartLabel.setVisibility(View.INVISIBLE);
        FrameLayout gameLayout = (FrameLayout)findViewById(R.id.gameLayout);
        gameLayout.addView(gameView);

        gameLayout.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);

        gameView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN && !aiInProgress) {
                    if (!gameBoard.isGameEnded()) {
                        float x = event.getRawX();
                        float y = event.getRawY();
                        FieldPosition fieldPosition = gameBoard.calculateFieldPositionFromXY(x, y, gameView.getWidth(), gameView.getHeight());
                        if (fieldPosition != null) {
                            GameBoard.Field field = gameBoard.getField(fieldPosition);
                            if (field.getSymbol().equals(Symbol.BLANK)) {
                                field.setSymbol(nextSymbol);
                                blankFields--;
                                gameBoard.checkIfThreeInARow();
                                if (!gameBoard.isGameEnded() && blankFields != 0) {
                                    if (nextSymbol.equals(Symbol.CIRCLE)) {
                                        nextSymbol = Symbol.CROSS;
                                        nextMovie.setImageDrawable(getDrawable(R.drawable.krzyzyk));
                                    } else if (nextSymbol.equals(Symbol.CROSS)) {
                                        nextSymbol = Symbol.CIRCLE;
                                        nextMovie.setImageDrawable(getDrawable(R.drawable.kolko));
                                    }
                                } else if(gameBoard.isGameEnded()) {
                                    gameLabel = (TextView) findViewById(R.id.gameLabel);
                                    gameLabel.setText(getString(R.string.winner));
                                    restartLabel.setVisibility(View.VISIBLE);
                                }
                                else if(blankFields == 0){
                                    gameLabel = (TextView) findViewById(R.id.gameLabel);
                                    gameLabel.setText(getString(R.string.draw));
                                    restartLabel.setVisibility(View.VISIBLE);
                                    nextMovie.setVisibility(View.INVISIBLE);
                                    gameBoard.setGameEnded(true);
                                }
                                gameView.invalidate();
                            }
                        }
                    } else {
                        gameBoard.cleanBoard();
                        nextMovie.setImageDrawable(getDrawable(R.drawable.kolko));
                        gameLabel.setText(getString(R.string.correntMovie));
                        gameView.invalidate();
                        restartLabel.setVisibility(View.INVISIBLE);
                        nextSymbol = Symbol.CIRCLE;
                        nextMovie.setVisibility(View.VISIBLE);
                        blankFields = 9;
                    }
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            runAI();
                        }
                    });
                    thread.start();
                }
                return true;
            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                runAI();
            }
        });
        thread.start();
        gameView.invalidate();
    }

    public void onAICompleted(FieldPosition position) {
        gameBoard.getField(position).setSymbol(nextSymbol);
        blankFields--;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                gameBoard.checkIfThreeInARow();
                if (!gameBoard.isGameEnded() && blankFields != 0) {
                    if (nextSymbol.equals(Symbol.CIRCLE)) {
                        nextSymbol = Symbol.CROSS;
                        nextMovie.setImageDrawable(getDrawable(R.drawable.krzyzyk));
                    } else if (nextSymbol.equals(Symbol.CROSS)) {
                        nextSymbol = Symbol.CIRCLE;
                        nextMovie.setImageDrawable(getDrawable(R.drawable.kolko));
                    }
                } else if(gameBoard.isGameEnded()) {
                    gameLabel = (TextView) findViewById(R.id.gameLabel);
                    gameLabel.setText(getString(R.string.winner));
                    restartLabel.setVisibility(View.VISIBLE);
                }
                else if(blankFields == 0){
                    gameLabel = (TextView) findViewById(R.id.gameLabel);
                    gameLabel.setText(getString(R.string.draw));
                    restartLabel.setVisibility(View.VISIBLE);
                    nextMovie.setVisibility(View.INVISIBLE);
                    gameBoard.setGameEnded(true);
                }
                gameView.invalidate();
                aiInProgress = false;
            }
        });
    }

    private void runAI() {
        boolean shouldAIBeExecuted = false;
        if(nextSymbol.equals(Symbol.CIRCLE) && circleAI) {
            shouldAIBeExecuted = true;
        }
        if(nextSymbol.equals(Symbol.CROSS) && crossAI) {
            shouldAIBeExecuted = true;
        }
        if(shouldAIBeExecuted) {;
            aiInProgress = true;
            AIRunner runner = new AIRunner(gameBoard, this);
            FieldPosition position = runner.executeAI(nextSymbol);
            onAICompleted(position);
        }
    }

    @Override
    public void onStop() {
        gameBoard.cleanBoard();
        super.onStop();
    }

    @Override
    public void onDestroy() {
        gameBoard.cleanBoard();
        super.onDestroy();
    }
}
