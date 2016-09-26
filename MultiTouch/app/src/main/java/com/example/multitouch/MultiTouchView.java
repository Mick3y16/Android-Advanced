package com.example.multitouch;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by pedro on 26/08/2016.
 */
public class MultiTouchView extends View {

    private SparseArray<PointF> activePointers;

    private Paint paint;

    private Paint textPaint;

    private static int[] COLOR_SET = new int[] { Color.BLUE, Color.GREEN, Color.MAGENTA, Color.BLACK,
            Color.CYAN, Color.GRAY, Color.RED, Color.DKGRAY, Color.LTGRAY, Color.YELLOW };

    private static final int SIZE = 60;

    public MultiTouchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Get index and id of pointer.
        int pointerIndex = event.getActionIndex();
        int pointerID = event.getPointerId(pointerIndex);

        // Get action
        int maskedAction = event.getActionMasked();

        switch (maskedAction) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                PointF pointDown = new PointF();
                pointDown.x = event.getX(pointerIndex);
                pointDown.y = event.getY(pointerIndex);
                this.activePointers.put(pointerID, pointDown);
                break;
            case MotionEvent.ACTION_MOVE:
                for (int size = event.getPointerCount(), i = 0; i < size; i++) {
                    PointF pointUp = this.activePointers.get(event.getPointerId(i));

                    if (pointUp != null) {
                        pointUp.x = event.getX(i);
                        pointUp.y = event.getY(i);
                    }
                }

                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_CANCEL:
                this.activePointers.remove(pointerID);
                break;
        }

        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw all pointers.
        for (int size = this.activePointers.size(), i = 0; i < size; i++) {
            PointF point = this.activePointers.valueAt(i);

            if (point != null) {
                this.paint.setColor(COLOR_SET[i % 9]);
                canvas.drawCircle(point.x, point.y, SIZE, this.paint);
            }

            canvas.drawText("Total pointers: " + this.activePointers.size(), 10, 40, this.textPaint);
        }
    }

    private void initView() {
        this.activePointers = new SparseArray<>();

        this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.paint.setColor(Color.BLUE);
        this.paint.setStyle(Paint.Style.FILL_AND_STROKE);

        this.textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.textPaint.setTextSize(20);
    }
}
