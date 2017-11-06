package com.urz.tictactoe;

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
        for(AIAlgorhitm algorhitm : AIAlgorhitmsOrderProvider.getAlgorhitmsList()) {
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
