package com.urz.tictactoe.AI.algorithms;

import com.urz.tictactoe.AI.AIAlgorithm;
import com.urz.tictactoe.engine.FieldPosition;
import com.urz.tictactoe.engine.GameBoard;
import com.urz.tictactoe.engine.Symbol;

/**
 * Created by jakub on 17.10.17.
 */

public class SimpleWinAlgorithm implements AIAlgorithm {
    @Override
    public FieldPosition doNextMovie(GameBoard gameBoard, Symbol symbol) {
        for(GameBoard.Field field : gameBoard) {
            if(field.getSymbol().equals(Symbol.BLANK)) {
                FieldPosition fieldPosition = new FieldPosition(field.getRow(), field.getColumn());
                GameBoard newBoard = gameBoard.getBoardWithNewSymbok(fieldPosition, symbol);
                if(newBoard.checkIfThreeInARow())
                    return fieldPosition;
            }
        }
        return null;
    }
}
