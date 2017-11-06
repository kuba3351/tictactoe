package com.urz.tictactoe;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.urz.tictactoe.kkoikrzyyk.R;

/**
 * Created by jakub on 14.10.17.
 */

public class GameView extends View {

    private int width;
    private int height;

    Drawable circle = getContext().getDrawable(R.drawable.kolko);
    Drawable cross = getContext().getDrawable(R.drawable.krzyzyk);

    private Paint paint = new Paint();

    public GameView(Context context) {
        super(context);
    }

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GameView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public GameView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onDraw(Canvas canvas) {
        paint.setStrokeWidth(20);
        GameBoard.getInstance().draw(canvas, width, height, paint, circle, cross);
    }

    @Override
    public void onSizeChanged(int width, int heigth, int odlWidth, int oldHeigth) {
        this.width = width;
        this.height = heigth;
    }
}
