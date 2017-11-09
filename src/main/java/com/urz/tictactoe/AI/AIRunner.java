package com.urz.tictactoe.AI;

import com.urz.tictactoe.engine.FieldPosition;
import com.urz.tictactoe.engine.Game;
import com.urz.tictactoe.engine.GameBoard;
import com.urz.tictactoe.engine.Symbol;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by jakub on 17.10.17.
 */

public class AIRunner {

    private GameBoard gameBoard;
    private Game game;

    public AIRunner(GameBoard gameBoard, Game game) {
        this.gameBoard = gameBoard;
        this.game = game;
    }

    protected FieldPosition executeAI(Symbol params) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for(AIAlgorithm algorhitm : AIAlgorhitmsOrderProvider.getAlgorhitmsList()) {
            FieldPosition position = algorhitm.doNextMovie(gameBoard, params);
            if(position != null)
                return position;
        }
        List<FieldPosition> blankFieldPositions = new ArrayList<>();
        for(GameBoard.Field field : gameBoard) {
            if(field.getSymbol().equals(Symbol.BLANK))
                blankFieldPositions.add(new FieldPosition(field.getRow(), field.getColumn()));
        }
        Random random = new Random();
        int fieldNumber = random.nextInt(blankFieldPositions.size());
        return blankFieldPositions.get(fieldNumber);
    }
}
