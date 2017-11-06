package com.urz.tictactoe;

/**
 * Created by jakub on 14.10.17.
 */

public class FieldPosition {
    private int row;
    private int column;

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public FieldPosition(int row, int column) {
        this.row = row;
        this.column = column;
    }


}
