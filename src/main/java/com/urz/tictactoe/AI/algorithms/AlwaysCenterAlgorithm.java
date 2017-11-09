package com.urz.tictactoe.AI.algorithms;

import com.urz.tictactoe.AI.AIAlgorithm;
import com.urz.tictactoe.engine.FieldPosition;
import com.urz.tictactoe.engine.GameBoard;
import com.urz.tictactoe.engine.Symbol;

/**
 * Created by jakub on 09.11.17.
 */

public class AlwaysCenterAlgorithm implements AIAlgorithm {
    @Override
    public FieldPosition doNextMovie(GameBoard gameBoard, Symbol symbol) {
        if(GameBoard.getInstance().column(2).row(2).getSymbol().equals(Symbol.BLANK))
            return new FieldPosition(2, 2);
        return null;
    }
}
