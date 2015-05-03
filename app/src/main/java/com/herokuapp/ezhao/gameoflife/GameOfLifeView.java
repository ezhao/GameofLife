package com.herokuapp.ezhao.gameoflife;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class GameOfLifeView extends View {
    private final int GRIDSIZE = 32;
    private Paint pixelPaint;
    private Paint guidePaint;
    private int pixelSize;
    private int[][] points;
    private Path guidePath;

    public GameOfLifeView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // Setup paint
        pixelPaint = new Paint();
        pixelPaint.setStyle(Paint.Style.FILL);
        pixelPaint.setColor(Color.BLACK);

        guidePaint = new Paint();
        guidePaint.setStyle(Paint.Style.STROKE);
        guidePaint.setStrokeWidth(1);
        guidePaint.setColor(Color.LTGRAY);

        points = new int[GRIDSIZE][GRIDSIZE];
    }

    public void play() {
        // Count # of live neighbors for each point
        int[][] liveNeighbors = new int[GRIDSIZE][GRIDSIZE];
        for (int x=0; x < GRIDSIZE; x++) {
            for (int y=0; y < GRIDSIZE; y++) {
                if (points[x][y] == 1) {
                    if (x > 0) {
                        liveNeighbors[x-1][y] = liveNeighbors[x-1][y]+1;
                        if (y > 0) {
                            liveNeighbors[x-1][y-1] = liveNeighbors[x-1][y-1]+1;
                        }
                        if (y < GRIDSIZE - 1) {
                            liveNeighbors[x-1][y+1] = liveNeighbors[x-1][y+1]+1;
                        }
                    }
                    if (x < GRIDSIZE - 1) {
                        liveNeighbors[x+1][y] = liveNeighbors[x+1][y]+1;
                        if (y > 0) {
                            liveNeighbors[x+1][y-1] = liveNeighbors[x+1][y-1]+1;
                        }
                        if (y < GRIDSIZE - 1) {
                            liveNeighbors[x+1][y+1] = liveNeighbors[x+1][y+1]+1;
                        }
                    }
                    if (y > 0) {
                        liveNeighbors[x][y-1] = liveNeighbors[x][y-1]+1;
                    }
                    if (y < GRIDSIZE - 1) {
                        liveNeighbors[x][y+1] = liveNeighbors[x][y+1]+1;
                    }
                }
            }
        }

        // Apply Game of Life rules en.wikipedia.org/wiki/Conway%27s_Game_of_Life
        for (int x=0; x < GRIDSIZE; x++) {
            for (int y = 0; y < GRIDSIZE; y++) {
                if (points[x][y] == 0 && liveNeighbors[x][y] == 3) {
                    // Dead cell with exactly 3 neighbors comes alive
                    points[x][y] = 1;
                } else if (points[x][y] == 1) {
                    if (liveNeighbors[x][y] < 2 || liveNeighbors[x][y] > 3) {
                        // Live cell with too few or too many neighbors dies
                        points[x][y] = 0;
                    }
                    // Otherwise live cell stays alive
                }
            }
        }

        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float canvasSize = getCanvasSize();
        float offsetLeft = offsetLeft();
        float offsetTop = offsetTop();
        float pixelSizePrecise = canvasSize / GRIDSIZE;

        pixelSize = Math.round(canvasSize / GRIDSIZE);

        // Draw guides
        if (guidePath == null) {
            guidePath = new Path();
            for (int i=0; i <= GRIDSIZE; i++) {
                guidePath.moveTo(offsetLeft, offsetTop+i*pixelSizePrecise);
                guidePath.lineTo(offsetLeft+canvasSize, offsetTop+i*pixelSizePrecise);

                guidePath.moveTo(offsetLeft+i*pixelSizePrecise, offsetTop);
                guidePath.lineTo(offsetLeft+i*pixelSizePrecise, offsetTop+canvasSize);
            }
        }
        canvas.drawPath(guidePath, guidePaint);

        // Draw pixels
        float coordX, coordY;
        for (int x=0; x < GRIDSIZE; x++) {
            for (int y=0; y < GRIDSIZE; y++) {
                if (points[x][y] == 1) {
                    coordX = canvasSize/ GRIDSIZE * x + offsetLeft;
                    coordY = canvasSize/ GRIDSIZE * y + offsetTop;
                    canvas.drawRect(coordX, coordY, coordX+pixelSize, coordY+pixelSize, pixelPaint);
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = Math.round((event.getX()-offsetLeft())/getCanvasSize()* GRIDSIZE);
        int y = Math.round((event.getY()-offsetTop())/getCanvasSize()* GRIDSIZE);
        if(x < GRIDSIZE && y < GRIDSIZE && x >= 0 && y >= 0) {
            points[x][y] = 1;
        }
        postInvalidate();
        return true;
    }

    private float getCanvasSize() {
        return Math.min(getWidth(), getHeight());
    }

    private float offsetTop() {
        if(getWidth() < getHeight()) {
            return (getHeight() - getWidth()) / 2;
        }
        return 0;
    }

    private float offsetLeft() {
        if(getWidth() > getHeight()) {
            return (getWidth() - getHeight()) / 2;
        }
        return 0;
    }
}
