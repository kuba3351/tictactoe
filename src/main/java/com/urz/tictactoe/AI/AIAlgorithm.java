package com.urz.tictactoe.AI;

import com.urz.tictactoe.engine.FieldPosition;
import com.urz.tictactoe.engine.GameBoard;
import com.urz.tictactoe.engine.Symbol;

/**
 * Created by jakub on 17.10.17.
 */

public interface AIAlgorithm {
    public FieldPosition doNextMovie(GameBoard gameBoard, Symbol symbol);
}
