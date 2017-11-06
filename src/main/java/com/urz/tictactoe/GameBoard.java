package com.urz.tictactoe;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by jakub on 14.10.17.
 */

public class GameBoard implements Iterable<GameBoard.Field> {

    private static final int OUTLINE_SIZE = 300;
    private static final int CENTER_SIZE = OUTLINE_SIZE /3;
    private static final int FIELD_SIZE = OUTLINE_SIZE - CENTER_SIZE;

    private boolean gameEnded = false;

    public void setGameEnded(boolean gameEnded) {
        this.gameEnded = gameEnded;
    }

    private Map<Line, FieldPosition> line;

    public boolean isGameEnded() {
        return gameEnded;
    }

    public Map<Line, FieldPosition> getLine() {
        return line;
    }

    private Field[][] board;

    private static GameBoard instance;

    public static GameBoard getInstance() {
        if(instance == null)
            instance = new GameBoard();
        return instance;
    }

    private GameBoard() {
        cleanBoard();
    }

    private GameBoard(Field[][] board) {
        this.board = board;
    }

    public void cleanBoard() {
        board = new Field[3][3];
        for(int i = 0;i<3;i++)
            for(int j = 0;j<3;j++)
                board[i][j] = new Field(Symbol.BLANK, i + 1, j + 1);
        gameEnded = false;
        line = null;
    }

    public Column column(int column) {
        return new Column(board[column - 1]);
    }

    public Row row(int row) {
        Field[] targetRow = new Field[3];
        targetRow[0] = board[0][row - 1];
        targetRow[1] = board[1][row - 1];
        targetRow[2] = board[2][row - 1];
        return new Row(targetRow);
    }

    public GameBoard getBoardWithNewSymbok(FieldPosition fieldPosition, Symbol symbol) {
        Field[][] newBoard = new Field[3][3];
        for(int i = 0;i<3;i++)
            for(int j = 0;j<3;j++)
                newBoard[i][j] = new Field(board[i][j].getSymbol(), i + 1, j + 1);
        newBoard[fieldPosition.getColumn() - 1][fieldPosition.getRow() - 1] = new Field(symbol, fieldPosition.getColumn(), fieldPosition.getRow());
        return new GameBoard(newBoard);
    }

    public void draw(Canvas canvas, int width, int height, Paint paint, Drawable circle, Drawable cross) {
        canvas.drawLine(width/2- OUTLINE_SIZE, height/2- CENTER_SIZE, width/2+ OUTLINE_SIZE, height/2- CENTER_SIZE, paint);
        canvas.drawLine(width/2- OUTLINE_SIZE, height/2+ CENTER_SIZE, width/2+ OUTLINE_SIZE, height/2+ CENTER_SIZE, paint);
        canvas.drawLine(width/2- CENTER_SIZE, height/2- OUTLINE_SIZE, width/2- CENTER_SIZE, height/2+ OUTLINE_SIZE, paint);
        canvas.drawLine(width/2+ CENTER_SIZE, height/2- OUTLINE_SIZE, width/2+ CENTER_SIZE, height/2+ OUTLINE_SIZE, paint);
        for(int i = 0;i<3;i++) {
            for (int j = 0; j < 3; j++) {
                FieldCoordinates fieldCoordinates = calculateFieldCoordinatesByPosition(i + 1, j + 1, width, height);
                switch (board[i][j].getSymbol()) {
                    case CIRCLE:
                        circle.setBounds(fieldCoordinates.getLeft(), fieldCoordinates.getTop(), fieldCoordinates.getRight(), fieldCoordinates.getBottom());
                        circle.draw(canvas);
                        break;
                    case CROSS:
                        cross.setBounds(fieldCoordinates.getLeft(), fieldCoordinates.getTop(), fieldCoordinates.getRight(), fieldCoordinates.getBottom());
                        cross.draw(canvas);
                        break;
                }
            }
        }
        if(isGameEnded() && line != null) {
            FieldPosition startFieldPosition = getLine().get(Line.START_FIELD);
            FieldPosition endFieldPosition = getLine().get(Line.END_FIELD);
            Map<Coordinates, Float> startPoint = calculateCenterFieldCoordinatesByPosition(startFieldPosition.getRow(), startFieldPosition.getColumn(), width, height);
            Map<Coordinates, Float> endPoint = calculateCenterFieldCoordinatesByPosition(endFieldPosition.getRow(), endFieldPosition.getColumn(), width, height);
            if(startFieldPosition.getColumn() < endFieldPosition.getColumn()) {
                startPoint.put(Coordinates.Y, startPoint.get(Coordinates.Y) - FIELD_SIZE / 3);
                endPoint.put(Coordinates.Y, endPoint.get(Coordinates.Y) + FIELD_SIZE / 3);
            }
            if(startFieldPosition.getRow() < endFieldPosition.getRow()) {
                startPoint.put(Coordinates.X, startPoint.get(Coordinates.X) - FIELD_SIZE / 3);
                endPoint.put(Coordinates.X, endPoint.get(Coordinates.X) + FIELD_SIZE / 3);
            }
            if(startFieldPosition.getColumn() > endFieldPosition.getColumn()) {
                startPoint.put(Coordinates.Y, startPoint.get(Coordinates.Y) + FIELD_SIZE / 3);
                endPoint.put(Coordinates.Y, endPoint.get(Coordinates.Y) - FIELD_SIZE / 3);
            }
            if(startFieldPosition.getRow() > endFieldPosition.getRow()) {
                startPoint.put(Coordinates.X, startPoint.get(Coordinates.X) + FIELD_SIZE / 3);
                endPoint.put(Coordinates.X, endPoint.get(Coordinates.X) - FIELD_SIZE / 3);
            }
            paint.setColor(Color.GREEN);
            canvas.drawLine(startPoint.get(Coordinates.X), startPoint.get(Coordinates.Y), endPoint.get(Coordinates.X), endPoint.get(Coordinates.Y), paint);
            paint.setColor(Color.BLACK);
        }
    }

    public Boolean checkIfThreeInARow() {
        Symbol diagonal = board[0][0].getSymbol();
        Symbol reverseDiagonal = board[2][0].getSymbol();
        boolean isDiagonal = !diagonal.equals(Symbol.BLANK);
        boolean isReverseDiagonal = !reverseDiagonal.equals(Symbol.BLANK);
        for(int i = 0;i<3;i++) {
            if(!board[i][i].getSymbol().equals(diagonal))
                isDiagonal = false;
            if(!board[i][2 - i].getSymbol().equals(reverseDiagonal))
                isReverseDiagonal = false;
            Symbol row = board[i][0].getSymbol();
            Symbol column = board[0][i].getSymbol();
            boolean isRow = !row.equals(Symbol.BLANK);
            boolean isColumn = !column.equals(Symbol.BLANK);
            for(int j = 0;j<3;j++) {
                if(!board[i][j].getSymbol().equals(row))
                    isRow = false;
                if(!board[j][i].getSymbol().equals(column))
                    isColumn = false;

            }
            if(isRow) {
                line = new HashMap<>();
                line.put(Line.START_FIELD, new FieldPosition(i + 1, 1));
                line.put(Line.MIDDLE_FIELD, new FieldPosition(i + 1, 2));
                line.put(Line.END_FIELD, new FieldPosition(i + 1, 3));
                gameEnded = true;
            }
            if(isColumn) {
                line = new HashMap<>();
                line.put(Line.START_FIELD, new FieldPosition(1, i + 1));
                line.put(Line.MIDDLE_FIELD, new FieldPosition(2, i + 1));
                line.put(Line.END_FIELD, new FieldPosition(3, i + 1));
                gameEnded = true;
            }
        }
        if(isDiagonal) {
            line = new HashMap<>();
            line.put(Line.START_FIELD, new FieldPosition(1, 1));
            line.put(Line.MIDDLE_FIELD, new FieldPosition(2, 2));
            line.put(Line.END_FIELD, new FieldPosition(3, 3));
            gameEnded = true;
        }
        if(isReverseDiagonal) {
            line = new HashMap<>();
            line.put(Line.START_FIELD, new FieldPosition(3, 1));
            line.put(Line.MIDDLE_FIELD, new FieldPosition(2, 2));
            line.put(Line.END_FIELD, new FieldPosition(1, 3));
            gameEnded = true;
        }
        return gameEnded;
    }

    @Override
    public Iterator<Field> iterator() {
        return new GameBoardIterator(this);
    }

    public FieldPosition calculateFieldPositionFromXY(float x, float y, int width, int height) {
        x -= width/2;
        x += (OUTLINE_SIZE);
        y -= height/2;
        y += (OUTLINE_SIZE);
        int column = (int) x / FIELD_SIZE + 1;
        int row = (int) y / FIELD_SIZE + 1;
        if(row >= 1 && row <= 3 && column >= 1 && column <= 3)
            return new FieldPosition(row, column);
        else return null;
    }

    public FieldCoordinates calculateFieldCoordinatesByPosition(int row, int column, int width, int height) {
        int left = (row-1) * FIELD_SIZE;
        int top = (column-1) * FIELD_SIZE;
        int right = (row) * FIELD_SIZE;
        int bottom = (column) * FIELD_SIZE;

        left += width/2;
        left -= (OUTLINE_SIZE);
        top += height/2;
        top -= (OUTLINE_SIZE);
        right += width/2;
        right -= (OUTLINE_SIZE);
        bottom += height/2;
        bottom -= (OUTLINE_SIZE);

        left += 10;
        top += 10;
        right -= 10;
        bottom -= 10;

        return new FieldCoordinates(left, top, right, bottom);
    }

    public Map<Coordinates, Float> calculateCenterFieldCoordinatesByPosition(int row, int column, int width, int height) {
        FieldCoordinates fieldCoordinates = calculateFieldCoordinatesByPosition(row, column, width, height);
        Map<Coordinates, Float> coordinates = new HashMap<>();
        coordinates.put(Coordinates.X, (float)fieldCoordinates.getLeft() + FIELD_SIZE/2 - 10);
        coordinates.put(Coordinates.Y, (float)fieldCoordinates.getTop() + FIELD_SIZE/2 - 10);
        return coordinates;
    }

    public Field getField(FieldPosition fieldPosition) {
        return column(fieldPosition.getColumn()).row(fieldPosition.getRow());
    }

    @Override
    public String toString() {
        return row(1).column(1) + "|" + row(1).column(2) + "|" + row(1).column(3) + "\n" +
                "-+-+-\n" +
                row(2).column(1) + "|" + row(2).column(2) + "|" + row(2).column(3) + "\n" +
                "-+-+-\n" +
                row(3).column(1) + "|" + row(3).column(2) + "|" + row(3).column(3) + "\n";
    }

    public class Row {
        Field[] row;

        private Row(Field[] row) {
            this.row = row;
        }

        public Field column(int column) {
            return row[column - 1];
        }
    }

    public class Column {
        Field[] column;

        private Column(Field[] column) {
            this.column = column;
        }

        public Field row(int row) {
            return column[row - 1];
        }
    }

    public class Field {
        private Symbol field;
        private int column;
        private int row;

        public int getColumn() {
            return column;
        }

        public void setColumn(int column) {
            this.column = column;
        }

        public int getRow() {
            return row;
        }

        public void setRow(int row) {
            this.row = row;
        }

        private Field(Symbol field, int column, int row) {
            this.field = field;
            this.column = column;
            this.row = row;
        }

        public Symbol getSymbol() {
            return field;
        }

        public void setSymbol(Symbol symbol) {
            this.field = symbol;
        }

        @Override
        public String toString() {
            switch (getSymbol()){
                case CIRCLE:
                    return "o";
                case CROSS:
                    return "x";
                case BLANK:
                    return " ";
                default: throw new RuntimeException("Unsupported symbol!");
            }
        }
    }

    public class GameBoardIterator implements Iterator<Field> {

        private GameBoard gameBoard;

        private GameBoardIterator(GameBoard gameBoard) {
            this.gameBoard = gameBoard;
        }

        private int row = 1;
        private int column = 1;

        @Override
        public boolean hasNext() {
            return !(column == 3 && row == 4);
        }

        @Override
        public Field next() {
            if(row <= 3) {
                return gameBoard.row(row++).column(column);
            }
            else {
                row = 1;
                return gameBoard.row(row++).column(++column);
            }
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove dield from GameBoard is not supported!");
        }
    }
}
