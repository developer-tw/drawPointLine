package com.zykronix.applications.drawpoint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class CustomDrawingView extends View {

    private List<Point> points;
    private Paint pointPaint, linePaint;
    private Path linePath;

    public CustomDrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        points = new ArrayList<>();

        points.add(new Point(100, 100));
        points.add(new Point(200, 200));
        points.add(new Point(300, 300));
        points.add(new Point(400, 400));
        points.add(new Point(500, 500));
        points.add(new Point(600, 600));
        points.add(new Point(700, 700));
        points.add(new Point(800, 800));
        points.add(new Point(900, 900));
        points.add(new Point(1000, 1000));

        pointPaint = new Paint();
        pointPaint.setColor(Color.BLACK);
        linePaint = new Paint();
        linePaint.setColor(Color.RED);
        linePaint.setStrokeWidth(5);
        linePaint.setStyle(Paint.Style.STROKE);
        linePath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (Point point : points) {
            pointPaint.setColor(point.clicked ? Color.GREEN : Color.BLACK);
            canvas.drawCircle(point.x, point.y, 20, pointPaint);
        }

        canvas.drawPath(linePath, linePaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                checkIfPointClicked(x, y);
                linePath.moveTo(x, y);
                return true;

            case MotionEvent.ACTION_MOVE:
                linePath.lineTo(x, y);
                invalidate();
                checkIfPointPassed(x, y);
                return true;

            default:
                return false;
        }
    }

    private void checkIfPointClicked(float x, float y) {
        for (Point point : points) {
            if (Math.abs(x - point.x) < 20 && Math.abs(y - point.y) < 20) {
                point.clicked = true;
            }
        }
    }

    private void checkIfPointPassed(float x, float y) {
        for (Point point : points) {
            if (Math.abs(x - point.x) < 20 && Math.abs(y - point.y) < 20) {
                point.clicked = true;
            }
        }
    }

    public void addPoint(int x, int y) {
        points.add(new Point(x, y));
    }

    private static class Point {
        int x, y;
        boolean clicked = false;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public void clearLines() {
        linePath.reset();
        invalidate();
    }

    public void clearPointsColor() {
        for (Point point : points) {
            point.clicked = false;
        }
        invalidate();
    }

}
