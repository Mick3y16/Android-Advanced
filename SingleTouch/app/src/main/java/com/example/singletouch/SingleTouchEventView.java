package com.example.singletouch;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by pedro on 26/08/2016.
 */
public class SingleTouchEventView extends View {

    private Paint paint = new Paint();

    private Path path = new Path();

    private float eventX;

    private float eventY;

    private boolean fingerDown = false;

    public SingleTouchEventView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // Setting up the paint for color, style and length draw configurations.
        this.paint.setAntiAlias(true);
        this.paint.setStrokeWidth(6f);
        this.paint.setColor(Color.BLACK);
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setStrokeJoin(Paint.Join.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(this.path, this.paint);

        if (fingerDown) {
            paint.setColor(Color.BLUE);
            canvas.drawCircle(eventX, eventY, 20, this.paint);
            paint.setColor(Color.BLACK);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.eventX = event.getX();
        this.eventY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                this.fingerDown = true;
                path.moveTo(this.eventX, this.eventY);
                return true;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(this.eventX, this.eventY);
                break;
            case MotionEvent.ACTION_UP:
                this.fingerDown = false;
                break;
            default:
                return false;
        }

        // Marks for repaint.
        invalidate();
        return true;
    }
}
