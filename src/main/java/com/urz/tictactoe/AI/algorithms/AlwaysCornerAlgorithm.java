package com.urz.tictactoe.AI.algorithms;

import com.urz.tictactoe.AI.AIAlgorithm;
import com.urz.tictactoe.engine.FieldPosition;
import com.urz.tictactoe.engine.GameBoard;
import com.urz.tictactoe.engine.Symbol;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by jakub on 09.11.17.
 */

public class AlwaysCornerAlgorithm implements AIAlgorithm {
    @Override
    public FieldPosition doNextMovie(GameBoard gameBoard, Symbol symbol) {
        FieldPosition[] positions = {new FieldPosition(1, 1),
                                    new FieldPosition(1, 3),
                                    new FieldPosition(3, 1),
                                    new FieldPosition(3, 3)};
        List<FieldPosition> positionList = new ArrayList<>();
        GameBoard board = GameBoard.getInstance();
        for(FieldPosition position : positions) {
            if(board.getField(position).getSymbol().equals(Symbol.BLANK))
                positionList.add(position);
        }
        if(positionList.size() == 0)
            return null;
        else {
            Random random = new Random();
            return positionList.get(random.nextInt(positionList.size()));
        }
    }
}
