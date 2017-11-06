package com.urz.tictactoe;

/**
 * Created by jakub on 14.10.17.
 */

public class FieldCoordinates {
    private int left;
    private int top;
    private int right;
    private int bottom;

    public int getRight() {
        return right;
    }

    public int getBottom() {
        return bottom;
    }

    public int getLeft() {
        return left;
    }

    public int getTop() {
        return top;
    }

    public FieldCoordinates(int left, int top, int right, int bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }
}
