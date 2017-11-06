package com.urz.tictactoe;

import java.util.Iterator;

/**
 * Created by jakub on 17.10.17.
 */

public class SimpleWinAlgorhitm implements AIAlgorhitm {
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
